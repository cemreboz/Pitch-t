package use_case.set_targetaudience;

/**
 * Input boundary for the Detailed Interactor.
 * Defines the contract for fetching detailed target audiences based on a category.
 */
public interface DetailedInputBoundary {

    /**
     * Method for executing the DetailedTA based on the input Data.
     * @param inputData from the input data class.
     */
    void execute(DetailedInputData inputData);
}
