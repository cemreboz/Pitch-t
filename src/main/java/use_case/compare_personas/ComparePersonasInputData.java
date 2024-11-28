package use_case.compare_personas;

import entity.Persona;
import java.util.List;

/**
 * The Input Data for the Compare Personas Use Case.
 */
public class ComparePersonasInputData {
    private final List<Persona> personas;

    public ComparePersonasInputData(List<Persona> personas) {
        this.personas = personas;
    }

    public List<Persona> getPersonas() {
        return personas;
    }
}