package interface_adapter.view_personas;

import entity.Persona;

import java.util.ArrayList;
import java.util.List;

/**
 * State class for the View Personas use case.
 */
public class ViewPersonasState {
    private List<Persona> personas = new ArrayList<Persona>();
    private String errorMessage;

    // Constructors
    public ViewPersonasState() {
        // Default constructor for fail state
    }

    public ViewPersonasState(List<Persona> personas) {
        this.personas = personas;
        this.errorMessage = "";
    }

    // Getters and Setters
    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}