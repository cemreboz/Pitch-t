package interface_adapter.targetaudience;

import entity.DetailedTargetAudience;

/**
 * State class for Detailed Target Audience View.
 */
public class DetailedTargetAudienceState {
    private DetailedTargetAudience detailedTargetAudience;

    public DetailedTargetAudience getDetailedTargetAudience() {
        return detailedTargetAudience;
    }

    public void setDetailedTargetAudience(DetailedTargetAudience detailedTargetAudience) {
        this.detailedTargetAudience = detailedTargetAudience;
    }
}
