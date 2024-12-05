package interface_adapter.chat_vision;

import entity.Persona;
import entity.Pitch;
import entity.Visual;
import use_case.chat_vision.ChatVisionInputBoundary;
import use_case.chat_vision.ChatVisionInputData;

/**
 * Controller for the chat persona use case.
 */
public class ChatVisionController {
    private final ChatVisionInputBoundary chatVisionInteractor;

    public ChatVisionController(ChatVisionInputBoundary chatVisionInteractor) {
        this.chatVisionInteractor = chatVisionInteractor;
    }

    /**
     * Method to handle the user message submission.
     * @param persona the persona for the chat
     * @param pitch the pitch being discussed
     * @param userMessage the user's message
     * @param visual the user's message
     */
    public void startChat(String userMessage, Persona persona, Pitch pitch, Visual visual) {
        final ChatVisionInputData inputData = new ChatVisionInputData(userMessage, persona, pitch, visual);
        chatVisionInteractor.initiateChat(inputData);
    }
}
