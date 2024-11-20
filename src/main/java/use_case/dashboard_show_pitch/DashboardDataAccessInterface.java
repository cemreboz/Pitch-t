package use_case.dashboard_show_pitch;

import entity.User;

/**
 * DAO for the show pitches use case.
 */
public interface DashboardDataAccessInterface {

    /**
     * Provides the current user to the dashboard.
     * @return the user currently logged in.
     */
    User getCurrentUser();
}
