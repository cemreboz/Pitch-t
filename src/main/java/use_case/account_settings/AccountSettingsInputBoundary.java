package use_case.account_settings;

/**
 * Input boundary for account settings interactor.
 */
public interface AccountSettingsInputBoundary {

    /**
     * Executes the account settings use case.
     * @param accountSettingsInputData the input data
     */
    void execute(AccountSettingsInputData accountSettingsInputData);

}
