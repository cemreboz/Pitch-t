package use_case.set_targetaudience;

/**
 * The output bpoundary interface for the general TargetAudience.
 */
public interface TargetAudienceOutputBoundary {
    /**
     * Success view.
     * @param outputData the outputdata for General Target Audience.
     * @throws Exception if any error occurs during data fetching
     */
    void prepareSuccessView(TargetAudienceOuputData outputData) throws Exception;

    /**
     * Failure View.
     * @param errorMessage the error message.
     */
    void prepareFailView(String errorMessage);
}
