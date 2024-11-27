package use_case.chat_expert;

import entity.Expert;

/**
 * Data access interface for retrieving expert data.
 */
public interface ChatExpertDataAccessInterface {

    /**
     * Retrieves an expert by ID.
     *
     * @param expertId The ID of the expert to retrieve.
     * @return The Expert object if found, null otherwise.
     */
    Expert getExpertById(String expertId);
}
