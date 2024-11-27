package interface_adapter.chat_expert;

import java.util.ArrayList;
import java.util.List;

/**
 * View model for the Chat with Expert use case.
 * Holds data for display in the view layer.
 */
public class ChatExpertViewModel {

    private String expertResponse;
    private List<String> chatHistory;

    /**
     * Constructs a ChatExpertViewModel object.
     */
    public ChatExpertViewModel() {
        this.chatHistory = new ArrayList<>();
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
     * Sets the expert's response.
     *
     * @param expertResponse The expert's response.
     */
    public void setExpertResponse(String expertResponse) {
        this.expertResponse = expertResponse;
    }

    /**
     * Gets the chat history.
     *
     * @return The chat history.
     */
    public List<String> getChatHistory() {
        return chatHistory;
    }

    /**
     * Sets the chat history.
     *
     * @param chatHistory The updated chat history.
     */
    public void setChatHistory(List<String> chatHistory) {
        this.chatHistory = chatHistory;
    }
}
