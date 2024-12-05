package interface_adapter.create_pitch;

import use_case.create_pitch.CreateNewPitchInputBoundary;
import use_case.create_pitch.CreateNewPitchInputData;

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
     * @param targetAudience the list of target audiences for the pitch
     * @throws Exception if there is an error with calling this method.
     */
    public void execute(String name, String description, String image, String targetAudience) throws Exception {
        // Creating an input data object for the use case
        final CreateNewPitchInputData createNewPitchInputData = new CreateNewPitchInputData(name, description,
                image, targetAudience);

        // Execute the use case to create the pitch
        createNewPitchInputBoundary.execute(createNewPitchInputData);
    }

    /**
     * Executes the "switch to PitchView" Use Case.
     */
    public void switchToPitchView() {

    }
}
