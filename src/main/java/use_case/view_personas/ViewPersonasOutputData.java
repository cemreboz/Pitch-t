package use_case.view_personas;

import java.util.List;

import entity.Persona;

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
