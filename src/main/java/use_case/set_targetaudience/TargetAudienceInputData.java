package use_case.set_targetaudience;

/**
 * Class for the input data for the general Target Audience.
 */
public class TargetAudienceInputData {
    private final String pitchname;
    private final String pitchdescription;

    public TargetAudienceInputData(String pitchname, String pitchdescription) {
        this.pitchname = pitchname;
        this.pitchdescription = pitchdescription;
    }

    public String getPitchname() {
        return pitchname;
    }

    public String getPitchdescription() {
        return pitchdescription;
    }
}
