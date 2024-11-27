package use_case.dashboard_show_pitch;

/**
 * Input boundary for dashboard interactor.
 */
public interface DashboardInputBoundary {

    /**
     * Executes the dashboard show pitch use case.
     * @param dashboardInputData the input data
     */
    void execute(DashboardInputData dashboardInputData);

}
