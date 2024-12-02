package interface_adapter.targetaudience;

import use_case.set_targetaudience.DetailedInputBoundary;
import use_case.set_targetaudience.DetailedInputData;

/**
 * Controller class for generating detailed target audiences.
 */
public class DetailedController {
    private final DetailedInputBoundary detailedInputBoundary;

    public DetailedController(DetailedInputBoundary detailedInputBoundary) {
        this.detailedInputBoundary = detailedInputBoundary;
    }

    /**
     * Generates the DetailedTA based off of the input data.
     *
     * @param detailedInputData The inputData for the Detailed TA.
     * @throws Exception when there is an error with generating the DetailedTA.
     */
    public void generateDetailed(DetailedInputData detailedInputData) throws Exception {
        detailedInputBoundary.execute(detailedInputData);
    }
}
