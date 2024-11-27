package use_case.create_pitch;

import data_access.DBUserDataAccessObject;
import entity.Pitch;

import java.util.List;

/**
 * The interactor for the Create Pitch use case.
 * This class implements the business logic for creating a new pitch.
 */
public class NewPitchInteractor implements NewPitchInputBoundary {

    private final DBUserDataAccessObject dbUserDataAccessObject;

    public NewPitchInteractor(DBUserDataAccessObject dbUserDataAccessObject) {
        this.dbUserDataAccessObject = dbUserDataAccessObject;
    }

    @Override
    public void execute(NewPitchInputData inputData) {
        // Validate the input data
        if (inputData.getName().isEmpty()) {
            // Here you can handle invalid input data (e.g., throw an exception or return an error message)
            throw new IllegalArgumentException("Pitch name cannot be empty");
        }

        if (inputData.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Pitch description cannot be empty");
        }

        if (inputData.getTargetAudienceList().isEmpty()) {
            throw new IllegalArgumentException("Target audience list cannot be empty");
        }

        // Create a new Pitch object from the input data
        Pitch newPitch = new Pitch(
                generatePitchID(),  // Generate a new unique pitch ID
                inputData.getName(),
                inputData.getDescription(),
                inputData.getTargetAudienceList()
        );

        // Save the pitch using the DBUserDataAccessObject (data access layer)
        dbUserDataAccessObject.savePitch(newPitch);

    }

    /**
     * Generates a new pitch ID based on the current timestamp.
     * @return the generated pitch ID.
     */
    private String generatePitchID() {
        // ID generation based on the current timestamp
        return String.valueOf(System.currentTimeMillis());
    }
}