package interface_adapter.compare_personas;

import use_case.compare_personas.ComparePersonasOutputBoundary;
import use_case.compare_personas.ComparePersonasOutputData;

public class ComparePersonasPresenter implements ComparePersonasOutputBoundary {
    private final ComparePersonasViewModel viewModel;

    public ComparePersonasPresenter(ComparePersonasViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentComparison(ComparePersonasOutputData outputData) {
        ComparePersonasState state = viewModel.getState();

        state.setPersona1(outputData.getPersona1());
        state.setPersona2(outputData.getPersona2());
        state.setPersona1Opinion(outputData.getPersona1Opinion());
        state.setPersona2Opinion(outputData.getPersona2Opinion());
        state.setSimilarities(outputData.getSimilarities());
        state.setDifferences(outputData.getDifferences());

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ComparePersonasState state = viewModel.getState();

        // Set an error message in the state
        state.setErrorMessage(errorMessage);

        // Update the view model state
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}