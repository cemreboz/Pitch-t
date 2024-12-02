package interface_adapter.view_personas;

import use_case.view_personas.ViewPersonasOutputBoundary;
import use_case.view_personas.ViewPersonasOutputData;

/**
 * Presenter for the View Personas use case.
 */
public class ViewPersonasPresenter implements ViewPersonasOutputBoundary {

    private final ViewPersonasViewModel viewModel;

    public ViewPersonasPresenter(ViewPersonasViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewPersonasOutputData outputData) {
        ViewPersonasState state = new ViewPersonasState();
        state.setPersonas(outputData.getPersonas());
        state.setErrorMessage(null); // No error in the success case

        // Update the view model with the successful output
        viewModel.setState(state);
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.getState().setErrorMessage(error);
    }
}