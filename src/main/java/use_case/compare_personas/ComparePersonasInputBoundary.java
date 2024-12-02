package use_case.compare_personas;

import use_case.compare_personas.ComparePersonasInputData;

/**
 * Input Boundary for comparing personas.
 */
public interface ComparePersonasInputBoundary {
    /**
     * Executes the compare personas use case.
     *
     * @param inputData the input data containing personas to be compared
     */
    void execute(ComparePersonasInputData inputData);
}