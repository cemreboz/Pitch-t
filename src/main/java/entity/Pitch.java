package entity;

import java.util.*;

/**
 * A pitch of the product.
 */
public class Pitch {

    private String pitchID;
    private String name;
    private String image;
    private String description;
    private List<String> targetAudienceList;
    private Map<String, DetailedTargetAudience> detailedTargetAudienceMap;
    private List<Persona> personas = new ArrayList<>();

    public Pitch(String pitchID, String name, String image, String description, List<String> targetAudienceList) {
        this.pitchID = pitchID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.targetAudienceList = targetAudienceList;
        this.detailedTargetAudienceMap = new HashMap<>();
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

    public Map<String, DetailedTargetAudience> getDetailedTargetAudienceMap() {
        return detailedTargetAudienceMap;
    }

    public void setDetailedTargetAudienceMap(Map<String, DetailedTargetAudience> detailedTargetAudienceMap) {
        this.detailedTargetAudienceMap = detailedTargetAudienceMap;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pitch pitch = (Pitch) o;

        return Objects.equals(pitchID, pitch.pitchID) &&
                Objects.equals(name, pitch.name) &&
                Objects.equals(image, pitch.image) &&
                Objects.equals(description, pitch.description) &&
                Objects.equals(targetAudienceList, pitch.targetAudienceList) &&
                Objects.equals(personas, pitch.personas) &&
                Objects.equals(detailedTargetAudienceMap, pitch.detailedTargetAudienceMap);
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(
                pitchID, name, image, description,
                targetAudienceList, personas, detailedTargetAudienceMap
        );
    }
}
