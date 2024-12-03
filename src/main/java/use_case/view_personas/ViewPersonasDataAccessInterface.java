package use_case.view_personas;

import entity.User;

/**
 * Data access interface for grabbing the current user object from the DAO.
 */
public interface ViewPersonasDataAccessInterface {

    /**
     * Provides the current user to the persona.
     * @return the user currently logged in.
     */
    User getCurrentUser();
}
