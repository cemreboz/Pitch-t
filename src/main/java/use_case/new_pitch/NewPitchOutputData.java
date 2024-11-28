package use_case.new_pitch;

import entity.User;

/**
 * Output data for the Create Pitch use case.
 * Contains the result of the use case execution.
 */
public class NewPitchOutputData {

    private final boolean useCaseFailed;
    private final User currentUser;

    public NewPitchOutputData(boolean useCaseFailed, User currentUser) {
        this.useCaseFailed = useCaseFailed;
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}