package use_case.create_pitch;

/**
 * Input data for the Create Pitch use case.
 * Contains the necessary information to create a pitch.
 */
public class CreateNewPitchInputData {

    private final String name;
    private final String description;
    private final String image;
    private final String targetAudience;

    public CreateNewPitchInputData(String name, String description, String image,
                                   String targetAudience) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.targetAudience = targetAudience;
    }

    /**
     * Gets the name of the pitch.
     *
     * @return the name of the pitch
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the pitch.
     *
     * @return the description of the pitch
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the image associated with the pitch.
     *
     * @return the image URL/path associated with the pitch
     */
    public String getImage() {
        return image;
    }

    /**
     * Gets the target audience of the pitch.
     *
     * @return the target audience of the pitch
     * @throws Exception if an error occurs while retrieving the target audience
     */
    public String getTargetAudienceList() throws Exception {
        return targetAudience;
    }
}
