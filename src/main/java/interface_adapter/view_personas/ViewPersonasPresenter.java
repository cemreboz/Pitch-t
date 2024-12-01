package interface_adapter.view_personas;

import entity.Persona;
import use_case.view_personas.ViewPersonasOutputBoundary;
import use_case.view_personas.ViewPersonasOutputData;
import java.util.List;

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
        // Extract list of personas from output data
        List<Persona> personas = outputData.getPersonas();

        // Create a new state object encapsulating the personas
        ViewPersonasState state = new ViewPersonasState(personas);

        // Update the view model with the newly created state
        viewModel.setState(state);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Create a state object with an error message
        ViewPersonasState state = new ViewPersonasState();
        state.setErrorMessage(errorMessage);

        // Update the view model with the error state
        viewModel.setState(state);
    }
}