package interface_adapter.create_pitch;

import java.util.ArrayList;
import java.util.List;

import entity.DBUser;
import entity.User;

/**
 * The state for the New Pitch View Model.
 */
public class CreateNewPitchState {
    private User currentUser = new DBUser("", "");
    private String name = "";
    private String description = "";
    private String image = "";
    private List<String> targetAudience = new ArrayList<>();
    private String errorMessage;
    private boolean isLoading;
    private boolean isSuccess;

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public List<String> getTargetAudience() {
        return targetAudience;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTargetAudience(List<String> targetAudience) {
        this.targetAudience = targetAudience;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
