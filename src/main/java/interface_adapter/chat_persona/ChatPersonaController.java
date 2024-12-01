package interface_adapter.chat_persona;

import entity.Persona;
import entity.Pitch;
import use_case.chat_persona.ChatPersonaInputBoundary;
import use_case.chat_persona.ChatPersonaInputData;

import java.io.IOException;

/**
 * Controller for the chat_persona use case.
 */
public class ChatPersonaController {
    private final ChatPersonaInputBoundary interactor;

    public ChatPersonaController(ChatPersonaInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Handles user message submission.
     * @param userMessage The user's message.
     * @param persona The persona for the chat.
     * @param pitch The pitch being discussed.
     */
    public void sendMessage(String userMessage, Persona persona, Pitch pitch) throws IOException, InterruptedException {
        ChatPersonaInputData inputData = new ChatPersonaInputData(userMessage, persona, pitch);
        interactor.execute(inputData);
    }
}
