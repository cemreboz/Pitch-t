package interface_adapter.new_pitch;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.new_pitch.NewPitchOutputBoundary;
import use_case.new_pitch.NewPitchOutputData;

/**
 * The presenter for the New Pitch Use Case.
 */
public class NewPitchPresenter implements NewPitchOutputBoundary {

    private final NewPitchViewModel newPitchViewModel;
    private DashboardViewModel dashboardViewModel;
    private ViewManagerModel viewManagerModel;

    public NewPitchPresenter(NewPitchViewModel newPitchViewModel,
                             DashboardViewModel dashboardViewModel, ViewManagerModel viewManagerModel) {
        this.newPitchViewModel = newPitchViewModel;
        this.dashboardViewModel = dashboardViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(NewPitchOutputData outputData) {
        final NewPitchState state = newPitchViewModel.getState();
        state.setCurrentUser(outputData.getCurrentUser());

        // Update the ViewModel to reflect changes in the state
        newPitchViewModel.setState(state);
        newPitchViewModel.firePropertyChanged();

        viewManagerModel.setState(newPitchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // assume can't fail
    }
}