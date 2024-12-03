package data_access;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import use_case.generate_visuals.ImageGeneratorInterface;

/**
 * Handles image generation and downloading.
 */
public class FileVisualDataAccessObject implements ImageGeneratorInterface {

    private static final int BUFFER_SIZE = 1024;

    @Override
    public String generateImage(String prompt, String filePath) throws Exception {
        // Use OpenAI API to generate and download the image
        return generateAndDownloadImage(prompt, filePath);
    }

    /**
     * Generates and downloads an image using OpenAI's DALL-E.
     *
     * @param userInput      The description of the image to generate.
     * @param outputFilePath The file path to save the image.
     * @return The path to the saved image file.
     * @throws Exception If the process fails.
     */

    public String generateAndDownloadImage(String userInput, String outputFilePath) throws Exception {
        // Use ImageGenerator to get the image URL
        final String imageUrl = VisualDataAccessObject.generateImage(userInput, "dall-e-3", 1, "1024x1024");

        System.out.println("Generated image URL: " + imageUrl);
        // Download the image from the URL
        downloadImage(imageUrl, outputFilePath);

        return outputFilePath;
    }

    private void downloadImage(String imageUrl, String filePath) throws Exception {
        final HttpURLConnection connection = (HttpURLConnection) URI.create(imageUrl).toURL().openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed to download image: HTTP " + connection.getResponseCode());
        }

        try (InputStream inputStream = connection.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(filePath)) {

            final byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        finally {
            connection.disconnect();
        }
    }
}
