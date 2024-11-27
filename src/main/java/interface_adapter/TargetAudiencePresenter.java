package interface_adapter;

import java.util.List;

import entity.Pitch;
import use_case.set_targetaudience.TargetAudienceInteractor;

/**
 * Presenter Class for Target Audience.
 */
public class TargetAudiencePresenter {
    private final TargetAudienceView view;
    private final TargetAudienceInteractor interactor;

    public TargetAudiencePresenter(TargetAudienceView view, TargetAudienceInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    /**
     * Method for fetchTargetAudience.
     * @param pitch from teh pitch class.
     * @throws Exception if there is an exception.
     * @throws IllegalArgumentException if the pitch data is invalid.
     */
    public void fetchTargetAudience(Pitch pitch) throws Exception {
        view.showLoading();

        // Validate the pitch
        if (pitch == null || pitch.getName() == null || pitch.getDescription() == null) {
            throw new IllegalArgumentException("Invalid pitch data.");
        }

        // Fetch the target audience from the Interactor
        final String targetAudienceResponse = interactor.generateTargetAudience(pitch);
        final List<String> targetAudience = List.of(targetAudienceResponse.split(";"));

        // Update the View
        view.hideLoading();
        view.displayTargetAudience(targetAudience);

    }
}
