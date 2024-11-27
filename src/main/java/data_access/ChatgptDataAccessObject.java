package data_access;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Main application class to send a request to OpenAI's API.
 */
public class ChatgptDataAccessObject {

    private static final String LOG_FILE_PATH = "api_calls.txt";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Fetch the OpenAI API key from environment variables
        // Example system and user messages
        final String systemMessage = "You are an assistant that provides helpful responses.";
        final String userMessage = "Can you explain the concept of machine learning?";

        // Call the API and get the response
        final String response = String.valueOf(utilizeApi(systemMessage, userMessage));

        // Print the response
        System.out.println("Response from OpenAI:");
        System.out.println(response);
    }

    /**
     * Sends a message to OpenAI's API with a system and user message.
     *
     * @param systemMessage The system message providing context to the assistant.
     * @param userMessage   The user message or query.
     * @return The API response as a string.
     * @throws IOException              If an I/O error occurs during the API call.
     * @throws InterruptedException     If the operation is interrupted.
     * @throws IllegalArgumentException if the API key is invalid.
     */
    public static String utilizeApi(String systemMessage, String userMessage)
            throws IOException, InterruptedException {
        final String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing. Please set the OPENAI_API_KEY environment "
                    + "variable.");
        }

        // JSON request body with system and user messages
        final String body = """
            {
                "model": "gpt-4",
                "messages": [
                    {
                        "role": "system",
                        "content": "%s"
                    },
                    {
                        "role": "user",
                        "content": "%s"
                    }
                ]
            }""".formatted(systemMessage, userMessage);

        // Create HTTP client
        final HttpClient client = HttpClient.newHttpClient();

        // Create HTTP request
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        // Send the request and get the response
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Log the input and output to a file
        logApiCall(systemMessage, userMessage, response.body());

        return response.body();
    }

    /**
     * Logs the API call input and output to a file.
     *
     * @param systemMessage The system message used in the API call.
     * @param userMessage The user message used in the API call.
     * @param output The API response.
     */
    private static void logApiCall(String systemMessage, String userMessage, String output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("System Message: " + systemMessage);
            writer.newLine();
            writer.write("User Input: " + userMessage);
            writer.newLine();
            writer.write("API Output: " + output);
            writer.newLine();
            writer.write("----------------------------------------------------");
            writer.newLine();
        }
        catch (IOException e) {
            System.out.println("Error logging API call: " + e.getMessage());
        }
    }
}
