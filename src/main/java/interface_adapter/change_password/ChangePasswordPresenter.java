package interface_adapter.change_password;

import interface_adapter.account_settings.AccountSettingsState;
import interface_adapter.account_settings.AccountSettingsViewModel;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.change_password.ChangePasswordOutputData;

/**
 * The Presenter for the Change Password Use Case.
 */
public class ChangePasswordPresenter implements ChangePasswordOutputBoundary {

    private final AccountSettingsViewModel accountSettingsViewModel;

    public ChangePasswordPresenter(AccountSettingsViewModel accountSettingsViewModel) {
        this.accountSettingsViewModel = accountSettingsViewModel;
    }

    @Override
    public void prepareSuccessView(ChangePasswordOutputData outputData) {
        // currently there isn't anything to change based on the output data,
        // since the output data only contains the username, which remains the same.
        // We still fire the property changed event, but just to let the view know that
        // it can alert the user that their password was changed successfully..
        final AccountSettingsState accountSettingsState = accountSettingsViewModel.getState();
        accountSettingsState.setConfirmedPassword(accountSettingsState.getPassword());
        accountSettingsViewModel.firePropertyChanged("password");

    }

    @Override
    public void prepareFailView(String error) {
        // note: this use case currently can't fail
    }
}
