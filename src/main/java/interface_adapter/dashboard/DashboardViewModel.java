package interface_adapter.dashboard;

import interface_adapter.ViewModel;

/**
 * The View Model for the Dashboard view.
 */
public class DashboardViewModel extends ViewModel<DashboardState> {

    public DashboardViewModel() {
        super("dashboard");
        setState(new DashboardState());
    }
}
