package use_case.expert;

/**
 * The expert interactor, for showing experts.
 */
public class ExpertInteractor implements ExpertInputBoundary {
    private ExpertDataAccessInterface userDataAccessObject;
    private ExpertOutputBoundary expertPresenter;

    public ExpertInteractor(ExpertDataAccessInterface userDataAccessObject, ExpertOutputBoundary expertPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.expertPresenter = expertPresenter;
    }

    @Override
    public void execute(ExpertInputData expertInputData) {

        final ExpertOutputData expertOutputData = new ExpertOutputData(false, expertInputData.getUsername(),
                expertInputData.getPassword());
        expertPresenter.prepareSuccessView(expertOutputData);
    }
}
