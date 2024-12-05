package use_case.create_pitch;

import entity.Pitch;

/**
 * Output data for the Create Pitch use case.
 * Encapsulates the results and metadata of the use case execution.
 */
public class CreateNewPitchOutputData {

    private final boolean useCaseFailed;
    private final Pitch newPitch;
    private final String username;
    private final String password;

    public CreateNewPitchOutputData(boolean useCaseFailed, Pitch newPitch, String username, String password) {
        this.useCaseFailed = useCaseFailed;
        this.newPitch = newPitch;
        this.username = username;
        this.password = password;
    }

    /**
     * Checks if the use case execution failed.
     *
     * @return {@code true} if the use case failed; {@code false} otherwise
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    /**
     * Gets the newly created pitch object.
     *
     * @return the newly created {@link Pitch} object
     */
    public Pitch getNewPitch() {
        return newPitch;
    }

    /**
     * Gets the username of the user who created the pitch.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user who created the pitch.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }
}
