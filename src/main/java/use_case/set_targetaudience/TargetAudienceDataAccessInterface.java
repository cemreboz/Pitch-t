package use_case.set_targetaudience;

import entity.Pitch;

/**
 * Sets the interface for target audience repository.
 */
public interface TargetAudienceDataAccessInterface {

    /**
     * Generates a list of target audiences based on a project description.
     *
     * @param pitch The pitch itself.
     * @return A list of target audience categories.
     * @throws Exception If any error occurs during data fetching.
     */
    String generateTargetAudience(Pitch pitch) throws Exception;
}
