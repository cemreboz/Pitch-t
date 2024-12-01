package use_case.chat_persona;

/**
 * Output boundary for the chat_persona use case.
 */
public interface ChatPersonaOutputBoundary {
    /**
     * Prepares the success view after executing the use case.
     * @param outputData The data to present in the view.
     */
    void prepareSuccessView(ChatPersonaOutputData outputData);

    /**
     * Prepares the fail view in case of errors.
     * @param errorMessage The error message to display.
     */
    void prepareFailView(String errorMessage);
}
