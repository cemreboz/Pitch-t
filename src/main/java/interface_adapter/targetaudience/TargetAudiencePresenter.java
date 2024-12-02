package interface_adapter.targetaudience;

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
     * Success view.
     *
     * @param outputData the outputdata for General Target Audience.
     */
    @Override
    public void prepareSuccessView(TargetAudienceOuputData outputData) throws Exception {
        final PitchState state = viewModel.getState();

        state.setTargetAudience(outputData.getTargetAudience());
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    /**
     * Failure View.
     *
     * @param errorMessage the error message.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final PitchState state = viewModel.getState();
        state.setErrorMessage(errorMessage);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
