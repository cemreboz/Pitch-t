package app;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Manager class for the "pitchit" user on the database, used to modify and view the API token.
 */
public class PitchitManager {

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String API_URL = "http://vm003.teach.cs.toronto.edu:20112";
    private static final String API_TOKEN_KEY = "apiToken";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private final OkHttpClient client;

    public PitchitManager() {
        this.client = new OkHttpClient().newBuilder().build();
    }

    /**
     * Prompts the user for the new API token and updates it in the database.
     *
     * @param password the authenticated password for the pitchit user
     */
    public void manageApiToken(String password) {
        final Scanner scanner = new Scanner(System.in);

        // Prompt for new API token
        System.out.print("Enter new API token: ");
        final String newApiToken = scanner.nextLine();

        // Update the API token
        try {
            updateApiToken(password, newApiToken);
            System.out.println("API token updated successfully.");
        }
        catch (JSONException ex) {
            System.out.println("Error updating API token: " + ex.getMessage());
        }
    }

    /**
     * Updates the API token stored in the "info" section of the "pitchit" user.
     *
     * @param password    the authenticated password for the pitchit user
     * @param newApiToken the new API token to store
     * @throws RuntimeException if the API call fails
     */
    private void updateApiToken(String password, String newApiToken) {
        try {
            // Create request body
            final JSONObject infoObject = new JSONObject();
            infoObject.put(API_TOKEN_KEY, newApiToken);

            final JSONObject requestBody = new JSONObject();
            requestBody.put("username", "pitchit");
            requestBody.put("password", password);
            requestBody.put("info", infoObject);

            // Send the API request
            final RequestBody body = RequestBody.create(requestBody.toString(), MediaType.parse(CONTENT_TYPE_JSON));
            final Request request = new Request.Builder()
                    .url(API_URL + "/modifyUserInfo")
                    .put(body)
                    .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Failed to update API token: " + response.body().string());
                }
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException("Error updating API token: " + ex.getMessage());
        }
    }

    /**
     * Retrieves and displays the current API token for the "pitchit" user.
     *
     * @param password the authenticated password for the pitchit user
     * @throws RuntimeException if the API call fails
     */
    public void viewApiToken(String password) {
        try {
            // Create a GET request to fetch the user info
            final Request request = new Request.Builder()
                    .url(API_URL + "/user?username=pitchit&password=" + password)
                    .get()
                    .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Failed to fetch user info: " + response.body().string());
                }

                final JSONObject responseBody = new JSONObject(response.body().string());
                final JSONObject userInfo = responseBody.getJSONObject("user").getJSONObject("info");

                if (userInfo.has(API_TOKEN_KEY)) {
                    System.out.println("Current API Token: " + userInfo.getString(API_TOKEN_KEY));
                }
                else {
                    System.out.println("API Token not set.");
                }
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException("Error retrieving API token: " + ex.getMessage());
        }
    }

    /**
     * Main method for managing the "pitchit" user's API token.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final PitchitManager manager = new PitchitManager();
        final Scanner scanner = new Scanner(System.in);

        System.out.print("Enter password for pitchit user: ");
        final String password = scanner.nextLine();

        System.out.println("Options:\n1. Update API Token\n2. View API Token");
        System.out.print("Enter your choice: ");
        final int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                manager.manageApiToken(password);
                break;
            case 2:
                manager.viewApiToken(password);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
