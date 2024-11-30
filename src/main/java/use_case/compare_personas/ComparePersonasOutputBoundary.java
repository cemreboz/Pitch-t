package use_case.compare_personas;

public interface ComparePersonasOutputBoundary {
    /**
     * Prepares the failure view if the comparison fails.
     *
     * @param errorMessage the error message to display
     */
    void prepareFailView(String errorMessage);

    /**
     * Prepares the comparison results for the output.
     * @param outputData The output data containing comparison details.
     */
    void presentComparison(ComparePersonasOutputData outputData);
}