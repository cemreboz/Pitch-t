package interface_adapter.chat_vision;

import interface_adapter.ViewManagerModel;
import interface_adapter.vision.VisionViewModel;
import use_case.chat_vision.ChatVisionOutputBoundary;
import use_case.chat_vision.ChatVisionOutputData;

/**
 * Presenter for the Chat with Expert use case.
 * Prepares data for display in the view.
 */
public class ChatVisionPresenter implements ChatVisionOutputBoundary {

    private final VisionViewModel viewModel;
    private ViewManagerModel viewManagerModel;

    /**
     * Constructs a ChatExpertPresenter object.
     *
     * @param viewModel The view model to update with output data.
     * @param viewManagerModel all good.
     */
    public ChatVisionPresenter(VisionViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares and updates the view model with chat output data.
     *
     * @param outputData The output data containing expert response
     *                   and updated chat history.
     */
    @Override
    public void presentChat(ChatVisionOutputData outputData) {
        // Update the view model with new data
        viewModel.getState().setVisionResponse(outputData.getVisionResponse());
        viewModel.getState().setChatHistory(outputData.getChatHistory());

        viewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();
    }
}
