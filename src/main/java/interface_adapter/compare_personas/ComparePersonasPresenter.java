package interface_adapter.compare_personas;

import use_case.compare_personas.ComparePersonasOutputBoundary;
import use_case.compare_personas.ComparePersonasOutputData;
import interface_adapter.compare_personas.ComparePersonasViewModel;

/**
 * The Presenter for the Compare Personas use case.
 * This class takes output data and transforms it into a format suitable for the view model.
 */
public class ComparePersonasPresenter implements ComparePersonasOutputBoundary {

    private final ComparePersonasViewModel viewModel;

    public ComparePersonasPresenter(ComparePersonasViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ComparePersonasOutputData outputData) {
        // Update the view model with the result of the comparison
        viewModel.setSimilarities(outputData.getSimilarities());
        viewModel.setDifferences(outputData.getDifferences());
        viewModel.firePropertyChanged(); // Notify view that data has been updated
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Update the view model with an error message
        viewModel.setErrorMessage(errorMessage);
        viewModel.firePropertyChanged(); // Notify view that data has been updated
    }
}