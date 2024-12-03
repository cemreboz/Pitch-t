package interface_adapter.chat_persona;

import interface_adapter.ViewManagerModel;
import interface_adapter.persona.PersonaState;
import interface_adapter.persona.PersonaViewModel;
import use_case.chat_persona.ChatPersonaOutputBoundary;
import use_case.chat_persona.ChatPersonaOutputData;

/**
 * Presenter for the chat_persona use case.
 */
public class ChatPersonaPresenter implements ChatPersonaOutputBoundary {

    private final PersonaViewModel viewModel;
    private ViewManagerModel viewManagerModel;

    public ChatPersonaPresenter(PersonaViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentChat(ChatPersonaOutputData outputData) {
        // Update the view model with new data
        final PersonaState state = viewModel.getState();
        state.setChatHistory(outputData.getChatHistory());

        viewModel.firePropertyChanged();
        // viewManagerModel.firePropertyChanged();
    }
}
