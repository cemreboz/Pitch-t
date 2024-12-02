package interface_adapter.new_pitch;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_pitch.CreateNewPitchState;
import interface_adapter.create_pitch.CreateNewPitchViewModel;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.show_new_pitch.ShowNewPitchOutputBoundary;
import use_case.show_new_pitch.ShowNewPitchOutputData;

/**
 * The presenter for the View Pitch Use Case.
 */
public class ShowNewPitchPresenter implements ShowNewPitchOutputBoundary {

    private final CreateNewPitchViewModel createNewPitchViewModel;
    private ViewManagerModel viewManagerModel;

    public ShowNewPitchPresenter(CreateNewPitchViewModel createNewPitchViewModel,
                                 DashboardViewModel dashboardViewModel,
                                 ViewManagerModel viewManagerModel) {
        this.createNewPitchViewModel = createNewPitchViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ShowNewPitchOutputData outputData) {
        final CreateNewPitchState state = createNewPitchViewModel.getState();
        state.setCurrentUser(outputData.getCurrentUser());

        // Update the ViewModel to reflect changes in the state
        createNewPitchViewModel.setState(state);
        createNewPitchViewModel.firePropertyChanged();

        viewManagerModel.setState(createNewPitchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // assume can't fail
    }
}