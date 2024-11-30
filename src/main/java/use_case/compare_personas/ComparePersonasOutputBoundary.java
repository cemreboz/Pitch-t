package use_case.compare_personas;

public interface ComparePersonasOutputBoundary {
    /**
     * Prepares the success view by passing the output data.
     *
     * @param outputData the result of the personas comparison
     */
    void prepareSuccessView(ComparePersonasOutputData outputData);

    /**
     * Prepares the failure view if the comparison fails.
     *
     * @param errorMessage the error message to display
     */
    void prepareFailView(String errorMessage);
}