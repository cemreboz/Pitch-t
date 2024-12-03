package use_case.create_pitch;

/**
 * Output boundary for the Create Pitch use case.
 * Used for returning data from the interactor to the controller.
 */
public interface CreateNewPitchOutputBoundary {

    /**
     * Prepares the success view for the create new pitch use case.
     * @param outputData the output data
     */
    void prepareSuccessView(CreateNewPitchOutputData outputData);

    /**
     * Prepares the failure view for the create new pitch use case.
     * @param errorMessage explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
