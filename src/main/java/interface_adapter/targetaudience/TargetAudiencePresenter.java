package interface_adapter.targetaudience;

import java.util.List;

import entity.Pitch;
import interface_adapter.pitch.PitchState;
import interface_adapter.pitch.PitchViewModel;
import use_case.set_targetaudience.TargetAudienceInteractor;

/**
 * Presenter for the General Target Audience Use Case.
 */
public class TargetAudiencePresenter {
    private final PitchViewModel viewModel;
    private final TargetAudienceInteractor interactor;

    public TargetAudiencePresenter(PitchViewModel viewModel, TargetAudienceInteractor interactor) {
        this.viewModel = viewModel;
        this.interactor = interactor;
    }

    /**
     * Fetches the target audience for a given pitch and updates the PitchViewModel.
     *
     * @param pitch The pitch for which to generate the target audience.
     */
    public void fetchTargetAudience(Pitch pitch) {
        final PitchState pitchState = viewModel.getState();
        pitchState.setLoading(true);

        try {
            final String targetAudienceResponse = interactor.generateTargetAudience(pitch);
            final List<String> targetAudience = List.of(targetAudienceResponse.split(";"));

            pitchState.setTargetAudience(targetAudience);
        }
        catch (Exception exception) {
            pitchState.setErrorMessage("Error generating target audience: " + exception.getMessage());
        }
        finally {
            pitchState.setLoading(false);
        }
    }
}
