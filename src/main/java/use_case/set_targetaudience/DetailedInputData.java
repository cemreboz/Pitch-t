package use_case.set_targetaudience;

/**
 * Class for the Detailed Target Audience's Input Data.
 */
public class DetailedInputData {
    private final String pitchname;
    private final String pitchdescription;
    private final String audiencecategory;

    public DetailedInputData(String pitchname, String pitchdescription, String audiencecategory) {
        this.pitchname = pitchname;
        this.pitchdescription = pitchdescription;
        this.audiencecategory = audiencecategory;
    }

    public String getPitchname() {
        return pitchname;
    }

    public String getPitchdescription() {
        return pitchdescription;
    }

    public String getAudiencecategory() {
        return audiencecategory;
    }
}
