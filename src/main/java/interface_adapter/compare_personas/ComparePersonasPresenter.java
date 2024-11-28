package interface_adapter.compare_personas;

import use_case.compare_personas.ComparePersonasOutputBoundary;
import use_case.compare_personas.ComparePersonasOutputData;

/**
 * Presenter for the Compare Personas Use Case.
 */
public class ComparePersonasPresenter implements ComparePersonasOutputBoundary {
    private String comparisonResult;

    @Override
    public void prepareSuccessView(ComparePersonasOutputData outputData) {
        comparisonResult = "Comparison Results: \n" + outputData.getComparisonResults().toString();
        // Additional logic to update the view can be added here.
    }

    @Override
    public void prepareFailView(String errorMessage) {
        comparisonResult = "Error: " + errorMessage;
        // Additional logic to handle failure in the view can be added here.
    }

    public String getComparisonResult() {
        return comparisonResult;
    }
}