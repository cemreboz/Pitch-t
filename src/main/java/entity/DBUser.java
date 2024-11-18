package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the User interface for DB use.
 */
public class DBUser implements User {

    private final String username;
    private final String password;
    private List<Pitch> pitches;
    private List<Expert> experts;

    public DBUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.pitches = new ArrayList<>();
        this.experts = new ArrayList<>();
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public List<Pitch> getPitches() {
        return pitches;
    }

    public void setPitches(List<Pitch> pitches) {
        this.pitches = pitches;
    }

    /**
     * Adds pitch to pitches list.
     * @param pitch is a pitch to be added
     */
    public void addPitch(Pitch pitch) {
        this.pitches.add(pitch);
    }

    /**
     * Removes pitch from pitches list.
     * @param pitch is a pitch to be removed
     */
    public void removePitch(Pitch pitch) {
        this.pitches.remove(pitch);
    }

    // Getters and setters for experts
    public List<Expert> getExperts() {
        return experts;
    }

    public void setExperts(List<Expert> experts) {
        this.experts = experts;
    }

    /**
     * Adds expert to pitches list.
     * @param expert is an expert to be added
     */
    public void addExpert(Expert expert) {
        this.experts.add(expert);
    }

    /**
     * Removes expert from pitches list.
     * @param expert is a expert to be removed
     */
    public void removeExpert(Expert expert) {
        this.experts.remove(expert);
    }
}
