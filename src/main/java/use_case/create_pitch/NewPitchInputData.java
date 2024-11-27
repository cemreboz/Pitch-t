package use_case.create_pitch;

import java.util.List;

/**
 * Input data for the Create Pitch use case.
 * Contains the necessary information to create a pitch.
 */
public class NewPitchInputData {

    private final String name;
    private final String description;
    private final String image;
    private final List<String> targetAudienceList;

    public NewPitchInputData(String name, String description, String image, List<String> targetAudienceList) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.targetAudienceList = targetAudienceList;
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

    public List<String> getTargetAudienceList() {
        return targetAudienceList;
    }
}