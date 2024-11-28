package interface_adapter.targetaudience;

import entity.DetailedTargetAudience;

/**
 * Class for the DetailedTargetAudienceState.
 */
public class DetailedTargetAudienceState {
    private boolean loading;
    private String errorMessage;
    private DetailedTargetAudience detailedTargetAudience;

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public DetailedTargetAudience getDetailedTargetAudience() {
        return detailedTargetAudience;
    }

    public void setDetailedTargetAudience(DetailedTargetAudience detailedTargetAudience) {
        this.detailedTargetAudience = detailedTargetAudience;
    }
}
