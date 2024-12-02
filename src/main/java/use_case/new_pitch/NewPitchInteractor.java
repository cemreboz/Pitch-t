package use_case.new_pitch;

import entity.User;
// Todo: currently db user, should add pitch method to be an interface user?

/**
 * Interactor for loading new pitch view.
 */
public class NewPitchInteractor implements NewPitchInputBoundary {
    private NewPitchDataAccessInterface userDataAccessObject;
    private NewPitchOutputBoundary newPitchPresenter;

    public NewPitchInteractor(NewPitchDataAccessInterface userDataAccessObject,
                              NewPitchOutputBoundary newPitchPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.newPitchPresenter = newPitchPresenter;
    }

    @Override
    public void execute(NewPitchInputData inputData) {
        final User currentUser = userDataAccessObject.getCurrentUser();
        final NewPitchOutputData newPitchOutputData = new NewPitchOutputData(false, currentUser);
        newPitchPresenter.prepareSuccessView(newPitchOutputData);
    }
}