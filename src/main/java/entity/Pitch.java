package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A pitch of the product.
 */
public class Pitch {

    private String pitchID;
    private String name;

    // TODO Think about how to do the images for the pitch. URL?
    private String image;

    private String description;
    private List<String> targetAudienceList;
    private List<Persona> personas = new ArrayList<>();

    public Pitch(String pitchID, String name, String image, String description, List<String> targetAudienceList) {
        this.pitchID = pitchID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.targetAudienceList = targetAudienceList;
    }

    public String getPitchID() {
        return pitchID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTargetAudienceList() {
        return targetAudienceList;
    }

    public List<Persona> getPersonas() {
        return personas;
    }
}
