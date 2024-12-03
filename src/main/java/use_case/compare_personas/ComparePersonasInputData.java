package use_case.compare_personas;

import java.util.Arrays;
import java.util.List;

import entity.Persona;
import entity.Pitch;

/**
 * The Input Data for the Compare Personas Use Case.
 */
public class ComparePersonasInputData {
    private final Pitch currentPitch;
    private final List<Persona> personas;

    public ComparePersonasInputData(Persona selectedPersona1, Persona selectedPersona2, Pitch currentPitch) {
        this.personas = Arrays.asList(selectedPersona1, selectedPersona2);
        this.currentPitch = currentPitch;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public Pitch getCurrentPitch() {
        return currentPitch;
    }
}