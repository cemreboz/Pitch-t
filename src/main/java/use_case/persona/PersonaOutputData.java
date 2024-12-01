package use_case.persona;

import entity.Persona;

public class PersonaOutputData {

    private final String username;
    private final String password;
    private final Persona persona;
    private final boolean useCaseFailed;

    public PersonaOutputData(boolean useCaseFailed, Persona persona, String username, String password) {
        this.useCaseFailed = useCaseFailed;
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
        return persona;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
