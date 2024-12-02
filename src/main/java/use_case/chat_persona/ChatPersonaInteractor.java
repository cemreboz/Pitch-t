package use_case.chat_persona;

import java.util.ArrayList;
import java.util.List;

import entity.ChatMessage;
import entity.Persona;
import entity.Pitch;

/**
 * Interactor for the chat_persona use case.
 */
public class ChatPersonaInteractor implements ChatPersonaInputBoundary {
    private final ChatPersonaDataAccessInterface chatGptDao;
    private final ChatPersonaOutputBoundary outputBoundary;

    public ChatPersonaInteractor(ChatPersonaDataAccessInterface chatGptDao, ChatPersonaOutputBoundary outputBoundary) {
        this.chatGptDao = chatGptDao;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Initiates a chat with a persona.
     *
     * @param inputData The input data containing user message
     *                  and persona ID.
     */
    @Override
    public void initiateChat(ChatPersonaInputData inputData) {
        final Persona persona = inputData.getPersona();
        final Pitch pitch = inputData.getPitch();
        final String userMessage = inputData.getUserMessage();
        final List<ChatMessage> chatHistory = persona.getChatHistory();
        if (chatHistory.isEmpty()) {
            // Generate the initial prompt
            final String initialPrompt = String.format(
                    "You are %s, a %d-year-old %s from %s. "
                            + "Please give your opinion about %s: %s",
                    persona.getName(), persona.getAge(), persona.getOccupation(), persona.getLocation(),
                    pitch.getName(), pitch.getDescription()
            );
            chatHistory.add(new ChatMessage("system", initialPrompt));
        }
        // Add user's message
        if (!("".equals(userMessage))) {
            chatHistory.add(new ChatMessage("user", userMessage));
        }
        // Call GPT API
        final String assistantResponse = chatGptDao.utilizeApi(chatHistory);
        chatHistory.add(new ChatMessage("assistant", assistantResponse));
        // Prepare output data
        final ChatPersonaOutputData outputData = new ChatPersonaOutputData(new ArrayList<>(chatHistory));
        outputBoundary.presentChat(outputData);
    }
}
