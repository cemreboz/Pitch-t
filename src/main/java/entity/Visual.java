package entity;

/**
 * A visual created by the program.
 */
public class Visual {
    private final String imagePath;
    private final String description;

    public Visual(String imagePath, String description) {
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }
}
