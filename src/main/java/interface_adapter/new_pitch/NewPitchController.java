package interface_adapter.new_pitch;

import use_case.create_pitch.NewPitchInputBoundary;
import use_case.create_pitch.NewPitchInputData;

import java.util.List;

public class NewPitchController {

    private final NewPitchInputBoundary newPitchUseCaseInteractor;
    private final NewPitchPresenter newPitchPresenter;

    public NewPitchController(NewPitchInputBoundary newPitchUseCaseInteractor, NewPitchPresenter newPitchPresenter) {
        this.newPitchUseCaseInteractor = newPitchUseCaseInteractor;
        this.newPitchPresenter = newPitchPresenter;
    }

    /**
     * Executes the Create New Pitch Use Case.
     *
     * @param name           the name of the pitch
     * @param description    the description of the pitch
     * @param image          the image URL/path associated with the pitch
     * @param targetAudience the list of target audiences for the pitch
     */
    public void execute(String name, String description, String image, List<String> targetAudience) {
        // Creating an input data object for the use case
        final NewPitchInputData newPitchInputData = new NewPitchInputData(
                name, description, image, targetAudience
        );

        // Execute the use case to create the pitch
        newPitchUseCaseInteractor.execute(newPitchInputData);
    }

    /**
     * Executes the "switch to PitchView" Use Case.
     */
    public void switchToPitchView() {

    }
}