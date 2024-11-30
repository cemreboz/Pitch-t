package use_case.set_targetaudience;

import entity.DetailedTargetAudience;

/**
 * Class for DetailedTA OutputData.
 */
public class DetailedOutputData {

    private final DetailedTargetAudience detailedTargetAudience;

    public DetailedOutputData(DetailedTargetAudience detailedTargetAudience) {
        this.detailedTargetAudience = detailedTargetAudience;
    }

    public DetailedTargetAudience getDetailedTargetAudience() {
        return detailedTargetAudience;
    }

}
