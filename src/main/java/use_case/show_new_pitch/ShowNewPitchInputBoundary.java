package use_case.show_new_pitch;

/**
 * Input boundary for the Show New Pitch use case.
 * Defines the contract for the interactor to execute the use case logic.
 */
public interface ShowNewPitchInputBoundary {

    /**
     * Executes the Show New Pitch use case with the given input data.
     * @param inputData the data needed to create the pitch.
     */
    void execute(ShowNewPitchInputData inputData);
}
