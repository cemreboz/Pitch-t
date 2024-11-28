package interface_adapter.targetaudience;

import java.util.List;

import entity.DetailedTargetAudience;
import use_case.set_targetaudience.DetailedInteractor;
import use_case.set_targetaudience.DetailedOutputBoundary;
import use_case.set_targetaudience.DetailedOutputData;

/**
 * The Presenter for the Detailed Target Audience Use Case.
 */
public class DetailedTargetAudiencePresenter implements DetailedOutputBoundary {
    private final DetailedTargetAudiencePageViewModel viewModel;

    public DetailedTargetAudiencePresenter(DetailedTargetAudiencePageViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the success view for the DetailedTA.
     *
     * @param outputData The output data containing the detailed target audience.
     */
    @Override
    public void prepareSuccessView(DetailedOutputData outputData) {
        final DetailedTargetAudienceState detailedState = viewModel.getState();
        final DetailedTargetAudience detailedTargetAudience = outputData.getDetailedTargetAudience();

        // Update the state with the detailed target audience data
        detailedState.setDetailedTargetAudience(detailedTargetAudience);
        detailedState.setLoading(false);

        // Notify the view model of the state update
        viewModel.setState(detailedState);
        viewModel.firePropertyChanged();
    }

    /**
     * Prepares the fail view with an error message.
     *
     * @param errorMessage The error message to display.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final DetailedTargetAudienceState detailedState = viewModel.getState();

        // Update the state with the error message
        detailedState.setErrorMessage(errorMessage);
        detailedState.setLoading(false);

        // Notify the view model of the state update
        viewModel.setState(detailedState);
        viewModel.firePropertyChanged();
    }

    /**
     * Fetches the detailed target audience and updates the view model.
     *
     * @param audienceCategory The category that the detailed target audience is based on.
     * @param interactor       The interactor from the use case layer.
     */
    public void fetchDetailedTargetAudience(String audienceCategory, DetailedInteractor interactor) {
        final DetailedTargetAudienceState detailedState = viewModel.getState();
        detailedState.setLoading(true);

        try {
            final List<DetailedTargetAudience> response = interactor.fetchDetailedTargetAudience(audienceCategory);

            final DetailedOutputData outputData = new DetailedOutputData(String.valueOf(response));

            prepareSuccessView(outputData);
        }
        catch (Exception exception) {
            prepareFailView("Failed to fetch detailed target audience: " + exception.getMessage());
        }

    }
}
