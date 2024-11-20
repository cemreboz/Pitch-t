package interface_adapter.account_settings;

/**
 * The State information representing the logged-in user.
 */
public class AccountSettingsState {
    private String username = "";

    private String password = "";
    private String confirmedPassword = "";
    private String passwordError;

    public AccountSettingsState(AccountSettingsState copy) {
        username = copy.username;
        password = copy.password;
        passwordError = copy.passwordError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public AccountSettingsState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPassword() {
        return password;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }
}
