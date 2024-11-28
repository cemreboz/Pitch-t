package use_case.new_pitch;

import java.util.List;

import entity.User;


/**
 * Input data for the Create Pitch use case.
 * Contains the necessary information to create a pitch.
 */
public class NewPitchInputData {

    private final String username;

    public NewPitchInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
