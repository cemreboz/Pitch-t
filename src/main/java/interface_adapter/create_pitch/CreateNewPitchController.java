package interface_adapter.create_pitch;

import interface_adapter.new_pitch.NewPitchPresenter;
import interface_adapter.new_pitch.NewPitchState;
import interface_adapter.new_pitch.NewPitchViewModel;
import use_case.new_pitch.NewPitchInputBoundary;
import use_case.new_pitch.NewPitchInputData;
import use_case.new_pitch.NewPitchOutputData;

import java.util.List;

/**
 * The controller for the Create New Pitch Use Case.
 */
public class CreateNewPitchController {
    private final NewPitchViewModel newPitchViewModel;

    private final NewPitchInputBoundary newPitchUseCaseInteractor;

    public CreateNewPitchController(NewPitchInputBoundary newPitchUseCaseInteractor) {
        this.newPitchUseCaseInteractor = newPitchUseCaseInteractor;
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
        final CreateNewPitchInputData createNewPitchInputData = new CreateNewPitchInputData();

        // Execute the use case to create the pitch
        newPitchUseCaseInteractor.execute(newPitchInputData);
    }

    /**
     * Executes the "switch to PitchView" Use Case.
     */
    public void switchToPitchView() {

    }
}
