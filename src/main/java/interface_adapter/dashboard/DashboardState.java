package interface_adapter.dashboard;

import java.util.ArrayList;
import java.util.List;

import entity.Pitch;

/**
 * The state for the Dashboard View Model.
 */
public class DashboardState {
    private String username = "";
    private String pitchLoadError;
    private List<Pitch> pitches = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Pitch> getPitches() {
        return pitches;
    }

    public void setPitches(List<Pitch> pitches) {
        this.pitches = pitches;
    }

    public String getPitchLoadError() {
        return pitchLoadError;
    }

    public void setPitchLoadError(String pitchLoadError) {
        this.pitchLoadError = pitchLoadError;
    }
}
