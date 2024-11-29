package use_case.chat_expert;

/**
 * Output boundary interface for the Chat with Expert use case.
 * Defines methods that the interactor will call to present data.
 */
public interface ChatExpertOutputBoundary {

    /**
     * Presents the chat output data to the user.
     *
     * @param outputData The output data containing expert response
     *                   and updated chat history.
     */
    void presentChat(ChatExpertOutputData outputData);
}
