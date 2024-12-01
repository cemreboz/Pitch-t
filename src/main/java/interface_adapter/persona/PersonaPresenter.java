package interface_adapter.persona;

import interface_adapter.ViewManagerModel;
import use_case.persona.PersonaOutputBoundary;
import use_case.persona.PersonaOutputData;

/**
 * The presenter for the persona (show chat) class.
 */
public class PersonaPresenter implements PersonaOutputBoundary {

    private PersonaViewModel personaViewModel;
    private ViewManagerModel viewManagerModel;

    public PersonaPresenter(PersonaViewModel personaViewModel, ViewManagerModel viewManagerModel) {
        this.personaViewModel = personaViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(PersonaOutputData response) {
        final PersonaState state = personaViewModel.getState();
        state.setPersona(response.getPersona());
        state.setUsername(response.getUsername());
        state.setPassword(response.getPassword());

        personaViewModel.setState(state);
        personaViewModel.firePropertyChanged();

        viewManagerModel.setState(personaViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Do nothing for now, it cant really fail.
        // Personas should already be generated when we are in persona list view, this is just swapping to chat view.
    }
}
