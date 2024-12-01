package interface_adapter.persona;

import java.util.ArrayList;
import java.util.List;

import entity.ChatMessage;
import entity.Persona;

/**
 * Represents the state for persona chat view.
 */
public class PersonaState {
    private String username = "";
    private String password = "";
    private String expertResponse = "";
    private Persona persona = new Persona();
    private List<ChatMessage> chatHistory = new ArrayList<>();

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

    public String getExpertResponse() {
        return expertResponse;
    }

    public void setExpertResponse(String expertResponse) {
        this.expertResponse = expertResponse;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }
}
