package use_case.set_targetaudience;

import data_access.ChatgptDataAccessObject;
import entity.Pitch;

/**
 * Class that implements the Interface TargetAudienceRepository.
 */
public class TargetAudienceRepositoryImpl implements TargetAudienceRepository {

    /**
     * Generates a list of target audiences based on a project description.
     *
     * @param pitch The pitch itself.
     * @return A list of target audience categories.
     * @throws Exception If any error occurs during data fetching.
     */
    @Override
    public String generateTargetAudience(Pitch pitch) throws Exception {
        final String systemMessage = """
                Based on the name and description of this project, I want you to give me a list of five \
                categories of people that would be interested in this project. Here is an example and how to structure:
                - Foodies;
                - Snack Enthusiasts;
                - Pickle Lovers;
                - Health-Conscious;
                - Construction workers;
                Your output must only contain the list, nothing else.""";

        // Use the instance of Pitch to get the name and description
        final String userMessage = pitch.getName() + " " + pitch.getDescription();

        return ChatgptDataAccessObject.utilizeApi(systemMessage, userMessage);
    }
}
