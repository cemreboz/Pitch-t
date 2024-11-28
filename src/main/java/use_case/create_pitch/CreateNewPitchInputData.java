package use_case.create_pitch;

/**
 * Input data for the Create Pitch use case.
 * Contains the necessary information to create a pitch.
 */
public class CreateNewPitchInputData {

    private final String name;
    private final String description;
    private final String image;

    public CreateNewPitchInputData(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

}
