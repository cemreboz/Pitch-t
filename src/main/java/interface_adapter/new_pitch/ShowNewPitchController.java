package interface_adapter.new_pitch;

import use_case.show_new_pitch.ShowNewPitchInputBoundary;
import use_case.show_new_pitch.ShowNewPitchInputData;

public class ShowNewPitchController {

    private final ShowNewPitchInputBoundary showNewPitchUseCaseInputBoundary;

    public ShowNewPitchController(ShowNewPitchInputBoundary showNewPitchUseCaseInputBoundary) {
        this.showNewPitchUseCaseInputBoundary = showNewPitchUseCaseInputBoundary;
    }

    /**
     * Executes the Create New Pitch Use Case.
     *
     * @param username is the current username
     * @param password is the current password
     */
    public void execute(String username, String password) {
        // Creating an input data object for the use case
        final ShowNewPitchInputData showNewPitchInputData = new ShowNewPitchInputData(username, password);

        // Execute the use case to create the pitch
        showNewPitchUseCaseInputBoundary.execute(showNewPitchInputData);
    }

    /**
     * Executes the "switch to PitchView" Use Case.
     */
    public void switchToPitchView() {

    }
}
