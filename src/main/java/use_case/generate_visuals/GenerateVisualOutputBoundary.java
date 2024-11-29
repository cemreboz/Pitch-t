package use_case.generate_visuals;

/**
 * The output boundary for the Change Password Use Case.
 */
public interface GenerateVisualOutputBoundary {
    /**
     * Prepares the success view for the Generate Visual Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(GenerateVisualOutputData outputData);

    /**
     * Prepares the failure view for the Generate Visual Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
