package use_case.account_settings;

/**
 * Output data for the Account Settings use case.
 */
public class AccountSettingsOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final String password;

    public AccountSettingsOutputData(String username, String password, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

}
