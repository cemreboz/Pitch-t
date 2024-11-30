package use_case.chat_expert;

/**
 * Input data class for the Chat with Expert use case.
 * Encapsulates the user message and expert ID.
 */
public class ChatExpertInputData {

    private final String expertId;
    private final String userMessage;

    /**
     * Constructs a ChatExpertInputData object.
     *
     * @param expertId    The ID of the expert.
     * @param userMessage The message from the user.
     */
    public ChatExpertInputData(String expertId, String userMessage) {
        this.expertId = expertId;
        this.userMessage = userMessage;
    }

    /**
     * Gets the expert ID.
     *
     * @return The expert ID.
     */
    public String getExpertId() {
        return expertId;
    }

    /**
     * Gets the user message.
     *
     * @return The user message.
     */
    public String getUserMessage() {
        return userMessage;
    }
}
