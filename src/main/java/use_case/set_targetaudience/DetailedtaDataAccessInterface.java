package use_case.set_targetaudience;

/**
 * Interface for the Detailed Target Audience Data Access Object.
 */
public interface DetailedtaDataAccessInterface {

    /**
     * Makes an API call to retrieve data for the detailed target audience.
     *
     * @param systemMessage The system prompt to set the context.
     * @param userMessage   The user prompt to provide specific input.
     * @return The API response content as a string.
     * @throws RuntimeException If any issue occurs during the API call.
     */
    String utilizeApi(String systemMessage, String userMessage);

}