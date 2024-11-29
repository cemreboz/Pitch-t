package use_case.chat_expert;

/**
 * Input boundary interface for the Chat with Expert use case.
 * Defines methods that the controller will call.
 */
public interface ChatExpertInputBoundary {

    /**
     * Initiates a chat with an expert.
     *
     * @param inputData The input data containing user message
     *                  and expert ID.
     */
    void initiateChat(ChatExpertInputData inputData);
}
