package use_case.account_settings;

import entity.User;

/**
 * The account settings interactor.
 */
public class AccountSettingsInteractor implements AccountSettingsInputBoundary {
    private AccountSettingsDataAccessInterface userDataAccessObject;
    private AccountSettingsOutputBoundary accountSettingsPresenter;

    public AccountSettingsInteractor(AccountSettingsDataAccessInterface userDataAccessObject,
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
