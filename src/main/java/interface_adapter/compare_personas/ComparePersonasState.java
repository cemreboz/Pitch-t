package interface_adapter.compare_personas;

import entity.Persona;

public class ComparePersonasState {
    private Persona persona1;
    private Persona persona2;
    private String persona1Opinion;
    private String persona2Opinion;
    private String similarities;
    private String differences;

    // Getters and Setters
    public Persona getPersona1() {
        return persona1;
    }

    public void setPersona1(Persona persona1) {
        this.persona1 = persona1;
    }

    public Persona getPersona2() {
        return persona2;
    }

    public void setPersona2(Persona persona2) {
        this.persona2 = persona2;
    }

    public String getPersona1Opinion() {
        return persona1Opinion;
    }

    public void setPersona1Opinion(String persona1Opinion) {
        this.persona1Opinion = persona1Opinion;
    }

    public String getPersona2Opinion() {
        return persona2Opinion;
    }

    public void setPersona2Opinion(String persona2Opinion) {
        this.persona2Opinion = persona2Opinion;
    }

    public String getSimilarities() {
        return similarities;
    }

    public void setSimilarities(String similarities) {
        this.similarities = similarities;
    }

    public String getDifferences() {
        return differences;
    }

    public void setDifferences(String differences) {
        this.differences = differences;
    }
}