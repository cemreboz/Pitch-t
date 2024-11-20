package use_case.login;

import entity.Pitch;

import java.util.List;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final List<Pitch> pitches;

    public LoginOutputData(String username, boolean useCaseFailed, List<Pitch> pitches) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.pitches = pitches;
    }

    public String getUsername() {
        return username;
    }

    public List<Pitch> getPitches() {
        return pitches;
    }

}
