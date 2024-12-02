package use_case.persona;

import entity.User;

/**
 * The Data access interface for persona.
 */
public interface PersonaDataAccessInterface {

    /**
     * Provides the current user to the persona.
     * @return the user currently logged in.
     */
    User getCurrentUser();
}
