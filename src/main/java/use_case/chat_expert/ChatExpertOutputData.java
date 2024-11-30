package use_case.chat_expert;

import entity.ChatMessage;

import java.util.List;

/**
 * Output data class for the Chat with Expert use case.
 * Contains the expert's response and updated chat history.
 */
public class ChatExpertOutputData {

    private final String expertResponse;
    private final List<ChatMessage> chatHistory;

    /**
     * Constructs a ChatExpertOutputData object.
     *
     * @param expertResponse The response from the expert.
     * @param chatHistory    The updated chat history.
     */
    public ChatExpertOutputData(String expertResponse,
                                List<ChatMessage> chatHistory) {
        this.expertResponse = expertResponse;
        this.chatHistory = chatHistory;
    }

    /**
     * Gets the expert's response.
     *
     * @return The expert's response.
     */
    public String getExpertResponse() {
        return expertResponse;
    }

    /**
     * Gets the chat history.
     *
     * @return The chat history.
     */
    public List<ChatMessage> getChatHistory() {
        return chatHistory;
    }
}
