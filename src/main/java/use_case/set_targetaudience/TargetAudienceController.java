package use_case.set_targetaudience;

import entity.Pitch;

/**
 * Class that generates the target audience.
 */
public class TargetAudienceController {

    private final TargetAudienceInteractor targetAudienceInteractor;

    public TargetAudienceController(TargetAudienceInteractor targetAudienceInteractor) {
        this.targetAudienceInteractor = targetAudienceInteractor;
    }

    /**
     * Generates the Target Audience based on the pitch.
     *
     * @param pitch the pitch itself.
     * @return the Target Audience based on the pitch.
     * @throws Exception if the API call doesn't work.
     */
    public String execute(Pitch pitch) throws Exception {
        return targetAudienceInteractor.generateTargetAudience(pitch);
    }
}
