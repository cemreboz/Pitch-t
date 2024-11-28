package use_case.create_pitch;

import entity.Pitch;

/**
 * Gateway interface for handling pitch-related data access.
 */
public interface CreateNewPitchDataAccessInterface {

    /**
     * Adds a pitch to the specified user.
     *
     * @param userId the ID of the user to associate the pitch with.
     * @param pitch  the pitch to add.
     * */
    void addPitch(String userId, Pitch pitch);
}
