package interface_adapter.pitch;

import entity.Pitch;

/**
 * The state for the pitch view model.
 */
public class PitchState {
    private Pitch pitch;
    private String pitchLoadError;
    private String detailedTaLoadError;

    public Pitch getPitch() {
        return pitch;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
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
}
