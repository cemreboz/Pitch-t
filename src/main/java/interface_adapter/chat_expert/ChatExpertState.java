package interface_adapter.chat_expert;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state for the ExpertChatView.
 */
public class ChatExpertState {
    private String username = "";
    private String password = "";
    private String expertResponse = "";
    private List<String> chatHistory = new ArrayList<>();

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

    /**
     * Gets the username associated with this state.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
