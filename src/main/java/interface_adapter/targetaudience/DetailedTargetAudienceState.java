package interface_adapter.targetaudience;

import java.util.List;

import entity.DetailedTargetAudience;

/**
 * Class for the DetailedTargetAudienceState.
 */
public class DetailedTargetAudienceState {
    private String errorMessage;
    private List<DetailedTargetAudience> detailedTargetAudience;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<DetailedTargetAudience> getDetailedTargetAudience() {
        return detailedTargetAudience;
    }

    public void setDetailedTargetAudience(List<DetailedTargetAudience> detailedTargetAudience) {
        this.detailedTargetAudience = detailedTargetAudience;
    }
}
