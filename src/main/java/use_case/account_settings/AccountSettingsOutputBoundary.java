package use_case.account_settings;

/**
 * The output boundary for the account settings use case.
 */
public interface AccountSettingsOutputBoundary {
    /**
     * Prepares the success view for the Account Settings Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AccountSettingsOutputData outputData);

    /**
     * Prepares the failure view for the Account Settings Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
