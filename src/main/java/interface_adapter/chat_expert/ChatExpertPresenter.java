package interface_adapter.chat_expert;

import interface_adapter.expert.ExpertViewModel;
import use_case.chat_expert.ChatExpertOutputBoundary;
import use_case.chat_expert.ChatExpertOutputData;

/**
 * Presenter for the Chat with Expert use case.
 * Prepares data for display in the view.
 */
public class ChatExpertPresenter implements ChatExpertOutputBoundary {

    private final ExpertViewModel viewModel;

    /**
     * Constructs a ChatExpertPresenter object.
     *
     * @param viewModel The view model to update with output data.
     */
    public ChatExpertPresenter(ExpertViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares and updates the view model with chat output data.
     *
     * @param outputData The output data containing expert response
     *                   and updated chat history.
     */
    @Override
    public void presentChat(ChatExpertOutputData outputData) {
        // Update the view model with new data
        viewModel.getState().setExpertResponse(outputData.getExpertResponse());
        viewModel.getState().setChatHistory(outputData.getChatHistory());
    }
}
