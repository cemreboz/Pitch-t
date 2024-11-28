package use_case.create_pitch;

import entity.Pitch;
import use_case.new_pitch.NewPitchInputData;

/**
 * Interactor for creating a new pitch and associating it with a user.
 */
public class CreateNewPitchInteractor {
    private CreateNewPitchDataAccessInterface userDataAccessObject;
    private final CreateNewPitchOutputBoundary userPresenter;

    /**
     * Constructor for the CreateNewPitchInteractor.
     *
     * @param createNewPitchDataAccessInterface Gateway for pitch-related data access.
     * @param createNewPitchOutputBoundary the presenter to be used after this interactor
     */
    public CreateNewPitchInteractor(CreateNewPitchDataAccessInterface createNewPitchDataAccessInterface,
                                    CreateNewPitchOutputBoundary createNewPitchOutputBoundary) {
        this.userDataAccessObject = createNewPitchDataAccessInterface;
        this.userPresenter = createNewPitchOutputBoundary;
    }

    @Override
    public void execute(NewPitchInputData inputData) {
        // Validate input
        if (inputData.getName().isEmpty()) {
            throw new IllegalArgumentException("Pitch name cannot be empty");
        }
        if (inputData.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Pitch description cannot be empty");
        }
        if (inputData.getTargetAudienceList().isEmpty()) {
            throw new IllegalArgumentException("Target audience list cannot be empty");
        }

    // Image is nice but not mandatory.
    // TODO: validate image somehow?

    // Create a new Pitch
    Pitch newPitch = new Pitch(
            generatePitchID(),
            inputData.getName(),
            inputData.getImage(),  // Image is optional and can be null
            inputData.getDescription(),
            inputData.getTargetAudienceList()
    );

    // Add the pitch to the user's in-memory list
        currentUser.addPitch(newPitch);

    // Use the gateway to persist the pitch
        newPitchDataAccess.addPitch(currentUser.getName(), newPitch);
    }

    /**
     * Generates a unique pitch ID. (Cause I like giving id to things)
     *
     * @return The generated pitch ID.
     */
    private String generatePitchID() {
        return String.valueOf(System.currentTimeMillis());
    }
}
