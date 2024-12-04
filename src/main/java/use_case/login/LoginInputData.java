package use_case.login;

import interface_adapter.ViewModel;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData {

    private final String username;
    private final String password;
    private final ViewModel viewModel;

    public LoginInputData(String username, String password, ViewModel viewModel) {
        this.username = username;
        this.password = password;
        this.viewModel = viewModel;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    ViewModel getViewModel() {
        return viewModel;
    }

}
