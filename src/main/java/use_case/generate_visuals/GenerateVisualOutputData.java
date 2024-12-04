package use_case.generate_visuals;

/**
 * The output data for the Generate Visual Use Case.
 */
public class GenerateVisualOutputData {
    private final String imagePath;


    public GenerateVisualOutputData(String imagePath, String message, String username, String password) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

}
