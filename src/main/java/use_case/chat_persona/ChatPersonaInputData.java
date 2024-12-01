package use_case.chat_persona;

import entity.Persona;
import entity.Pitch;

/**
 * Input data for the chat_persona use case.
 */
public class ChatPersonaInputData {
    private final String userMessage;
    private final Persona persona;
    private final Pitch pitch;

    public ChatPersonaInputData(String userMessage, Persona persona, Pitch pitch) {
        this.userMessage = userMessage;
        this.persona = persona;
        this.pitch = pitch;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public Persona getPersona() {
        return persona;
    }

    public Pitch getPitch() {
        return pitch;
    }
}
