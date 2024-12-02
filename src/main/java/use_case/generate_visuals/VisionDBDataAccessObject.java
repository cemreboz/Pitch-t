package use_case.generate_visuals;

import entity.User;

/**
 * DAO for the vision use case to access the database.
 */
public interface VisionDBDataAccessObject {
    /**
     * Gets the current user of the application.
     * @return the current user of the program
     */
    User getCurrentUser();
}
