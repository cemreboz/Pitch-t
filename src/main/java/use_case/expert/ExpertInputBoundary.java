package use_case.expert;

/**
 * Input boundary for expert interactor.
 */
public interface ExpertInputBoundary {
    /**
     * Executes the dashboard show pitch use case.
     * @param expertInputData the input dat
     * */
    void execute(ExpertInputData expertInputData);

}
