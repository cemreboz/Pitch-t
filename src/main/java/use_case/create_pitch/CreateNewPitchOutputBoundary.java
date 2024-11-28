package use_case.create_pitch;

import use_case.new_pitch.NewPitchOutputData;

/**
 * Output boundary for the Create Pitch use case.
 * Used for returning data from the interactor to the controller.
 */
public interface CreateNewPitchOutputBoundary {

    /**
     * Sends the result of creating a new pitch and updates the view model.
     * @param outputData the result from the interactor (success/failure).
     */
    void presentOutput(NewPitchOutputData outputData);
}
