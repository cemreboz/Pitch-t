package use_case.persona;

/**
 * The output boundary for the persona chat use case.
 */
public interface PersonaOutputBoundary {

    /**
     * Prepares the success view for the persona show chat use case.
     * @param outputData the output data
     */
    void prepareSuccessView(PersonaOutputData outputData);

    /**
     * Prepares the failure view for the persona show chat use case.
     * @param errorMessage explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
