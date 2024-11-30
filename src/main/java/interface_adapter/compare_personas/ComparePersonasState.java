package interface_adapter.compare_personas;

import entity.Persona;
import java.util.List;

/**
 * State for the Compare Personas View Model.
 */
public class ComparePersonasState {
    private Persona persona1;
    private Persona persona2;
    private String persona1Opinion;
    private String persona2Opinion;
    private List<String> similarities;
    private List<String> differences;
    private String errorMessage;

    // Getters
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

    public String getErrorMessage() {
        return errorMessage;
    }

    // Setters
    public void setPersona1(Persona persona1) {
        this.persona1 = persona1;
    }

    public void setPersona2(Persona persona2) {
        this.persona2 = persona2;
    }

    public void setPersona1Opinion(String persona1Opinion) {
        this.persona1Opinion = persona1Opinion;
    }

    public void setPersona2Opinion(String persona2Opinion) {
        this.persona2Opinion = persona2Opinion;
    }

    public void setSimilarities(List<String> similarities) {
        this.similarities = similarities;
    }

    public void setDifferences(List<String> differences) {
        this.differences = differences;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}