package interface_adapter.new_pitch;

import java.util.ArrayList;
import java.util.List;

import entity.DBUser;
import entity.Pitch;
import entity.User;
import use_case.set_targetaudience.TargetAudienceInteractor;

/**
 * The state for the New Pitch View Model.
 */
public class NewPitchState {
    private final TargetAudienceInteractor targetAudienceInteractor;
    private User currentUser = new DBUser("", "");
    private String name = "";
    private String description = "";
    private String image = "";
    private List<String> targetAudience = new ArrayList<>();
    private String errorMessage;
    private boolean isLoading;
    private boolean isSuccess;

    public NewPitchState(TargetAudienceInteractor targetAudienceInteractor) {
        this.targetAudienceInteractor = targetAudienceInteractor;
    }

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

    /**
     * Sets the TargetAudience using the interactor.
     * @throws IllegalArgumentException if the Pitch is empty.
     */
    public void setTargetAudience() {
        if (description.isEmpty() || name.isEmpty()) {
            throw new IllegalArgumentException("Pitch name and description cannot be empty when generating target audience.");
        }

        try {
            final Pitch pitch = new Pitch(
                    "temp-id",
                    name,
                    image,
                    description,
                    targetAudience
            );

            // Generate target audience using the interactor
            final String generatedAudience = targetAudienceInteractor.generateTargetAudience(pitch);

            // Convert generated audience into a list and set it
            this.targetAudience = List.of(generatedAudience.split(";"));
        }
        catch (Exception exception) {
            // Handle errors in generation
            this.errorMessage = "Failed to generate target audience: " + exception.getMessage();
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
