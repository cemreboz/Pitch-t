package interface_adapter.dashboard;

import entity.Pitch;
import use_case.dashboard_show_pitch.DashboardInputBoundary;
import use_case.dashboard_show_pitch.DashboardInputData;

/**
 * The controller for the Dashboard (show pitch) class.
 */
public class DashboardController {

    private final DashboardInputBoundary dashboardUseCaseInteractor;

    public DashboardController(DashboardInputBoundary dashboardUseCaseInteractor) {
        this.dashboardUseCaseInteractor = dashboardUseCaseInteractor;
    }

    /**
     * Executes the dashboard show pitch use case.
     * @param pitch to be shown
     * @param username username of user
     * @param password password of user
     */
    public void execute(Pitch pitch, String username, String password) {
        final DashboardInputData dashboardInputData = new DashboardInputData(pitch, username, password);
        dashboardUseCaseInteractor.execute(dashboardInputData);
    }

}
