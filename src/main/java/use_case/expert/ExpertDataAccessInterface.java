package use_case.expert;

import entity.User;

/**
 * The Data access interface for expert
 */
public interface ExpertDataAccessInterface {

    /**
     * Provides the current user to the dashboard.
     * @return the user currently logged in.
     */
    User getCurrentUser();

}
