package use_case.show_new_pitch;

import entity.User;

/**
 * Gateway interface for handling pitch-related data access.
 */
public interface ShowNewPitchDataAccessInterface {

    /**
     * Provides the current user to the dashboard.
     * @return the user currently logged in.
     */
    User getCurrentUser();
}
