package use_case.compare_personas;

import java.util.Arrays;
import java.util.List;

import entity.Persona;

/**
 * The Input Data for the Compare Personas Use Case.
 */
public class ComparePersonasInputData {
    private final List<Persona> personas;

    public ComparePersonasInputData(Persona selectedPersona, Persona selectedPersona1) {
        this.personas = Arrays.asList(selectedPersona, selectedPersona1);
    }

    public List<Persona> getPersonas() {
        return personas;
    }
}