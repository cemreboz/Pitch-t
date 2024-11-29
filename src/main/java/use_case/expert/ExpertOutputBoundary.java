package use_case.expert;

/**
 * The output boundary for the expert use case.
 */
public interface ExpertOutputBoundary {

    /**
     * Prepares the success view for the expert show chat use case.
     * @param outputData the output data
     */
    void prepareSuccessView(ExpertOutputData outputData);

    /**
     * Prepares the failure view for the expert show chat use case.
     * @param errorMessage explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
