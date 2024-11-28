package interface_adapter.compare_personas;

import entity.Pitch;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Dashboard View Model.
 */
public class PersonaComparisonState {
    private String username = "";
    private String password = "";
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
