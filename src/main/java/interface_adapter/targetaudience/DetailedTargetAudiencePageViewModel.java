package interface_adapter.targetaudience;

import interface_adapter.ViewModel;

/**
 * The View Model for the Dashboard view.
 */
public class DetailedTargetAudiencePageViewModel extends ViewModel<DetailedTargetAudienceState> {

    public DetailedTargetAudiencePageViewModel() {
        super("detailedTA");
        setState(new DetailedTargetAudienceState());
    }
}
