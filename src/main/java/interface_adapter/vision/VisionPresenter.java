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
        // Successfully generated image, update the ViewModel
        viewModel.setImagePath(outputData.getImagePath());
        viewModel.setErrorMessage(null);
        viewModel.notifyListeners();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Error occurred while generating the image
        viewModel.setImagePath(null);
        viewModel.setErrorMessage(errorMessage);
        viewModel.notifyListeners();
    }
}