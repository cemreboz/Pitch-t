package use_case.show_new_pitch;

/**
 * Output boundary for the Show New Pitch use case.
 * Used for returning data from the interactor to the controller.
 */
public interface ShowNewPitchOutputBoundary {

    /**
     * Prepares the success view for the view pitch use case.
     * @param outputData the output data
     */
    void prepareSuccessView(ShowNewPitchOutputData outputData);

    /**
     * Prepares the failure view for the view pitch use case.
     * @param errorMessage explanation of the failure
     */
    void prepareFailView(String errorMessage);
}