package interface_adapter.view_personas;

import interface_adapter.ViewManagerModel;
import use_case.view_personas.ViewPersonasOutputBoundary;
import use_case.view_personas.ViewPersonasOutputData;

/**
 * Presenter for the View Personas use case.
 */
public class ViewPersonasPresenter implements ViewPersonasOutputBoundary {

    private final ViewPersonasViewModel viewPersonasViewModel;
    private final ViewManagerModel viewManagerModel;

    public ViewPersonasPresenter(ViewPersonasViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewPersonasViewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ViewPersonasOutputData outputData) {
        ViewPersonasState state = new ViewPersonasState();
        state.setPersonas(outputData.getPersonas());
        state.setThisPitch(outputData.getPitch());
        state.setUsername(outputData.getUsername());
        state.setPassword(outputData.getPassword());
        state.setErrorMessage(null); // No error in the success case

        // Update the view model with the successful output
        viewPersonasViewModel.setState(state);
        viewPersonasViewModel.firePropertyChanged();

        viewManagerModel.setState(viewPersonasViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ViewPersonasState state = new ViewPersonasState();
        state.setPersonas(null); // No personas in the failure case
        state.setErrorMessage(errorMessage);

        // Update the view model with the failure output
        viewPersonasViewModel.setState(state);
        viewPersonasViewModel.firePropertyChanged();

        viewManagerModel.setState(viewPersonasViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}