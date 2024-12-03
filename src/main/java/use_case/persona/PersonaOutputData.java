package use_case.persona;

import entity.Persona;
import entity.Pitch;

/**
 * The output data for presenting the persona chat.
 */
public class PersonaOutputData {

    private final String username;
    private final String password;
    private final Persona persona;
    private final Pitch pitch;
    private final boolean useCaseFailed;

    public PersonaOutputData(boolean useCaseFailed, Persona persona, Pitch pitch, String username, String password) {
        this.useCaseFailed = useCaseFailed;
        this.persona = persona;
        this.pitch = pitch;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Persona getPersona() {
        return persona;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
