package interface_adapter.new_pitch;

import use_case.create_pitch.NewPitchOutputBoundary;
import use_case.create_pitch.NewPitchOutputData;

/**
 * The presenter for the New Pitch Use Case.
 */
public class NewPitchPresenter implements NewPitchOutputBoundary {

    private final NewPitchViewModel newPitchViewModel;

    public NewPitchPresenter(NewPitchViewModel newPitchViewModel) {
        this.newPitchViewModel = newPitchViewModel;
    }

    /**
     * Handles the result of the New Pitch Use Case.
     * @param outputData the output data from the use case execution.
     */
    @Override
    public void presentOutput(NewPitchOutputData outputData) {
        NewPitchState state = newPitchViewModel.getState();

        // Update the state based on the use case outcome
        if (!outputData.isSuccess()) {
            state.setErrorMessage(outputData.getMessage());
            state.setSuccess(false);
        }
        else {
            state.setErrorMessage(null);
            state.setSuccess(true);
        }

        // Update the ViewModel to reflect changes in the state
        newPitchViewModel.setState(state);
        newPitchViewModel.firePropertyChanged();
    }
}