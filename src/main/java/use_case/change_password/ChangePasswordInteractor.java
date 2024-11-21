package use_case.change_password;

import entity.DBUser;
import entity.DBUserFactory;
import entity.User;
import entity.UserFactory;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
    private final ChangePasswordUserDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface,
                                    ChangePasswordOutputBoundary changePasswordOutputBoundary,
                                    UserFactory userFactory) {
        this.userDataAccessObject = changePasswordDataAccessInterface;
        this.userPresenter = changePasswordOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        final User newUser;
        final User existingUser = userDataAccessObject.get(changePasswordInputData.getUsername());
        if (existingUser instanceof DBUser) {
            final DBUser castedUser = (DBUser) existingUser;
            final DBUserFactory castedUserFactory = (DBUserFactory) userFactory;
            newUser = castedUserFactory.create(changePasswordInputData.getUsername(),
                    changePasswordInputData.getPassword(), castedUser.getPitches(), castedUser.getExperts());
        }
        else {
            newUser = userFactory.create(changePasswordInputData.getUsername(), changePasswordInputData.getPassword());
        }

        userDataAccessObject.changePassword(newUser);

        final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(newUser.getName(),
                                                                                  false);
        userPresenter.prepareSuccessView(changePasswordOutputData);
    }
}
