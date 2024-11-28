package interface_adapter.targetaudience;

import entity.DetailedTargetAudience;

/**
 * ViewModel for the Detailed Target Audience UI.
 */
public class DetailedTargetAudiencePageViewModel {
    private DetailedTargetAudience detailedTargetAudience;
    private String errorMessage;

    public DetailedTargetAudience getDetailedTargetAudience() {
        return detailedTargetAudience;
    }

    /**
     * Updates the Target Audience.
     * @param detailedTargetAudience the detailed target audience.
     */
    public void updateDetailedTargetAudience(DetailedTargetAudience detailedTargetAudience) {
        this.detailedTargetAudience = detailedTargetAudience;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
