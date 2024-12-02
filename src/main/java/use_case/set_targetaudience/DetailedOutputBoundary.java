package use_case.set_targetaudience;

/**
 * Interface for DetailedTA OutputBoundary.
 */
public interface DetailedOutputBoundary {
    /**
     * Success view.
     * @param outputData the outputdata for DetailedTA.
     */
    void prepareSuccessView(DetailedOutputData outputData);

    /**
     * Failure View.
     * @param errorMessage the error message.
     */
    void prepareFailView(String errorMessage);
}
