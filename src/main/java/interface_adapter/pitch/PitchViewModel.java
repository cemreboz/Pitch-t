package interface_adapter.pitch;

import interface_adapter.ViewModel;

/**
 * The View Model for the Pitch view.
 */
public class PitchViewModel extends ViewModel<PitchState> {

    public PitchViewModel() {
        super("pitch");
        setState(new PitchState());
    }
}
