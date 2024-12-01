package use_case.chat_persona;

import data_access.ChatgptDataAccessObject;
import entity.ChatMessage;
import entity.Persona;
import entity.Pitch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interactor for the chat_persona use case.
 */
public class ChatPersonaInteractor implements ChatPersonaInputBoundary {
    private final ChatgptDataAccessObject chatgptDao;
    private final ChatPersonaOutputBoundary outputBoundary;

    public ChatPersonaInteractor(ChatgptDataAccessObject chatgptDao, ChatPersonaOutputBoundary outputBoundary) {
        this.chatgptDao = chatgptDao;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ChatPersonaInputData inputData) {
        final Persona persona = inputData.getPersona();
        final Pitch pitch = inputData.getPitch();
        final String userMessage = inputData.getUserMessage();

        final List<ChatMessage> chatHistory = persona.getChatHistory();

        if (chatHistory.isEmpty()) {
            // Generate the initial prompt
            final String initialPrompt = String.format(
                    "You are %s, a %d-year-old %s from %s. " +
                            "Please give your opinion about %s: %s",
                    persona.getName(), persona.getAge(), persona.getOccupation(), persona.getLocation(),
                    pitch.getName(), pitch.getDescription()
            );

            chatHistory.add(new ChatMessage("system", initialPrompt));
        }

        // Add user's message
        chatHistory.add(new ChatMessage("user", userMessage));

        // Call GPT API
        try {
            final String assistantResponse = chatgptDao.utilizeApi(chatHistory);
            chatHistory.add(new ChatMessage("assistant", assistantResponse));

            // Prepare output data
            final ChatPersonaOutputData outputData = new ChatPersonaOutputData(new ArrayList<>(chatHistory));
            outputBoundary.prepareSuccessView(outputData);

        }
        catch (IOException | InterruptedException e) {
            outputBoundary.prepareFailView("Error communicating with GPT API: " + e.getMessage());
        }
    }
}
