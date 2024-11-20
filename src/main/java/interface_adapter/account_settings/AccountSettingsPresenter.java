package interface_adapter.account_settings;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.account_settings.AccountSettingsOutputBoundary;
import use_case.account_settings.AccountSettingsOutputData;

/**
 * The presenter for the Account Settings use case.
 */
public class AccountSettingsPresenter implements AccountSettingsOutputBoundary {

    private DashboardViewModel dashboardViewModel;
    private AccountSettingsViewModel accountSettingsViewModel;
    private ViewManagerModel viewManagerModel;

    public AccountSettingsPresenter(
            DashboardViewModel dashboardViewModel,
            AccountSettingsViewModel accountSettingsViewModel,
            ViewManagerModel viewManagerModel) {
        this.dashboardViewModel = dashboardViewModel;
        this.accountSettingsViewModel = accountSettingsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(AccountSettingsOutputData response) {
        final AccountSettingsState accountSettingsState = accountSettingsViewModel.getState();
        accountSettingsState.setUsername(response.getUsername());
        accountSettingsState.setConfirmedPassword(response.getPassword());

        this.accountSettingsViewModel.setState(accountSettingsState);
        this.accountSettingsViewModel.firePropertyChanged();

        this.viewManagerModel.setState(accountSettingsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Can't fail
    }
}
