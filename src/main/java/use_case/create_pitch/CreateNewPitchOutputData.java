package use_case.create_pitch;

public class CreateNewPitchOutputData {

    private final boolean success;
    private final String message;

    public CreateNewPitchOutputData(boolean success, String message) {
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
