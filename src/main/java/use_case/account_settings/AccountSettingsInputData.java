package use_case.account_settings;

/**
 * The input data for the AccountSettings use case.
 */
public class AccountSettingsInputData {

    private final String username;
    private final String password;

    public AccountSettingsInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
