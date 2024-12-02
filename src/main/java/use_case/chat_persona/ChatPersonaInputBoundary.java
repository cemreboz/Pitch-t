package use_case.chat_persona;

/**
 * Input boundary for the chat persona use case.
 */
public interface ChatPersonaInputBoundary {

    /**
     * Initiates a chat with the persona.
     *
     * @param inputData The input data containing user message and context.
     */
    void initiateChat(ChatPersonaInputData inputData);
}
