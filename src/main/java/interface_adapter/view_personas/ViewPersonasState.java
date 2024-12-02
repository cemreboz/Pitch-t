package interface_adapter.view_personas;

import entity.Persona;
import entity.Pitch;

import java.util.ArrayList;
import java.util.List;

/**
 * State class for the View Personas use case.
 */
public class ViewPersonasState {
    private List<Persona> personas = new ArrayList<Persona>();
    private String errorMessage;
    private Pitch thisPitch;

    // Constructors
    public ViewPersonasState() {
        // Default constructor for fail state
        this.errorMessage = "";
    }

    public ViewPersonasState(List<Persona> personas) {

        this.personas = personas;
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

    public Pitch getThisPitch() {
        return thisPitch;
    }

    public void setThisPitch(Pitch thisPitch) {}

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}