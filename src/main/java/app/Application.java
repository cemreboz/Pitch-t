package app;

import java.io.BufferedWriter;
import java.io.FileWriter;
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

    private static final String LOG_FILE_PATH = "api_calls.txt";

    /**
     * The main method for executing the API call.
     * @param args command-line arguments, not used here.
     * @throws IOException if an I/O error occurs during the API call.
     * @throws InterruptedException if the operation is interrupted.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Fetch the OpenAI API key from environment variables
        final String apiKey = System.getenv("OPENAI_API_KEY");

        // Create HTTP client outside of the loop to reuse it
        final HttpClient client = HttpClient.newHttpClient();

        // Set up scanner for user input
        final Scanner scanner = new Scanner(System.in);

        while (true) {
            // Prompt the user for input in the terminal
            System.out.print("Enter your message for OpenAI (or type 'exit' to quit): ");
            final String userMessage = scanner.nextLine();

            // Exit the loop if the user types "exit"
            if ("exit".equalsIgnoreCase(userMessage)) {
                System.out.println("Exiting program.");
                break;
            }

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

            // Send the request and get the response
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response in the terminal
            final String apiResponse = response.body();
            System.out.println("Response from OpenAI:");
            System.out.println(apiResponse);

            // Log the input and output to a file
            logApiCall(userMessage, apiResponse);
        }

        // Close the scanner
        scanner.close();
    }

    /**
     * Logs the API call input and output to a file.
     * @param input The user's input message.
     * @param output The API response.
     */
    private static void logApiCall(String input, String output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("User Input: " + input);
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
