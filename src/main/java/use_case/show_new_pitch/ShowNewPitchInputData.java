package use_case.show_new_pitch;


/**
 * Input data for the Show New Pitch use case.
 * Contains the necessary information to view a pitch.
 */
public class ShowNewPitchInputData {

    private final String username;
    private final String password;

    public ShowNewPitchInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
