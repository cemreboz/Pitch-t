package use_case.persona;

import entity.Persona;

/**
 * The input data for the Persona use case.
 */
public class PersonaInputData {
    private final String username;
    private final String password;
    private final Persona persona;

    public PersonaInputData(Persona persona, String username, String password) {
        this.persona = persona;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Persona getPersona() {
        return persona; }
}
