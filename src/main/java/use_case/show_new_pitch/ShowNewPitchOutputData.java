package use_case.show_new_pitch;

import entity.User;

/**
 * Output data for the Show New Pitch use case.
 * Contains the result of the use case execution.
 */
public class ShowNewPitchOutputData {

    private final boolean useCaseFailed;
    private final User currentUser;

    public ShowNewPitchOutputData(boolean useCaseFailed, User currentUser) {
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