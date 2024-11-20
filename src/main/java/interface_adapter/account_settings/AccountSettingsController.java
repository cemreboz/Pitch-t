package interface_adapter.account_settings;

import use_case.account_settings.AccountSettingsInputBoundary;
import use_case.account_settings.AccountSettingsInputData;

/**
 * The controller for the Account Settings class.
 */
public class AccountSettingsController {

    private final AccountSettingsInputBoundary accountSettingsUseCaseInteractor;

    public AccountSettingsController(AccountSettingsInputBoundary accountSettingsUseCaseInteractor) {
        this.accountSettingsUseCaseInteractor = accountSettingsUseCaseInteractor;
    }

    /**
     * Executes the account settings use case.
     * @param username username of user
     * @param password password of user
     */
    public void execute(String username, String password) {
        final AccountSettingsInputData accountSettingsInputData = new AccountSettingsInputData(username, password);
        accountSettingsUseCaseInteractor.execute(accountSettingsInputData);
    }
}
