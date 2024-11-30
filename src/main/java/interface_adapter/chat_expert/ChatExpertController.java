package interface_adapter.chat_expert;

import use_case.chat_expert.ChatExpertInputBoundary;
import use_case.chat_expert.ChatExpertInputData;

/**
 * Controller for the Chat with Expert use case.
 * Handles user actions and invokes the interactor.
 */
public class ChatExpertController {

    private final ChatExpertInputBoundary inputBoundary;

    /**
     * Constructs a ChatExpertController object.
     *
     * @param inputBoundary The input boundary for the use case.
     */
    public ChatExpertController(ChatExpertInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Starts a chat with the specified expert.
     *
     * @param expertId    The ID of the expert to chat with.
     * @param userMessage The initial message from the user.
     */
    public void startChat(String expertId, String userMessage) {
        final ChatExpertInputData inputData = new ChatExpertInputData(expertId, userMessage);
        inputBoundary.initiateChat(inputData);
    }
}
