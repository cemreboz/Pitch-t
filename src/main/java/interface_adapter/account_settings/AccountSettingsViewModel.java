package interface_adapter.account_settings;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class AccountSettingsViewModel extends ViewModel<AccountSettingsState> {

    public static final int TITLE_FONT = 24;
    public static final int DEFUALT_HEIGHT = 20;
    public static final int DEFUALT_WIDTH = 90;

    public AccountSettingsViewModel() {
        super("account settings");
        setState(new AccountSettingsState());
    }

}
