package interface_adapter.targetaudience;

import use_case.set_targetaudience.TargetAudienceInputBoundary;
import use_case.set_targetaudience.TargetAudienceInputData;

/**
 * Controller class that generates the target audience.
 */
public class TargetAudienceController {
    private final TargetAudienceInputBoundary inputBoundary;
    
    public TargetAudienceController(TargetAudienceInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Generates the Target Audience.
     * @param inputData The Input data for the general Target Audience.
     * @return the generated TA.
     * @throws Exception if there is an error with generating the target audience.
     */
    public String generate(TargetAudienceInputData inputData) throws Exception {
        return inputBoundary.execute(inputData);
    }
}
