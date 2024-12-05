package interface_adapter.login;

import interface_adapter.ViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/**
 * The controller for the Login Use Case.
 */
public class LoginController {

    private final LoginInputBoundary loginUseCaseInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     * @param viewModel the current view model
     */
    public void execute(String username, String password, ViewModel viewModel) {
        final LoginInputData loginInputData = new LoginInputData(
                username, password, viewModel);

        loginUseCaseInteractor.execute(loginInputData);
    }

    /**
     * Executes the "switch to SignupView" Use Case.
     */
    public void switchToSignupView() {
        loginUseCaseInteractor.switchToSignupView();
    }
}
