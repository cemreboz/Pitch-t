package interface_adapter.pitch;

import java.util.ArrayList;
import java.util.List;

import entity.Pitch;

/**
 * The state for the pitch view model.
 */
public class PitchState {
    private Pitch pitch = new Pitch("", "", "", "", new ArrayList<>());
    private String username = "";
    private String password = "";
    private String pitchLoadError;
    private String detailedTaLoadError;
    private List<String> targetaudience = new ArrayList<>();

    public Pitch getPitch() {
        return pitch;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPitchLoadError() {
        return pitchLoadError;
    }

    public void setPitchLoadError(String pitchLoadError) {
        this.pitchLoadError = pitchLoadError;
    }

    public String getDetailedTaLoadError() {
        return detailedTaLoadError;
    }

    public void setDetailedTaLoadError(String detailedTaLoadError) {
        this.detailedTaLoadError = detailedTaLoadError;
    }

    public void setTargetAudience(List<String> targetAudience) {
        this.targetaudience = targetAudience;
    }

    /**
     * To determine whether the target audience is loading.
     * @param loading return whether it's loading or not.
     */
    public void setLoading(boolean loading) {
        // TODO document why this method is empty
    }

    /**
     * It's supposed to return the error message if there is one.
     * @param errorMessage the error message.
     */
    public void setErrorMessage(String errorMessage) {
        // TODO document why this method is empty
    }
}
