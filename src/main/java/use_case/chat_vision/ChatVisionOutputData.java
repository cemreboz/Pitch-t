package use_case.chat_vision;

import entity.ChatMessage;

import java.util.List;

/**
 * Output data class for the Chat with Persona use case.
 * Contains the updated chat history.
 */
public class ChatVisionOutputData {

    private final String visionResponse;
    private final List<ChatMessage> chatHistory;

    public ChatVisionOutputData(String visionResponse, List<ChatMessage> chatHistory) {
        this.visionResponse = visionResponse;
        this.chatHistory = chatHistory;
    }

    public String getVisionResponse() {
        return visionResponse;
    }

    public List<ChatMessage> getChatHistory() {
        return chatHistory;
    }
}

