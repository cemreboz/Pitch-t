package interface_adapter.compare_personas;

import use_case.compare_personas.ComparePersonasInputBoundary;
import use_case.compare_personas.ComparePersonasInputData;

/**
 * Controller for the Compare Personas Use Case.
 */
public class ComparePersonasController {

    private final ComparePersonasInputBoundary interactor;

    public ComparePersonasController(ComparePersonasInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void comparePersonas(ComparePersonasInputData inputData) {
        interactor.execute(inputData);
    }
}
