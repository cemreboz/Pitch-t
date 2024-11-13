package app;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

/**
 * Main application class to send a request to OpenAI's API.
 */
public class Application {

    /**
     * The main method for executing the API call.
     * @param args command-line arguments, not used here.
     * @throws IOException if an I/O error occurs during the API call.
     * @throws InterruptedException if the operation is interrupted.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Fetch the OpenAI API key from environment variables
        final String apiKey = System.getenv("OPENAI_API_KEY");

        // Prompt the user for input in the terminal
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your message for OpenAI: ");
        final String userMessage = scanner.nextLine();

        // JSON request body with the user's input
        final String body = """
                {
                    "model": "gpt-4",
                    "messages": [
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ]
                }""".formatted(userMessage);

        // Create HTTP request
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        // Create HTTP client and send the request
        final HttpClient client = HttpClient.newHttpClient();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response in the terminal
        System.out.println("Response from OpenAI:");
        System.out.println(response.body());

        scanner.close();
    }
}
