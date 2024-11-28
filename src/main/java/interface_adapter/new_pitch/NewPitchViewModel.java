package interface_adapter.new_pitch;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the New Pitch View.
 */
public class NewPitchViewModel extends ViewModel<NewPitchState> {

    public NewPitchViewModel() {
        super("new pitch");
        setState(new NewPitchState());
    }

    /**
     * Updates the view with the current state.
     * @param state the updated state.
     */
    public void updateView(NewPitchState state) {
        setState(state);
    }

}