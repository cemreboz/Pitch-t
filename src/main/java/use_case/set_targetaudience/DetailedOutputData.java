package use_case.set_targetaudience;

import entity.DetailedTargetAudience;

/**
 * Class for DetailedTA OutputData.
 */
public class DetailedOutputData {
    private final DetailedTargetAudience detailedTargetAudience;
    private final String errorMessage;

    public DetailedOutputData(DetailedTargetAudience detailedTargetAudience) {
        this.detailedTargetAudience = detailedTargetAudience;
        this.errorMessage = null;
    }

    public DetailedOutputData(String errorMessage) {
        this.detailedTargetAudience = null;
        this.errorMessage = errorMessage;
    }

    public DetailedTargetAudience getDetailedTargetAudience() {
        return detailedTargetAudience;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
