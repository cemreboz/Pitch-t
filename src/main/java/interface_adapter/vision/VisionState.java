package interface_adapter.vision;

import entity.Persona;
import entity.Pitch;

/**
 * The state for the vision view model.
 */
public class VisionState {
    private Persona selectedPersona;
    private Pitch pitch;
    private String generatedImageUrl = "";
    private String errorMessage;
    private boolean isLoading;
    private String username;
    private String password;
    private boolean isSuccess;

    // Getters and Setters
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

    public Persona getSelectedPersona() {
        return selectedPersona;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public void setSelectedPersona(Persona selectedPersona) {
        this.selectedPersona = selectedPersona;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }

    public String getGeneratedImageUrl() {
        return generatedImageUrl;
    }

    public void setGeneratedImageUrl(String generatedImageUrl) {
        this.generatedImageUrl = generatedImageUrl;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}

