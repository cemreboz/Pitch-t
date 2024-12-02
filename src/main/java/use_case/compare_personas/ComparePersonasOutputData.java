package use_case.compare_personas;

import entity.Persona;
import java.util.List;

/**
 * Output Data for the Compare Personas Use Case.
 */
public class ComparePersonasOutputData {
    private final Persona persona1;
    private final Persona persona2;
    private final String persona1Opinion;
    private final String persona2Opinion;
    private final List<String> similarities;
    private final List<String> differences;

    public ComparePersonasOutputData(Persona persona1, Persona persona2, String persona1Opinion, String persona2Opinion, List<String> similarities, List<String> differences) {
        this.persona1 = persona1;
        this.persona2 = persona2;
        this.persona1Opinion = persona1Opinion;
        this.persona2Opinion = persona2Opinion;
        this.similarities = similarities;
        this.differences = differences;
    }

    public Persona getPersona1() {
        return persona1;
    }

    public Persona getPersona2() {
        return persona2;
    }

    public String getPersona1Opinion() {
        return persona1Opinion;
    }

    public String getPersona2Opinion() {
        return persona2Opinion;
    }

    public List<String> getSimilarities() {
        return similarities;
    }

    public List<String> getDifferences() {
        return differences;
    }
}