package interface_adapter.vision;

import interface_adapter.ViewModel;

/**
 * The view model for Vision.
 */
public class VisionViewModel extends ViewModel<VisionState> {

    public VisionViewModel() {
        super("vision");
        setState(new VisionState());
    }

    /**
     * Updates the view with the current state.
     * @param state the updated state.
     */
    public void updateView(VisionState state) {
        setState(state);
    }
}
