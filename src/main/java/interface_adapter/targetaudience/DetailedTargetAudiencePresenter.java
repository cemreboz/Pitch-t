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
        if (outputData.getDetailedTargetAudience() == null || outputData.getDetailedTargetAudience().isEmpty()) {
            System.out.println("DetailedTargetAudience list is empty or null!");
        } else {
            System.out.println("DetailedTargetAudience received: " + outputData.getDetailedTargetAudience());
        }

        final DetailedTargetAudienceState detailedState = viewModel.getState();
        detailedState.setDetailedTargetAudience(outputData.getDetailedTargetAudience());

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
        detailedState.setErrorMessage(errorMessage);

        viewModel.setState(detailedState);
        viewModel.firePropertyChanged();
    }

}
