package use_case.view_personas;

/**
 * Output Boundary for the View Personas Use Case.
 */
public interface ViewPersonasOutputBoundary {
    /**
     * Presents the successful output data.
     *
     * @param outputData the output data containing personas.
     */
    void presentSuccess(ViewPersonasOutputData outputData);

    /**
     * Presents an error message in case of failure.
     *
     * @param errorMessage the error message.
     */
    void presentFailure(String errorMessage);
}
