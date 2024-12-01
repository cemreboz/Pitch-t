package data_access;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Visual;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class VisualDataAccessObject {
    private static final String API_URL = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    private final Map<String, Visual> imageStorage = new HashMap<>();

    /**
     * Generates an image using OpenAI's DALL-E model.
     *
     * @param prompt The combined system and user input prompt.
     * @param model The model to use, e.g., "dall-e-3".
     * @param n The number of images to generate.
     * @param size The size of the generated images, e.g., "1024x1024".
     * @return The JSON response from the API as a string.
     * @throws Exception If an error occurs during the API call.
     */
    public static String generateImage(String prompt, String model, int n, String size) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String payload = objectMapper.writeValueAsString(new data_access.VisualDataAccessObject.ImageGenerationRequest(model, prompt, n, size));

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        }
        else {
            throw new RuntimeException("Error generating image: " + response.body());
        }
    }

    /**
     * Saves the image URL in the in-memory storage.
     *
     * @param filePath The file path (key) where the image is saved.
     * @param imageUrl The URL of the generated image.
     */
    public void saveImage(String filePath, String imageUrl, String prompt) {
        final Visual visual = new Visual(imageUrl, prompt);
        imageStorage.put(filePath, visual);
    }

    public static void main(String[] args) {
        try {
            final String prompt = "Create a vibrant and motivational advertisement for 'FitFuel' energy bars targeting health-conscious professionals.";
            final String model = "dall-e-3";
            int n = 1;
            final String size = "1024x1024";

            final String jsonResponse = generateImage(prompt, model, n, size);

            // Parse the response to extract the image URL
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode jsonResponseNode = objectMapper.readTree(jsonResponse);
            final String imageUrl = jsonResponseNode.get("data").get(0).get("url").asText();
            System.out.println("Generated Image URL: " + imageUrl);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ImageGenerationRequest {
        public String model;
        public String prompt;
        public int n;
        public String size;

        public ImageGenerationRequest(String model, String prompt, int n, String size) {
            this.model = model;
            this.prompt = prompt;
            this.n = n;
            this.size = size;
        }
    }
}
