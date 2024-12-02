package use_case.chat_persona;

/**
 * Output boundary interface for the Chat with Persona use case.
 * Defines methods that the interactor will call to present data.
 */
public interface ChatPersonaOutputBoundary {

    /**
     * Presents the chat output data to the user.
     *
     * @param outputData The output data containing persona response
     *                   and updated chat history.
     */
    void presentChat(ChatPersonaOutputData outputData);
}
