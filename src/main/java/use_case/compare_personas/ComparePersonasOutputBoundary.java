package use_case.compare_personas;

import use_case.compare_personas.ComparePersonasOutputData;

/**
 * Output Boundary for the Compare Personas Use Case.
 */
public interface ComparePersonasOutputBoundary {
    /**
     * Prepares the view for a successful comparison of personas.
     *
     * @param outputData the output data containing comparison details
     */
    void prepareSuccessView(ComparePersonasOutputData outputData);

    /**
     * Prepares the view for a failed comparison attempt.
     *
     * @param errorMessage the reason for the failure
     */
    void prepareFailView(String errorMessage);
}