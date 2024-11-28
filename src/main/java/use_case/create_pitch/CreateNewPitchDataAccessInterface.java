package use_case.create_pitch;

import entity.User;

/**
 * Gateway interface for handling pitch-related data access.
 */
public interface CreateNewPitchDataAccessInterface {

    /**
     * Provides the current user to the dashboard.
     * @return the user currently logged in.
     */
    User getCurrentUser();

    /**
     * Sets the user indicating the current user of the application.
     * @param user the new user object; null to indicate that no one is currently logged into the application
     */
    void setCurrentUser(User user);
}
