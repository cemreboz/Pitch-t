package use_case.create_pitch;

/**
 * Output boundary for the Create Pitch use case.
 * Used for returning data from the interactor to the controller.
 */
public interface NewPitchOutputBoundary {

    /**
     * Sends the result of creating a new pitch.
     * @param success true if the pitch was created successfully, false otherwise.
     * @param message a message indicating success or failure.
     */
    void sendNewPitchResult(boolean success, String message);
}