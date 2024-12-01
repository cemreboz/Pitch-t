package use_case.view_personas;

import entity.DetailedTargetAudience;
import entity.Pitch;

/**
 * Input Data for the View Personas Use Case.
 */
public class ViewPersonasInputData {
    private final Pitch currentPitch;
    private final DetailedTargetAudience currentAudience;

    /**
     * Constructs the input data with the specified pitch ID.
     *
     * @param currentPitch the pitch.
     */
    public ViewPersonasInputData(Pitch currentPitch, DetailedTargetAudience currentAudience) {
        this.currentPitch = currentPitch;
        this.currentAudience = currentAudience;
    }

    /**
     * Retrieves the pitch.
     *
     * @return the pitch.
     */
    public Pitch getPitch() {
        return currentPitch;
    }

    /**
     * Retrieves the Target audience.
     *
     * @return the pitch ID.
     */
    public DetailedTargetAudience getCurrentAudience() {
        return currentAudience;
    }

}

