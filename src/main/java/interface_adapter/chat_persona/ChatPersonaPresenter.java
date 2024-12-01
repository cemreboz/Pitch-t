package interface_adapter.chat_persona;

import use_case.chat_persona.ChatPersonaOutputBoundary;
import use_case.chat_persona.ChatPersonaOutputData;

/**
 * Presenter for the chat_persona use case.
 */
public class ChatPersonaPresenter implements ChatPersonaOutputBoundary {
    private final ChatPersonaViewModel viewModel;

    public ChatPersonaPresenter(ChatPersonaViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ChatPersonaOutputData outputData) {
        ChatPersonaState state = viewModel.getState();
        state.setChatHistory(outputData.getChatHistory());
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.err.println("Error in ChatPersonaPresenter: " + errorMessage);
        // Handle error state if needed
    }
}
