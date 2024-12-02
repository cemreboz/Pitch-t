package interface_adapter.compare_personas;

import use_case.compare_personas.ComparePersonasInputBoundary;
import use_case.compare_personas.ComparePersonasInputData;

/**
 * Controller for the Compare Personas Use Case.
 */
public class ComparePersonasController {
    private final ComparePersonasInputBoundary inputBoundary;

    public ComparePersonasController(ComparePersonasInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Executes the compare personas use case.
     *
     * @param inputData the input data containing personas to be compared
     */
    public void compare(ComparePersonasInputData inputData) {
        inputBoundary.execute(inputData);
    }
}