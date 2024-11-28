package use_case.new_pitch;

import entity.User;

/**
 * Gateway interface for handling pitch-related data access.
 */
public interface NewPitchDataAccessInterface {

    /**
     * Provides the current user to the dashboard.
     * @return the user currently logged in.
     */
    User getCurrentUser();
}
