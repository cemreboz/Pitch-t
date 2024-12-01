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
     * @param currentPitch the ID of the pitch.
     */
    public ViewPersonasInputData(Pitch currentPitch) {
        this.currentPitch = currentPitch;
    }

    /**
     * Retrieves the pitch ID.
     *
     * @return the pitch ID.
     */
    public Pitch getPitch() {
        return currentPitch;
    }
}

