package use_case.compare_personas;

import entity.User;

/**
 * Access interface for compare personas to access the DAO.
 */
public interface ComparePersonasDataAccessInterface {
    /**
     * Provides the current user to the dashboard.
     * @return the user currently logged in.
     */
    User getCurrentUser();
}
