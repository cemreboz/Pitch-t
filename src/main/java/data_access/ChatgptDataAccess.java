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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.PitchitManager;
import entity.ChatMessage;
import use_case.chat_expert.ExpertChatDataAccessInterface;
import use_case.chat_persona.ChatPersonaDataAccessInterface;
import use_case.set_targetaudience.DetailedDataAccessObjectInterface;

/**
 * Main application class to send a request to OpenAI's API.
 */
public class ChatgptDataAccessObject implements DetailedDataAccessObjectInterface,
        ExpertChatDataAccessInterface,
        ChatPersonaDataAccessInterface {

    private static final String LOG_FILE_PATH = "api_calls.txt";

    public ChatgptDataAccessObject() {
    }

    /**
     * Sends a message to OpenAI's API with a system and user message.
     *
     * @param systemMessage The system message providing context to the assistant.
     * @param userMessage   The user message or query.
     * @return The API response content as a string.
     */
    @Override
    public String utilizeApi(String systemMessage, String userMessage) {
        final String result;
        try {
            final List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage("system", systemMessage));
            messages.add(new ChatMessage("user", userMessage));
            result = utilizeApi(messages);
        }
        catch (IllegalArgumentException | JSONException ex) {
            throw new RuntimeException("Failed to utilize API with given messages.", ex);
        }
        return result;
    }

    @Override
    public String utilizeApi(List<ChatMessage> messages) {
        final String result;
        try {
            final String apiKey = PitchitManager.getApiKey();

            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalArgumentException(
                        "API key is missing. Please set the OPENAI_API_KEY environment variable.");
            }

            final JSONArray messagesJson = new JSONArray();
            for (ChatMessage message : messages) {
                final JSONObject messageJson = new JSONObject();
                messageJson.put("role", message.getRole());
                messageJson.put("content", message.getContent());
                messagesJson.put(messageJson);
            }

            final JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4o-mini");
            requestBody.put("messages", messagesJson);

            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = extractContent(response.body());

            logApiCall(messages, result);

        }
        catch (IOException | InterruptedException | IllegalArgumentException | JSONException ex) {
            throw new RuntimeException("Failed to utilize API.", ex);
        }
        return result;
    }

    private static String extractContent(String responseBody) {
        final String content;
        try {
            final JSONObject jsonResponse = new JSONObject(responseBody);
            final JSONArray choices = jsonResponse.getJSONArray("choices");
            if (!choices.isEmpty()) {
                final JSONObject firstChoice = choices.getJSONObject(0);
                content = firstChoice.getJSONObject("message").getString("content").trim();
            }
            else {
                content = "No content found in the API response.";
            }
        }
        catch (JSONException ex) {
            throw new RuntimeException("Invalid response format or missing fields in the API response.", ex);
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
        }
        catch (IOException ex) {
            throw new RuntimeException("Error logging API call.", ex);
        }
    }
}
