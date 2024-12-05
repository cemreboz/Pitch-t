package interface_adapter.view_personas;

import java.util.ArrayList;
import java.util.List;

import entity.Persona;
import entity.Pitch;

/**
 * State class for the View Personas use case.
 */
public class ViewPersonasState {
    private List<Persona> personas = new ArrayList<Persona>();
    private String username;
    private String password;
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

    public void setThisPitch(Pitch thisPitch) {
        this.thisPitch = thisPitch;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
