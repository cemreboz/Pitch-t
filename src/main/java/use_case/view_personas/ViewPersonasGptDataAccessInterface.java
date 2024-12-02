package use_case.view_personas;

/**
 * Gateway interface for interacting with the GPT service.
 */
public interface ViewPersonasGptDataAccessInterface {
    /**
     * Generates personas based on the pitch.
     *
     * @return a list of generated personas.
     * @throws Exception if an error occurs during generation.
     */

    String utilizeApi(String systemMessage) throws Exception;
}
