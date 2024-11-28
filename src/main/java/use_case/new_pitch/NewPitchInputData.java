package use_case.new_pitch;

import java.util.List;

import entity.User;


/**
 * Input data for the Create Pitch use case.
 * Contains the necessary information to create a pitch.
 */
public class NewPitchInputData {

    private final String username;
    private final String password;

    public NewPitchInputData(String username, String password) {
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
