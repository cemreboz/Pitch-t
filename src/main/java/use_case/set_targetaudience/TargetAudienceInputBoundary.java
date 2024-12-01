package use_case.set_targetaudience;

/**
 * Input boundary for the Detailed Interactor.
 * Defines the contract for fetching detailed target audiences based on a category.
 */
public interface TargetAudienceInputBoundary {

    /**
     * Method for executing the DetailedTA based on the input Data.
     * @param inputData from the input data class.
     * @return the Target Audience.
     * @throws Exception If it cannot get the Detailed Target Audience.                 
     */
    void execute(TargetAudienceInputData inputData) throws Exception;
}
