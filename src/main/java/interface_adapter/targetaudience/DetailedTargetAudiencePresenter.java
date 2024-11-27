package interface_adapter.targetaudience;

import java.util.List;

import entity.DetailedTargetAudience;
import use_case.set_targetaudience.DetailedInteractor;

/**
 * Class for the Detailed TA presenter.
 */
public class DetailedTargetAudiencePresenter {
    private final DetailedTargetAudienceView view;
    private final DetailedInteractor interactor;

    public DetailedTargetAudiencePresenter(DetailedTargetAudienceView view, DetailedInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    /**
     * Method for fetching the Detailed TA.
     * @param audienceCategory the audience category that the detailed TA is based off of.
     * @throws Exception when there is an error.
     * @throws IllegalArgumentException when the Audience Category is null/empty.
     */
    public void fetchDetailedTargetAudience(String audienceCategory) throws Exception {
        view.showLoading();

        // Validate input
        if (audienceCategory == null || audienceCategory.isEmpty()) {
            throw new IllegalArgumentException("Audience category cannot be null or empty.");
        }

        // Fetch detailed target audience from the Interactor
        final List<DetailedTargetAudience> detailedTargetAudience =
                interactor.fetchDetailedTargetAudience(audienceCategory);

        // Update the View
        view.hideLoading();
        view.displayDetailedTargetAudience(detailedTargetAudience);

    }
}
