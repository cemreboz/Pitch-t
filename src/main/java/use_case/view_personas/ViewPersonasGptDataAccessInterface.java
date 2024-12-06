package use_case.view_personas;

/**
 * Gateway interface for interacting with the GPT service.
 */
public interface ViewPersonasGptDataAccessInterface {
    /**
     * Generates personas based on the pitch.
     *
     * @param systemMessage the message sent to the GPT system for persona generation
     * @return a list of generated personas.
     * @throws Exception if an error occurs during generation.
     */

    String utilizeApi(String systemMessage) throws Exception;
}
