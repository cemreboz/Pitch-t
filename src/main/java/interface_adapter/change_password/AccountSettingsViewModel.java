package interface_adapter.change_password;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class AccountSettingsViewModel extends ViewModel<AccountSettingsState> {

    public AccountSettingsViewModel() {
        super("account settings");
        setState(new AccountSettingsState());
    }

}
