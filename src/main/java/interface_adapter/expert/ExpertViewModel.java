package interface_adapter.expert;

import interface_adapter.ViewModel;

/**
 * View model for the Chat with Expert use case.
 * Holds data for display in the view layer.
 */
public class ExpertViewModel extends ViewModel<ExpertState> {

    public ExpertViewModel() {
        super("chat expert");
        setState(new ExpertState());
    }
}
