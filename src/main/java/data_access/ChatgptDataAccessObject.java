package data_access;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import entity.ChatMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.chat_persona.ChatPersonaGptAccessInterface;

/**
 * Main application class to send a request to OpenAI's API.
 */
public final class ChatgptDataAccessObject implements DetailedDataAccessObjectInterface,
        ChatPersonaGptAccessInterface {

    private static final String LOG_FILE_PATH = "api_calls.txt";

    public ChatgptDataAccessObject() {

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
    @Override
    public String utilizeApi(String systemMessage, String userMessage)
            throws IOException, InterruptedException {
        // Create messages list
        final List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemMessage));
        messages.add(new ChatMessage("user", userMessage));

        // Call the new method
        return utilizeApi(messages);
    }

    @Override
    public String utilizeApi(List<ChatMessage> messages) throws IOException, InterruptedException {
        final String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing. Please set the OPENAI_API_KEY environment variable.");
        }

        // Convert ChatMessage list to JSON array
        final JSONArray messagesJson = new JSONArray();
        for (ChatMessage message : messages) {
            final JSONObject messageJson = new JSONObject();
            messageJson.put("role", message.getRole());
            messageJson.put("content", message.getContent());
            messagesJson.put(messageJson);
        }

        // Create JSON request body
        final JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4o-mini"); // Update the model name as needed
        requestBody.put("messages", messagesJson);

        final HttpClient client = HttpClient.newHttpClient();

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        final String content = extractContent(response.body());

        logApiCall(messages, content);

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
     * @param messages The user message used in the API call.
     * @param output The API response.
     */
    private void logApiCall(List<ChatMessage> messages, String output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("Messages:");
            writer.newLine();
            for (ChatMessage message : messages) {
                writer.write(message.getRole() + ": " + message.getContent());
                writer.newLine();
            }
            writer.write("API Output: " + output);
            writer.newLine();
            writer.write("----------------------------------------------------");
            writer.newLine();
        } catch (IOException exception) {
            System.out.println("Error logging API call: " + exception.getMessage());
        }
    }
}
