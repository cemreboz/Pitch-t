package data_access;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import app.PitchitManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Visual;

/**
 * Data Access Object for interacting with visual generation data.
 */
public class VisualDataAccessObject {

    private static final String API_URL = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = PitchitManager.getApiKey();
    private static final int CODE = 200;

    private final List<Visual> imageStorage = new ArrayList<>();

    /**
     * Generates an image using OpenAI's DALL-E model.
     *
     * @param prompt The combined system and user input prompt.
     * @param model  The model to use, e.g., "dall-e-3".
     * @param num      The number of images to generate.
     * @param size   The size of the generated images, e.g., "1024x1024".
     * @return The generated image URL.
     * @throws Exception If an error occurs during the API call.
     * @throws RuntimeException if the API key is missing.
     */
    public static String generateImage(String prompt, String model, int num, String size) throws Exception {
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new RuntimeException("API key is missing. Ensure the OPENAI_API_KEY environment variable is set.");
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        final String payload = objectMapper.writeValueAsString(new ImageGenerationRequest(model, prompt, num, size));

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == CODE) {
            return extractImageUrl(response.body());
        }
        else {
            throw new RuntimeException("Error generating image: " + response.body());
        }
    }

    /**
     * Extracts the image URL from the API response.
     *
     * @param apiResponse The API response containing the image URL.
     * @return The image URL.
     * @throws Exception If there is an issue parsing the response.
     */
    private static String extractImageUrl(String apiResponse) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonResponseNode = objectMapper.readTree(apiResponse);
        return jsonResponseNode.get("data").get(0).get("url").asText();
    }

    /**
     * Saves the generated visual to the in-memory storage.
     *
     * @param visual The generated visual.
     */
    public void saveImage(Visual visual) {
        imageStorage.add(visual);
    }

    /**
     * Retrieves all stored visuals.
     *
     * @return List of stored visuals.
     */
    public List<Visual> getAllImages() {
        return new ArrayList<>(imageStorage);
    }

    /**
     * Clears the image storage.
     */
    public void clearStorage() {
        imageStorage.clear();
    }

    /**
     * Class for all of the input to generate image.
     */
    static class ImageGenerationRequest {
        private String model;
        private String prompt;
        private int num;
        private String size;

        ImageGenerationRequest(String model, String prompt, int num, String size) {
            this.model = model;
            this.prompt = prompt;
            this.num = num;
            this.size = size;
        }
    }
}
