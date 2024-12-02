package use_case.logout;

import entity.User;

/**
 * DAO for the Logout Use Case.
 */
public interface LogoutUserDataAccessInterface {

    /**
     * Returns the username of the curren user of the application.
     * @return the username of the current user
     */
    String getCurrentUsername();

    /**
     * Sets the username indicating who is the current user of the application.
     * @param username the new current username
     */
    void setCurrentUsername(String username);

    /**
     * Sets the user indicating the current user of the application.
     * @param user the new user object; null to indicate that no one is currently logged into the application
     */
    void setCurrentUser(User user);

    /**
     * Gets the current user of the application.
     * @return the current user of the program
     */
    User getCurrentUser();

    /**
     * Saves the user.
     * @param user the user to save
     */
    void save(User user);
}
