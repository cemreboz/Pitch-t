package use_case.persona;

import entity.Persona;
import entity.Pitch;

/**
 * The input data for the Persona use case.
 */
public class PersonaInputData {
    private final String username;
    private final String password;
    private final Persona persona;
    private final Pitch pitch;

    public PersonaInputData(Persona persona, Pitch pitch, String username, String password) {
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
}
