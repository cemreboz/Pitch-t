package use_case.chat_vision;

/**
 * Output boundary interface for the Chat with Persona use case.
 * Defines methods that the interactor will call to present data.
 */
public interface ChatVisionOutputBoundary {

    /**
     * Presents the chat output data to the user.
     *
     * @param outputData The output data containing persona response
     *                   and updated chat history.
     */
    void presentChat(ChatVisionOutputData outputData);
}
