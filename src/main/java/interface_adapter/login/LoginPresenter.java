package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.AccountSettingsState;
import interface_adapter.change_password.AccountSettingsViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final AccountSettingsViewModel accountSettingsViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          AccountSettingsViewModel accountSettingsViewModel,
                          LoginViewModel loginViewModel, SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.accountSettingsViewModel = accountSettingsViewModel;
        this.loginViewModel = loginViewModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the logged in view.

        final AccountSettingsState accountSettingsState = accountSettingsViewModel.getState();
        accountSettingsState.setUsername(response.getUsername());
        this.accountSettingsViewModel.setState(accountSettingsState);
        this.accountSettingsViewModel.firePropertyChanged();

        this.viewManagerModel.setState(accountSettingsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
