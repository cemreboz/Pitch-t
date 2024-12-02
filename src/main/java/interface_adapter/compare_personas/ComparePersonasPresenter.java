package interface_adapter.compare_personas;

import use_case.compare_personas.ComparePersonasOutputBoundary;
import use_case.compare_personas.ComparePersonasOutputData;
import interface_adapter.ViewManagerModel;

/**
 * Presenter for the Compare Personas use case.
 */
public class ComparePersonasPresenter implements ComparePersonasOutputBoundary {

    private final ComparePersonasViewModel compareViewModel;
    private final ViewManagerModel viewManagerModel;

    public ComparePersonasPresenter(ComparePersonasViewModel compareViewModel, ViewManagerModel viewManagerModel) {
        this.compareViewModel = compareViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ComparePersonasOutputData outputData) {
        // Create a new ComparePersonasState based on the output data
        ComparePersonasState state = new ComparePersonasState();
        state.setPersona1(outputData.getPersona1());
        state.setPersona2(outputData.getPersona2());
        state.setPersona1Opinion(outputData.getPersona1Opinion());
        state.setPersona2Opinion(outputData.getPersona2Opinion());
        state.setSimilarities(outputData.getSimilarities());
        state.setDifferences(outputData.getDifferences());
        state.setErrorMessage(null); // No error in the success case

        // Update the view model state and notify the UI components
        compareViewModel.setState(state);
        compareViewModel.firePropertyChanged();

        // Notify the ViewManagerModel to change the view to the persona comparison view
        viewManagerModel.setState(compareViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Set the error message in the state
        ComparePersonasState state = new ComparePersonasState();
        state.setErrorMessage(errorMessage);

        // Update the view model state and notify the UI components
        compareViewModel.setState(state);
        compareViewModel.firePropertyChanged();
    }
}
