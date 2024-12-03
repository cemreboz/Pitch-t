package use_case.compare_personas;

import entity.Persona;
import java.util.List;

/**
 * Output Data for the Compare Personas Use Case.
 */
public class ComparePersonasOutputData {
    private final Persona persona1;
    private final Persona persona2;
    private final String p1Opinion;
    private final String p2Opinion;
    private final List<String> similarities;
    private final List<String> differences;
    private final String username;
    private final String password;

    public ComparePersonasOutputData(Persona persona1, Persona persona2, String p1Opinion, String p2Opinion,
                                     List<String> similarities, List<String> differences, String username,
                                     String password) {
        this.persona1 = persona1;
        this.persona2 = persona2;
        this.p1Opinion = p1Opinion;
        this.p2Opinion = p2Opinion;
        this.similarities = similarities;
        this.differences = differences;
        this.username = username;
        this.password = password;
    }

    public Persona getPersona1() {
        return persona1;
    }

    public Persona getPersona2() {
        return persona2;
    }

    public String getPersona1Opinion() {
        return p1Opinion;
    }

    public String getPersona2Opinion() {
        return p2Opinion;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getSimilarities() {
        return similarities;
    }

    public List<String> getDifferences() {
        return differences;
    }
}
