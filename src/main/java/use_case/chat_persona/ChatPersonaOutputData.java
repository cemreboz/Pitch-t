package use_case.chat_persona;

import java.util.List;

import entity.ChatMessage;

/**
 * Output data for the chat_persona use case.
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
