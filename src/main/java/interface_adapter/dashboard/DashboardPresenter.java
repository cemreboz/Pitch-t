package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import interface_adapter.pitch.PitchState;
import interface_adapter.pitch.PitchViewModel;
import use_case.dashboard_show_pitch.DashboardOutputBoundary;
import use_case.dashboard_show_pitch.DashboardOutputData;

/**
 * The presenter for dashboard show pitch use case.
 */
public class DashboardPresenter implements DashboardOutputBoundary {

    private DashboardViewModel dashboardViewModel;
    private PitchViewModel pitchViewModel;
    private ViewManagerModel viewManagerModel;

    public DashboardPresenter(DashboardViewModel dashboardViewModel,
                              PitchViewModel pitchViewModel, ViewManagerModel viewManagerModel) {
        this.dashboardViewModel = dashboardViewModel;
        this.pitchViewModel = pitchViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(DashboardOutputData response) {
        final PitchState pitchState = pitchViewModel.getState();
        pitchState.setPitch(response.getPitch());
        pitchState.setUsername(response.getUsername());
        pitchState.setPassword(response.getPassword());

        pitchViewModel.setState(pitchState);
        pitchViewModel.firePropertyChanged();

        this.viewManagerModel.setState(pitchViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final DashboardState dashboardState = dashboardViewModel.getState();
        dashboardState.setPitchLoadError(errorMessage);
        dashboardViewModel.firePropertyChanged();
    }
}
