package interface_adapter.chat_persona;

import entity.ChatMessage;
import entity.Persona;
import entity.Pitch;

import java.util.ArrayList;
import java.util.List;

/**
 * State for the chat_persona use case.
 */
public class ChatPersonaState {
    private Persona persona;
    private Pitch pitch;
    private List<ChatMessage> chatHistory;

    public ChatPersonaState() {
        this.chatHistory = new ArrayList<>();
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }

    public List<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }
}
