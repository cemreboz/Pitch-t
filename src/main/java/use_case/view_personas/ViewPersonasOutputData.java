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

    /**
     * Constructs the output data with the list of personas.
     *
     * @param personas the list of personas.
     */
    public ViewPersonasOutputData(List<Persona> personas, Pitch thisPitch) {
        this.personas = personas;
        this.pitch = thisPitch;
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
}
