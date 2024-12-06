package use_case.set_targetaudience;

/**
 * Sets the interface for target audience repository.
 */
public interface TargetAudienceDataAccessInterface {

    /**
     * Generates a list of target audiences based on a project description.
     *
     * @param systemMessage The message sent to the Chatgpt system.
     * @param userMessage   Dependent on the pitch itself.
     * @return a string representing the generated target audience.
     * @throws Exception If any error occurs during data fetching.
     */
    String generateTargetAudience(String systemMessage, String userMessage) throws Exception;
}
