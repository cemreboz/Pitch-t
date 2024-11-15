package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an expert with an ID and a chat history.
 */
public class Expert {
    private String id;
    private List<String> chatHistory;

    public Expert(String id) {
        this.id = id;
        this.chatHistory = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<String> chatHistory) {
        this.chatHistory = chatHistory;
    }

    /**
     * Adds a message to the chat history.
     * @param message is a message to be added
     */
    public void addChatMessage(String message) {
        this.chatHistory.add(message);
    }

    /**
     * Clears all message from the chat history.
     */
    public void clearChatHistory() {
        this.chatHistory.clear();
    }
}