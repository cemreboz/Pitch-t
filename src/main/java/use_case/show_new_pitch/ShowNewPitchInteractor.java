package use_case.show_new_pitch;

import entity.User;

/**
 * Interactor for loading the Create New Pitch view.
 */
public class ShowNewPitchInteractor implements ShowNewPitchInputBoundary {
    private ShowNewPitchDataAccessInterface userDataAccessObject;
    private ShowNewPitchOutputBoundary newPitchPresenter;

    public ShowNewPitchInteractor(ShowNewPitchDataAccessInterface userDataAccessObject,
                                  ShowNewPitchOutputBoundary newPitchPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.newPitchPresenter = newPitchPresenter;
    }

    @Override
    public void execute(ShowNewPitchInputData inputData) {
        final User currentUser = userDataAccessObject.getCurrentUser();
        final ShowNewPitchOutputData newPitchOutputData = new ShowNewPitchOutputData(false, currentUser);
        newPitchPresenter.prepareSuccessView(newPitchOutputData);
    }
}