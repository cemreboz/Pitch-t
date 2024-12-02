package interface_adapter.chat_persona;

import entity.Persona;
import entity.Pitch;
import use_case.chat_persona.ChatPersonaInputBoundary;
import use_case.chat_persona.ChatPersonaInputData;

/**
 * Controller for the chat persona use case.
 */
public class ChatPersonaController {
    private final ChatPersonaInputBoundary chatPersonaInteractor;

    public ChatPersonaController(ChatPersonaInputBoundary chatPersonaInputBoundary) {
        this.chatPersonaInteractor = chatPersonaInputBoundary;
    }

    /**
     * Method to handle the user message submission.
     * @param persona the persona for the chat
     * @param pitch the pitch being discussed
     * @param userMessage the user's message
     */
    public void startChat(String userMessage, Persona persona, Pitch pitch) {
        final ChatPersonaInputData inputData = new ChatPersonaInputData(userMessage, persona, pitch);
        chatPersonaInteractor.initiateChat(inputData);
    }
}
