package interface_adapter.create_pitch;

import interface_adapter.new_pitch.NewPitchPresenter;
import interface_adapter.new_pitch.NewPitchState;
import interface_adapter.new_pitch.NewPitchViewModel;
import use_case.create_pitch.CreateNewPitchInputBoundary;
import use_case.create_pitch.CreateNewPitchInputData;
import use_case.create_pitch.CreateNewPitchOutputData;
import use_case.new_pitch.NewPitchInputBoundary;
import use_case.new_pitch.NewPitchInputData;
import use_case.new_pitch.NewPitchOutputData;

import java.util.List;

/**
 * The controller for the Create New Pitch Use Case.
 */
public class CreateNewPitchController {

    private final CreateNewPitchInputBoundary createNewPitchInputBoundary;

    public CreateNewPitchController(CreateNewPitchInputBoundary createNewPitchUseCaseInteractor) {
        this.createNewPitchInputBoundary = createNewPitchUseCaseInteractor;
    }

    /**
     * Executes the Create New Pitch Use Case.
     *
     * @param name           the name of the pitch
     * @param description    the description of the pitch
     * @param image          the image URL/path associated with the pitch
     */
    public void execute(String name, String description, String image) {
        // Creating an input data object for the use case
        final CreateNewPitchInputData createNewPitchInputData = new CreateNewPitchInputData(name, description, image);

        // Execute the use case to create the pitch
        createNewPitchInputBoundary.execute(createNewPitchInputData);
    }

    /**
     * Executes the "switch to PitchView" Use Case.
     */
    public void switchToPitchView() {

    }

}
