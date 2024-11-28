package interface_adapter.create_pitch;

import interface_adapter.new_pitch.NewPitchState;
import interface_adapter.new_pitch.NewPitchViewModel;
import use_case.new_pitch.NewPitchOutputData;

/**
 * The presenter for the Create New Pitch Use Case.
 */
public class CreateNewPitchPresenter {


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
