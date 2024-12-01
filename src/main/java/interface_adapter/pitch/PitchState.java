package interface_adapter.pitch;

import java.util.ArrayList;

import data_access.ChatgptDataAccessObject;
import entity.Pitch;
import interface_adapter.targetaudience.TargetAudiencePresenter;
import use_case.set_targetaudience.TargetAudienceDataAccessInterface;
import use_case.set_targetaudience.TargetAudienceInputData;
import use_case.set_targetaudience.TargetAudienceInteractor;
import use_case.set_targetaudience.TargetAudienceOutputBoundary;

/**
 * The state for the pitch view model.
 */
public class PitchState {
    private Pitch pitch = new Pitch("", "", "", "", "");
    private String username = "";
    private String password = "";
    private String pitchLoadError;
    private String detailedTaLoadError;
    private String targetaudience = "";

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

    public void setTargetAudience() throws Exception {
        final TargetAudienceInputData inputData = new TargetAudienceInputData(pitch.getName(), pitch.getDescription());
        final TargetAudienceDataAccessInterface dataAccessObject = new ChatgptDataAccessObject();
        final PitchViewModel pitchViewModel = new PitchViewModel();
        final TargetAudienceOutputBoundary outputBoundary = new TargetAudiencePresenter(pitchViewModel);
        final TargetAudienceInteractor targetAudienceInteractor = new TargetAudienceInteractor(dataAccessObject,
                outputBoundary);
        targetAudienceInteractor.execute(inputData);
    }

    public String getTargetAudience() {
        return targetaudience;
    }

    /**
     * It's supposed to return the error message if there is one.
     * @param errorMessage the error message.
     */
    public void setErrorMessage(String errorMessage) {
        // TODO document why this method is empty
    }
}
