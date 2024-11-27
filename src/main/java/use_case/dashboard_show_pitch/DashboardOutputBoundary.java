package use_case.dashboard_show_pitch;

/**
 * The output boundary for the dashboard show pitch use case.
 */
public interface DashboardOutputBoundary {

    /**
     * Prepares the success view for the dashboard show pitch use case.
     * @param outputData the output data
     */
    void prepareSuccessView(DashboardOutputData outputData);

    /**
     * Prepares the failure view for the dashboard show pitch use case.
     * @param errorMessage explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
