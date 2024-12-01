package use_case.view_personas;

/**
 * Input Boundary for the View Personas Use Case.
 */
public interface ViewPersonasInputBoundary {
    /**
     * Executes the view personas use case with the given input data.
     *
     * @param inputData the input data containing the pitch ID.
     */
    void execute(ViewPersonasInputData inputData);
}
