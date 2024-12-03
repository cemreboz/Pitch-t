package interface_adapter.create_pitch;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the New Pitch View.
 */
public class CreateNewPitchViewModel extends ViewModel<CreateNewPitchState> {

    public CreateNewPitchViewModel() {
        super("new pitch");
        setState(new CreateNewPitchState());
    }

    /**
     * Updates the view with the current state.
     * @param state the updated state.
     */
    public void updateView(CreateNewPitchState state) {
        setState(state);
    }

}