package interface_adapter.expert;

import interface_adapter.ViewManagerModel;
import use_case.expert.ExpertOutputBoundary;
import use_case.expert.ExpertOutputData;

/**
 * The presenter for the expert (show view) class.
 */
public class ExpertPresenter implements ExpertOutputBoundary {

    private ExpertViewModel expertViewModel;
    private ViewManagerModel viewManagerModel;

    public ExpertPresenter(ExpertViewModel expertViewModel, ViewManagerModel viewManagerModel) {
        this.expertViewModel = expertViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ExpertOutputData response) {
        final ExpertState expertState = expertViewModel.getState();
        expertState.setUsername(response.getUsername());
        expertState.setPassword(response.getPassword());

        expertViewModel.setState(expertState);
        expertViewModel.firePropertyChanged();

        this.viewManagerModel.setState(expertViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // assume cant fail (view switch)
    }
}
