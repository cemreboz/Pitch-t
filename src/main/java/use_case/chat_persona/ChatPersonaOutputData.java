package use_case.chat_persona;

import entity.ChatMessage;

import java.util.List;

/**
 * Output data class for the Chat with Persona use case.
 * Contains the updated chat history.
 */
public class ChatPersonaOutputData {
    private final List<ChatMessage> chatHistory;

    public ChatPersonaOutputData(List<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public List<ChatMessage> getChatHistory() {
        return chatHistory;
    }
}
