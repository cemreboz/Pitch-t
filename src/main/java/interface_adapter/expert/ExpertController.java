package interface_adapter.expert;

import use_case.expert.ExpertInputBoundary;
import use_case.expert.ExpertInputData;

/**
 * The controller for the expert (show view) class.
 */
public class ExpertController {

    private final ExpertInputBoundary expertUseCaseInteractor;

    public ExpertController(ExpertInputBoundary expertInputBoundary) {
        this.expertUseCaseInteractor = expertInputBoundary;
    }

    /**
     * Executes the expert use case.
     * @param username username of user
     * @param password password of user
     */
    public void execute(String username, String password) {
        final ExpertInputData expertInputData = new ExpertInputData(username, password);
        expertUseCaseInteractor.execute(expertInputData);
    }

}
