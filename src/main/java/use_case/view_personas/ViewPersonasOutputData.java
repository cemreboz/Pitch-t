package use_case.view_personas;

import entity.Persona;
import java.util.List;

/**
 * Output Data for the View Personas Use Case.
 */
public class ViewPersonasOutputData {
    private final List<Persona> personas;

    /**
     * Constructs the output data with the list of personas.
     *
     * @param personas the list of personas.
     */
    public ViewPersonasOutputData(List<Persona> personas) {
        this.personas = personas;
    }

    /**
     * Retrieves the list of personas.
     *
     * @return the personas.
     */
    public List<Persona> getPersonas() {
        return personas;
    }
}
