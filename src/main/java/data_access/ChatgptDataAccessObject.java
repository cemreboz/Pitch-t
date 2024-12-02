package data_access;

import entity.ChatMessage;
import org.json.JSONException;
import use_case.view_personas.ViewPersonasGptDataAccessInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application class to send a request to OpenAI's API.
 */
public final class ChatgptDataAccessObject implements ViewPersonasGptDataAccessInterface {

    private static final String LOG_FILE_PATH = "api_calls.txt";

    public ChatgptDataAccessObject() {

    }

    @Override
    public String utilizeApi(String systemMessage) throws IOException, InterruptedException {
        final String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing. Please set the OPENAI_API_KEY environment variable.");
        }

        // Construct the request body for the API call
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemMessage));

        // Make the API call and get response
        return getInteraction(messages);
    }

    public String getInteraction(List<ChatMessage> messages) throws IOException, InterruptedException {
        final String apiKey = System.getenv("OPENAI_API_KEY");

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

    private static String extractContent(String responseBody) {
        String content = "No content found in the API response.";

        try {
            final JSONObject jsonResponse = new JSONObject(responseBody);
            final JSONArray choices = jsonResponse.getJSONArray("choices");
            if (!choices.isEmpty()) {
                final JSONObject firstChoice = choices.getJSONObject(0);
                content = firstChoice.getJSONObject("message").getString("content").trim();
            }
        } catch (JSONException exception) {
            throw new IllegalArgumentException("Invalid response format or missing fields in the API response.",
                    exception);
        }
        return content;
    }

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