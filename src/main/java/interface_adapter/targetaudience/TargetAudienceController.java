package interface_adapter.targetaudience;

import entity.Pitch;
import interface_adapter.pitch.PitchState;
import use_case.set_targetaudience.TargetAudienceInputBoundary;
import use_case.set_targetaudience.TargetAudienceInputData;

/**
 * Controller class that generates the target audience.
 */
public class TargetAudienceController {
    private final TargetAudienceInputBoundary inputBoundary = null;
    
    public TargetAudienceController(TargetAudienceInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }
    
    public generate(TargetAudienceInputData inputdata) {
        inputBoundary.execute(inputdata);
    }
}
