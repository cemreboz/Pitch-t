package use_case.set_targetaudience;

import java.util.List;

import entity.DetailedTargetAudience;

/**
 * Class for DetailedTA OutputData.
 */
public class DetailedOutputData {

    private final List<DetailedTargetAudience> detailedTargetAudience;

    public DetailedOutputData(List<DetailedTargetAudience> detailedTargetAudience) {
        this.detailedTargetAudience = detailedTargetAudience;
    }

    public List<DetailedTargetAudience> getDetailedTargetAudience() {
        return detailedTargetAudience;
    }

}
