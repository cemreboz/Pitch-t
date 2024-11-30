package interface_adapter.compare_personas;

import use_case.compare_personas.ComparePersonasOutputBoundary;
import use_case.compare_personas.ComparePersonasOutputData;

/**
 * Presenter for the Compare Personas Use Case.
 * Prepares the comparison result for the view.
 */
public class ComparePersonasPresenter implements ComparePersonasOutputBoundary {

    private final ComparePersonasViewModel comparePersonasViewModel;

    public ComparePersonasPresenter(ComparePersonasViewModel comparePersonasViewModel) {
        this.comparePersonasViewModel = comparePersonasViewModel;
    }

    @Override
    public void presentComparison(ComparePersonasOutputData outputData) {
        // Set the comparison results in the ViewModel
        ComparePersonasState state = comparePersonasViewModel.getState();
        state.setPersona1Opinion(outputData.getPersona1Opinion());
        state.setPersona2Opinion(outputData.getPersona2Opinion());
        state.setSimilarities(outputData.getSimilarities());
        state.setDifferences(outputData.getDifferences());

        // Update the ViewModel with the new state
        comparePersonasViewModel.setState(state);
        comparePersonasViewModel.firePropertyChanged();
    }
}
