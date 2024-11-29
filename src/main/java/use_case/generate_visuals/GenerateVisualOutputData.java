package use_case.generate_visuals;

/**
 * The output data for the Generate Visual Use Case.
 */
public class GenerateVisualOutputData {
    private final String imagePath;
    private final String message;

    public GenerateVisualOutputData(String imagePath, String message) {
        this.imagePath = imagePath;
        this.message = message;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getMessage() {
        return message;
    }
}
