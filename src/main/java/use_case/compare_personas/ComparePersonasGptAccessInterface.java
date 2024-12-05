package use_case.compare_personas;

import java.io.IOException;

/**
 * Interface for the comparing personas access interface.
 */
public interface ComparePersonasGptAccessInterface {
    /**
     * Method for accessing the API.
     * @param message the input to send to chatgpt.
     * @return the output of the chatgpt response.
     * @throws IOException if there is an error with accessing the API call.
     * @throws InterruptedException If there is an error with getting the API response.
     */
    String utilizeApi(String message) throws IOException, InterruptedException;
}
