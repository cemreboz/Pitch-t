package use_case.set_targetaudience;

import entity.User;

/**
 * Data access interface for the detailed use case in order to set the new detailed map.
 */
public interface DetailedDataObjectAccessInterface {

    /**
     * Provides the current user to the persona.
     * @return the user currently logged in.
     */
    User getCurrentUser();
}
