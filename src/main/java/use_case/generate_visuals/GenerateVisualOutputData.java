package use_case.generate_visuals;

/**
 * The output data for the Generate Visual Use Case.
 */
public class GenerateVisualOutputData {
    private final String imagePath;
    private final String message;
    private final String username;
    private final String password;

    public GenerateVisualOutputData(String imagePath, String message, String username, String password) {
        this.imagePath = imagePath;
        this.message = message;
        this.username = username;
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
