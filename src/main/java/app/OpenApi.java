package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Creates a class for openAPI.
 */
public class OpenApi {

    /**
     * Creates a class for chat.
     * @param prompt is what the input is to access chatgpt
     * @return the extracted message.
     * @throws RuntimeException if there is an issue.
     */
    public static String chat(String prompt) {
        final String url = "https://api.openai.com/v1/chat/completions";
        final String apiKey = "sk-proj-jgoflH3wDt9aSnqiOxjV0l7hJSRbMW4T8mlIraMNS2_hwAEZkqG9ODyay5bakuFr2MYezzLNErT3"
                + "BlbkFJGTOfo1kbtZBGNVRlLS2nJRNGdtq7voWZPS8TAkt1Zgu74OhhlY1WVOxfd1AGDuvm3_uRoiDUcA";
        final String model = "gpt-3.5-turbo";

        try {
            final URL obj = new URL(url);
            final HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            final String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \""
                    + prompt + "\"}]}";
            connection.setDoOutput(true);
            final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            final BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            final StringBuilder response = new StringBuilder();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return messageFromJSONResponse(response.toString());

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a class for openapi.
     * @param response is the response of chatgpt.
     * @return the response of the chatgpt.
     */
    public static String messageFromJSONResponse(String response) {
        final int start = response.indexOf("content") + 11;

        final int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }

    /**
     * Creates a class for openapi.
     * @param args for the string input.
     */
    public static void main(String[] args) {

        System.out.println(chat("hello, how are you? Can you tell me what's a Fibonacci Number?"));

    }
}

