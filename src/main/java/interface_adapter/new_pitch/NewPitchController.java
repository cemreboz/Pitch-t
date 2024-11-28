package interface_adapter.new_pitch;

import use_case.new_pitch.NewPitchInputBoundary;
import use_case.new_pitch.NewPitchInputData;

import java.util.List;

public class NewPitchController {

    private final NewPitchInputBoundary newPitchUseCaseInteractor;

    public NewPitchController(NewPitchInputBoundary newPitchUseCaseInteractor) {
        this.newPitchUseCaseInteractor = newPitchUseCaseInteractor;
    }

    /**
     * Executes the Create New Pitch Use Case.
     *
     * @param username is the current username
     */
    public void execute(String username) {
        // Creating an input data object for the use case
        final NewPitchInputData newPitchInputData = new NewPitchInputData(username);

        // Execute the use case to create the pitch
        newPitchUseCaseInteractor.execute(newPitchInputData);
    }

    /**
     * Executes the "switch to PitchView" Use Case.
     */
    public void switchToPitchView() {

    }
}