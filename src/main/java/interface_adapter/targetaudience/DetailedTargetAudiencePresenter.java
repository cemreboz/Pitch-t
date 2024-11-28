package interface_adapter.targetaudience;

import entity.DetailedTargetAudience;
import use_case.set_targetaudience.DetailedInteractor;

/**
 * Presenter for the Detailed Target Audience Use Case.
 */
public class DetailedTargetAudiencePresenter {
    private final DetailedTargetAudiencePageViewModel viewModel;
    private final DetailedInteractor interactor;

    public DetailedTargetAudiencePresenter(DetailedTargetAudiencePageViewModel viewModel, DetailedInteractor interactor) {
        this.viewModel = viewModel;
        this.interactor = interactor;
    }

    /**
     * Fetches the detailed target audience for a given category.
     *
     * @param audienceCategory The category of the target audience.
     */
    public void fetchDetailedTargetAudience(String audienceCategory) {
        try {
            // Fetch from interactor
            final var detailedAudience = interactor.fetchDetailedTargetAudience(audienceCategory);

            // Update ViewModel
            viewModel.updateDetailedTargetAudience((DetailedTargetAudience) detailedAudience);
        }
        catch (Exception exception) {
            // Handle error in fetching
            viewModel.setErrorMessage("Error fetching detailed target audience: " + exception.getMessage());
        }
    }
}
