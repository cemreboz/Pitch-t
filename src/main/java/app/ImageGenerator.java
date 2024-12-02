package app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageGenerator {

    private static final String API_URL = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

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
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(new ImageGenerationRequest(model, prompt, n, size));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Error generating image: " + response.body());
        }
    }

    public static void main(String[] args) {
        try {
            String prompt = "Create a vibrant and motivational advertisement for 'FitFuel' energy bars targeting health-conscious professionals.";
            String model = "dall-e-3";
            int n = 1;
            String size = "1024x1024";

            String jsonResponse = generateImage(prompt, model, n, size);

            // Parse the response to extract the image URL
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponseNode = objectMapper.readTree(jsonResponse);
            String imageUrl = jsonResponseNode.get("data").get(0).get("url").asText();
            System.out.println("Generated Image URL: " + imageUrl);
        } catch (Exception e) {
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
