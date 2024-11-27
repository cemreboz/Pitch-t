package use_case.set_targetaudience;

import entity.Pitch;

/**
 * Controller class that generates the target audience.
 */
public class TargetAudienceController {

    private final TargetAudienceInteractor targetAudienceInteractor;

    /**
     * Constructor for TargetAudienceController.
     *
     * @param targetAudienceInteractor The interactor responsible for generating the target audience.
     * @throws IllegalArgumentException If the targetaudienceinteractor is null.
     */
    public TargetAudienceController(TargetAudienceInteractor targetAudienceInteractor) {
        if (targetAudienceInteractor == null) {
            throw new IllegalArgumentException("TargetAudienceInteractor cannot be null.");
        }
        this.targetAudienceInteractor = targetAudienceInteractor;
    }

    /**
     * Generates the Target Audience based on the pitch.
     *
     * @param pitch The pitch itself.
     * @return The Target Audience based on the pitch.
     * @throws Exception If an error occurs during audience generation.
     * @throws IllegalArgumentException if there is no pitch.
     */
    public String execute(Pitch pitch) throws Exception {
        if (pitch == null) {
            throw new IllegalArgumentException("Pitch cannot be null.");
        }
        return targetAudienceInteractor.generateTargetAudience(pitch);
    }
}
