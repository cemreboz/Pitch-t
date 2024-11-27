package interface_adapter.chat_expert;

import use_case.chat_expert.ChatExpertOutputBoundary;
import use_case.chat_expert.ChatExpertOutputData;

/**
 * Presenter for the Chat with Expert use case.
 * Prepares data for display in the view.
 */
public class ChatExpertPresenter implements ChatExpertOutputBoundary {

    private final ChatExpertViewModel viewModel;

    /**
     * Constructs a ChatExpertPresenter object.
     *
     * @param viewModel The view model to update with output data.
     */
    public ChatExpertPresenter(ChatExpertViewModel viewModel) {
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
        viewModel.setExpertResponse(outputData.getExpertResponse());
        viewModel.setChatHistory(outputData.getChatHistory());
    }
}
