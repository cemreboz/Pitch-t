package interface_adapter.vision;

import use_case.generate_visuals.GenerateVisualOutputBoundary;
import use_case.generate_visuals.GenerateVisualOutputData;

/**
 * Presenter to format and update the view model with output data.
 */
public class VisionPresenter implements GenerateVisualOutputBoundary {

    private final VisionViewModel viewModel;

    public VisionPresenter(VisionViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(GenerateVisualOutputData outputData) {
        // Update the VisionState in the ViewModel
        final VisionState currentState = viewModel.getState();
        currentState.setGeneratedImageUrl(outputData.getImagePath());
        currentState.setErrorMessage(null);
        currentState.setUsername(outputData.getUsername());
        currentState.setPassword(outputData.getPassword());

        // Notify listeners that the state has been updated
        viewModel.updateView(currentState);
        // For testing purposes
        System.out.println("Presenter: Generated image path = " + outputData.getImagePath());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Update the VisionState in the ViewModel with the error message
        final VisionState currentState = viewModel.getState();
        currentState.setGeneratedImageUrl(null);
        currentState.setErrorMessage(errorMessage);

        // Notify listeners that the state has been updated
        viewModel.updateView(currentState);
    }

}
