package interface_adapter.targetaudience;

import java.util.List;

import entity.Pitch;
import interface_adapter.pitch.PitchState;
import interface_adapter.pitch.PitchViewModel;
import use_case.set_targetaudience.TargetAudienceOuputData;
import use_case.set_targetaudience.TargetAudienceOutputBoundary;

/**
 * Presenter for the General Target Audience Use Case.
 */
public class TargetAudiencePresenter implements TargetAudienceOutputBoundary {
    private final PitchViewModel viewModel;

    public TargetAudiencePresenter(PitchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Method for getting the Target Audience.
     * @param pitch the pitch itself.
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

    /**
     * Success view.
     *
     * @param outputData the outputdata for General Target Audience.
     */
    @Override
    public void prepareSuccessView(TargetAudienceOuputData outputData) {
        final PitchState state = viewModel.getState();
        state.setLoading(false);

        state.

    }

    /**
     * Failure View.
     *
     * @param errorMessage the error message.
     */
    @Override
    public void prepareFailView(String errorMessage) {

    }
}
