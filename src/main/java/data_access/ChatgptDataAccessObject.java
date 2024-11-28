package data_access;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Main application class to send a request to OpenAI's API.
 */
public final class ChatgptDataAccessObject implements DetailedDataAccessObjectInterface {

    private static final String LOG_FILE_PATH = "api_calls.txt";

    private ChatgptDataAccessObject() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Sends a message to OpenAI's API with a system and user message.
     *
     * @param systemMessage The system message providing context to the assistant.
     * @param userMessage   The user message or query.
     * @return The API response content as a string.
     * @throws IOException              If an I/O error occurs during the API call.
     * @throws InterruptedException     If the operation is interrupted.
     * @throws IllegalArgumentException if the API key is invalid.
     */
    public String utilizeApi(String systemMessage, String userMessage)
            throws IOException, InterruptedException {
        final String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing. Please set the OPENAI_API_KEY environment "
                    + "variable.");
        }

        // JSON request body with system and user messages
        final String body = """ 
            {
                "model": "gpt-4-turbo",
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

        final HttpClient client = HttpClient.newHttpClient();

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        final String content = extractContent(response.body());

        logApiCall(systemMessage, userMessage, content);

        return content;
    }

    /**
     * Extracts the "content" field from the OpenAI API response JSON.
     *
     * @param responseBody The raw response body as a JSON string.
     * @return The content field of the assistant's message.
     * @throws IllegalArgumentException If the response format is invalid or does not contain the required fields.
     */
    private static String extractContent(String responseBody) {
        String content = "No content found in the API response.";

        try {
            final JSONObject jsonResponse = new JSONObject(responseBody);
            final JSONArray choices = jsonResponse.getJSONArray("choices");
            if (!choices.isEmpty()) {
                final JSONObject firstChoice = choices.getJSONObject(0);
                content = firstChoice.getJSONObject("message").getString("content").trim();
            }
        }
        catch (JSONException exception) {
            throw new IllegalArgumentException("Invalid response format or missing fields in the API response.",
                    exception);
        }
        return content;
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
        catch (IOException exception) {
            System.out.println("Error logging API call: " + exception.getMessage());
        }
    }
}
