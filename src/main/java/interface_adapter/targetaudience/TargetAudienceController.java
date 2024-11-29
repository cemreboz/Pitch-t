package interface_adapter.targetaudience;

import entity.Pitch;
import interface_adapter.pitch.PitchState;

/**
 * Controller class that generates the target audience.
 */
public class TargetAudienceController {

    private final TargetAudiencePresenter targetAudiencePresenter;

    /**
     * Constructor for TargetAudienceController.
     *
     * @param targetAudiencePresenter The presenter responsible for generating the target audience.
     * @throws IllegalArgumentException If the targetAudiencePresenter is null.
     */
    public TargetAudienceController(TargetAudiencePresenter targetAudiencePresenter) {
        if (targetAudiencePresenter == null) {
            throw new IllegalArgumentException("TargetAudiencePresenter cannot be null.");
        }
        this.targetAudiencePresenter = targetAudiencePresenter;
    }

    /**
     * Fetches and updates the Target Audience for a given pitch.
     * @return returns the target audience.
     * @param pitch The pitch for which the target audience needs to be generated.
     * @throws IllegalArgumentException if the pitch is null.
     */
    public String fetchAndUpdateTargetAudience(Pitch pitch) {
        if (pitch == null) {
            throw new IllegalArgumentException("Pitch cannot be null.");
        }

        // Call the presenter to fetch and update the target audience.
        targetAudiencePresenter.fetchTargetAudience(pitch);
        final PitchState pitchState = new PitchState();
        // Optionally, you can return a success message or delegate further actions as needed.
        return String.join(";", pitchState.getTargetAudience());
    }
}
