package use_case.set_targetaudience;

import data_access.ChatgptDataAccessObject;
import entity.Pitch;

/**
 * Class that implements the interface TargetAudienceDataAccessInterface.
 */
public class TargetAudienceInteractor implements TargetAudienceDataAccessInterface {

    /**
     * Generates a list of target audiences based on a project description.
     *
     * @param pitch The pitch itself.
     * @return A list of target audience categories.
     * @throws Exception If any error occurs during data fetching.
     */
    @Override
    public String generateTargetAudience(Pitch pitch) throws Exception {
        validatePitch(pitch);

        final String systemMessage = """
                Based on the name and description of this project, I want you to give me a list of five \
                categories of people that would be interested in this project. Here is an example and how to structure:
                - Foodies;
                - Snack Enthusiasts;
                - Pickle Lovers;
                - Health-Conscious;
                - Construction workers;
                Your output must only contain the list, nothing else.""";

        final String userMessage = pitch.getName() + " " + pitch.getDescription();

        return ChatgptDataAccessObject.utilizeApi(systemMessage, userMessage);
    }

    /**
     * Validates the pitch to ensure required fields are present.
     *
     * @param pitch The pitch to validate.
     * @throws IllegalArgumentException If the pitch is invalid.
     */
    private void validatePitch(Pitch pitch) {
        if (pitch == null || pitch.getName() == null || pitch.getName().isBlank()
            || pitch.getDescription() == null || pitch.getDescription().isBlank()) {
            throw new IllegalArgumentException("Invalid Pitch: Name and Description must not be null or empty.");
        }
    }
}
