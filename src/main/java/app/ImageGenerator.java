package app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class for generating the image using ChatGPT API.
 */
public class ImageGenerator {

    private static final String API_URL = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = PitchitManager.getApiKey();
    private static final int CODE = 200;

    /**
     * Generates an image using OpenAI's DALL-E model.
     *
     * @param prompt The combined system and user input prompt.
     * @param model The model to use, e.g., "dall-e-3".
     * @param num The number of images to generate.
     * @param size The size of the generated images, e.g., "1024x1024".
     * @return The JSON response from the API as a string.
     * @throws Exception If an error occurs during the API call.
     * @throws RuntimeException if there is an error generating the image.
     */
    public static String generateImage(String prompt, String model, int num, String size) throws Exception {
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
            return response.body();
        }
        else {
            throw new RuntimeException("Error generating image: " + response.body());
        }
    }

    /**
     * Main entry point for the ImageGenerator application.
     * This method sets up the necessary parameters for generating an advertisement image using ChatGPT's API.
     * The generated image is meant to advertise 'FitFuel' energy bars, specifically targeting health-conscious
     * professionals.
     * The prompt, model, image size, and other configurations are defined within the method.
     *
     * @param args command-line arguments (currently unused).
     */
    public static void main(String[] args) {
        try {
            final String prompt = "Create a vibrant and motivational advertisement for 'FitFuel' energy bars targeting"
                    + " health-conscious professionals.";
            final String model = "dall-e-3";
            final int n = 1;
            final String size = "1024x1024";

            final String jsonResponse = generateImage(prompt, model, n, size);

            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode jsonResponseNode = objectMapper.readTree(jsonResponse);
            final String imageUrl = jsonResponseNode.get("data").get(0).get("url").asText();
            System.out.println("Generated Image URL: " + imageUrl);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * CLass for the setting up the input for the image generation.
     */
    static class ImageGenerationRequest {
        private final String model;
        private final String prompt;
        private final int num;
        private final String size;

        ImageGenerationRequest(String model, String prompt, int num, String size) {
            this.model = model;
            this.prompt = prompt;
            this.num = num;
            this.size = size;
        }
    }
}
