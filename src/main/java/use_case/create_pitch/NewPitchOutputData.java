package use_case.create_pitch;

/**
 * Output data for the Create Pitch use case.
 * Contains the result of the use case execution.
 */
public class NewPitchOutputData {

    private final boolean success;
    private final String message;

    public NewPitchOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}