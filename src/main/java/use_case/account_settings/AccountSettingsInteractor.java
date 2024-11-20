package use_case.account_settings;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;

/**
 * The account settings interactor.
 */
public class AccountSettingsInteractor implements AccountSettingsInputBoundary {
    private LoginUserDataAccessInterface userDataAccessObject;
    private AccountSettingsOutputBoundary accountSettingsPresenter;

    public AccountSettingsInteractor(LoginUserDataAccessInterface userDataAccessObject,
                                     AccountSettingsOutputBoundary accountSettingsPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.accountSettingsPresenter = accountSettingsPresenter;
    }

    @Override
    public void execute(AccountSettingsInputData accountSettingsInputData) {
        final User user = userDataAccessObject.get(accountSettingsInputData.getUsername());

        final AccountSettingsOutputData accountSettingsOutputData = new AccountSettingsOutputData(user.getName(),
                user.getPassword(), false);
        accountSettingsPresenter.prepareSuccessView(accountSettingsOutputData);
    }
}
