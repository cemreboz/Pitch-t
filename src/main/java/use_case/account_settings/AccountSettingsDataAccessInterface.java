package use_case.account_settings;

import entity.User;

/**
 * DAO for Account settings use case.
 */
public interface AccountSettingsDataAccessInterface {
    /**
     * Returns the user with the given username.
     * @param username the username to look up
     * @return the user with the given username
     */
    User get(String username);
}
