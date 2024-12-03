package interface_adapter.vision;

import interface_adapter.ViewManagerModel;
import use_case.generate_visuals.GenerateVisualOutputBoundary;
import use_case.generate_visuals.GenerateVisualOutputData;

/**
 * Presenter to format and update the view model with output data.
 */
public class VisionPresenter implements GenerateVisualOutputBoundary {

    private final VisionViewModel viewModel;
    private ViewManagerModel viewManagerModel;

    public VisionPresenter(VisionViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(GenerateVisualOutputData outputData) {
        // Update the VisionState in the ViewModel
        final VisionState currentState = viewModel.getState();
        currentState.setGeneratedImageUrl(outputData.getImagePath());
        currentState.setErrorMessage(null);

        // Notify listeners that the state has been updated
        viewModel.updateView(currentState);
        // For testing purposes
        System.out.println("Presenter: Generated image path = " + outputData.getImagePath());

        viewModel.setState(currentState);
        viewModel.firePropertyChanged();

        this.viewManagerModel.setState(viewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Update the VisionState in the ViewModel with the error message
        final VisionState currentState = viewModel.getState();
        currentState.setGeneratedImageUrl(null);
        currentState.setErrorMessage(errorMessage);

        // Notify listeners that the state has been updated
        viewModel.setState(currentState);
        viewModel.updateView(currentState);

        this.viewManagerModel.setState(viewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

}
