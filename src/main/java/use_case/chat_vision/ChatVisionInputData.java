package use_case.chat_vision;

import entity.Persona;
import entity.Pitch;
import entity.Visual;

/**
 * Input data for the chat persona use case.
 */
public class ChatVisionInputData {
    private final String userMessage;
    private final Persona persona;
    private final Pitch pitch;
    private final Visual visual;

    public ChatVisionInputData(String userMessage, Persona persona, Pitch pitch, Visual visual) {
        this.userMessage = userMessage;
        this.persona = persona;
        this.pitch = pitch;
        this.visual = visual;
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

    public Visual getVisual() {
        return visual;
    }
}
