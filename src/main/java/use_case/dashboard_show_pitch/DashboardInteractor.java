package use_case.dashboard_show_pitch;

/**
 * The dashboard interactor, for showing pitches.
 */
public class DashboardInteractor implements DashboardInputBoundary {
    private DashboardDataAccessInterface userDataAccessObject;
    private DashboardOutputBoundary dashboardPresenter;

    public DashboardInteractor(DashboardDataAccessInterface userDataAccessObject,
                               DashboardOutputBoundary dashboardPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.dashboardPresenter = dashboardPresenter;
    }

    @Override
    public void execute(DashboardInputData dashboardInputData) {

        final DashboardOutputData dashboardOutputData = new DashboardOutputData(dashboardInputData.getPitch(),
                false, dashboardInputData.getUsername(), dashboardInputData.getPassword());
        dashboardPresenter.prepareSuccessView(dashboardOutputData);
    }

}
