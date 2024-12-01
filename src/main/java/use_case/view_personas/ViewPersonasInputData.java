package use_case.view_personas;

import entity.Pitch;

/**
 * Input Data for the View Personas Use Case.
 */
public class ViewPersonasInputData {
    private final Pitch currentPitch;

    /**
     * Constructs the input data with the specified pitch ID.
     *
     * @param currentPitch the pitch.
     */
    public ViewPersonasInputData(Pitch currentPitch) {
        this.currentPitch = currentPitch;
    }

    /**
     * Retrieves the pitch.
     *
     * @return the pitch.
     */
    public Pitch getPitch() {
        return currentPitch;
    }
}

