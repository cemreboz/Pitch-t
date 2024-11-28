package interface_adapter.create_pitch;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.new_pitch.NewPitchState;
import interface_adapter.new_pitch.NewPitchViewModel;
import interface_adapter.pitch.PitchState;
import interface_adapter.pitch.PitchViewModel;
import use_case.create_pitch.CreateNewPitchOutputBoundary;
import use_case.create_pitch.CreateNewPitchOutputData;
import use_case.new_pitch.NewPitchOutputData;

import javax.swing.text.View;

/**
 * The presenter for the Create New Pitch Use Case.
 */
public class CreateNewPitchPresenter implements CreateNewPitchOutputBoundary {

    private final NewPitchViewModel newPitchViewModel;
    private final PitchViewModel pitchViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateNewPitchPresenter(NewPitchViewModel newPitchViewModel, PitchViewModel pitchViewModel,
                             ViewManagerModel viewManagerModel) {
        this.newPitchViewModel = newPitchViewModel;
        this.viewManagerModel = viewManagerModel;
        this.pitchViewModel = pitchViewModel;
    }

    @Override
    public void prepareSuccessView(CreateNewPitchOutputData outputData) {
        final PitchState pitchState = pitchViewModel.getState();
        pitchState.setPitch(outputData.getNewPitch());
        pitchState.setUsername(outputData.getUsername());
        pitchState.setPassword(outputData.getPassword());

        pitchViewModel.setState(pitchState);
        pitchViewModel.firePropertyChanged();

        this.viewManagerModel.setState(pitchViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final NewPitchState state = newPitchViewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setSuccess(false);
    }
}
