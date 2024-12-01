package use_case.view_personas;

/**
 * Input Data for the View Personas Use Case.
 */
public class ViewPersonasInputData {
    private final String pitchID;

    /**
     * Constructs the input data with the specified pitch ID.
     *
     * @param pitchID the ID of the pitch.
     */
    public ViewPersonasInputData(String pitchID) {
        this.pitchID = pitchID;
    }

    /**
     * Retrieves the pitch ID.
     *
     * @return the pitch ID.
     */
    public String getPitchID() {
        return pitchID;
    }
}

