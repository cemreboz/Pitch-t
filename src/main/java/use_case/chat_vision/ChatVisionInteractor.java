package use_case.chat_vision;

import entity.ChatMessage;
import entity.Persona;
import entity.Pitch;
import entity.Visual;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor for the chat_persona use case.
 */
public class ChatVisionInteractor implements ChatVisionInputBoundary {
    private final ChatVisionDataAccessInterface chatgptDataAccessObject;
    private final ChatVisionOutputBoundary outputBoundary;

    public ChatVisionInteractor(ChatVisionDataAccessInterface chatgptDataAccessObject, ChatVisionOutputBoundary outputBoundary) {
        this.chatgptDataAccessObject = chatgptDataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Initiates a chat with a persona.
     *
     * @param inputData The input data containing user message
     *                  and persona ID.
     */
    @Override
    public void initiateChat(ChatVisionInputData inputData) {
        final Persona persona = inputData.getPersona();
        final Pitch pitch = inputData.getPitch();
        final Visual visual = inputData.getVisual();
        final String userMessage = inputData.getUserMessage();
        final List<ChatMessage> chatHistory = persona.getChatHistory();
        if (chatHistory.isEmpty()) {
            // Generate the initial prompt
            final String initialPrompt = String.format(
                    "You are %s, a %d-year-old %s from %s. "
                            + "Please give your opinion about %s: %s\nHere is the image: %s",
                    persona.getName(), persona.getAge(), persona.getOccupation(), persona.getLocation(),
                    pitch.getName(), pitch.getDescription(), visual.getImagePath(), visual.getDescription()
            );
            chatHistory.add(new ChatMessage("system", initialPrompt));
        }
        final int maxMessages = 10;
        final int startIndex = Math.max(chatHistory.size() - maxMessages, 0);
        final List<ChatMessage> messagesForApi = new ArrayList<>();
        for (int i = startIndex; i < chatHistory.size(); i++) {
            messagesForApi.add(chatHistory.get(i));
        }

        String visionResponse;
        try {
            visionResponse = chatgptDataAccessObject.utilizeApi(messagesForApi);
        }
        catch (Exception event) {
            visionResponse = "I'm sorry, but I'm having trouble responding right now.";
            event.printStackTrace();
        }

        // Add user's message
        if (!("".equals(userMessage))) {
            chatHistory.add(new ChatMessage("user", userMessage));
        }
        // Call GPT API
        final String assistantResponse = chatgptDataAccessObject.utilizeApi(chatHistory);
        chatHistory.add(new ChatMessage("assistant", assistantResponse));
        // Prepare output data
        final ChatVisionOutputData outputData = new ChatVisionOutputData(visionResponse, chatHistory);
        outputBoundary.presentChat(outputData);
    }
}
