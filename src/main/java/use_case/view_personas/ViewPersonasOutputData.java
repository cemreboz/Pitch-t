package use_case.view_personas;

import java.util.List;

import entity.Persona;
import entity.Pitch;

/**
 * Output Data for the View Personas Use Case.
 */
public class ViewPersonasOutputData {
    private final List<Persona> personas;
    private final Pitch pitch;
    private final String username;
    private final String password;

    /**
     * Constructs the output data with the list of personas.
     *
     * @param personas the list of personas.
     * @param thisPitch current pitch
     * @param username current username
     * @param password current password
     */
    public ViewPersonasOutputData(List<Persona> personas, Pitch thisPitch, String username, String password) {
        this.personas = personas;
        this.pitch = thisPitch;
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the list of personas.
     *
     * @return the personas.
     */
    public List<Persona> getPersonas() {

        return personas;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
