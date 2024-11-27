package use_case.create_pitch;

import entity.Pitch;
import entity.DBUser;
// Todo: currently db user, should add pitch method be in interface user?

/**
 * Interactor for creating a new pitch and associating it with a user.
 */
public class NewPitchInteractor implements NewPitchInputBoundary {
    private final NewPitchDataAccess newPitchDataAccess;
    private final DBUser currentUser;

    /**
     * Constructor for the NewPitchInteractor.
     *
     * @param newPitchDataAccess Gateway for pitch-related data access.
     * @param currentUser        The currently logged-in user.
     */
    public NewPitchInteractor(NewPitchDataAccess newPitchDataAccess, DBUser currentUser) {
        this.newPitchDataAccess = newPitchDataAccess;
        this.currentUser = currentUser;
    }

    @Override
    public void execute(NewPitchInputData inputData) {
        // Validate input
        if (inputData.getName().isEmpty()) throw new IllegalArgumentException("Pitch name cannot be empty");
        if (inputData.getDescription().isEmpty()) throw new IllegalArgumentException("Pitch description cannot be empty");
        if (inputData.getTargetAudienceList().isEmpty()) throw new IllegalArgumentException("Target audience list cannot be empty");
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