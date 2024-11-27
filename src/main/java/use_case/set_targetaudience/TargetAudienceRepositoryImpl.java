package use_case.set_targetaudience;

import app.Application;
import entity.Pitch;

/**
 * Class that implements the Interface TargetAudienceRepository.
 */
public class TargetAudienceRepositoryImpl implements TargetAudienceRepository {

    @Override
    public String generateTargetAudience(String projectDescription) throws Exception {
        // Example implementation: Call an API or mock data for now
        final String systemMessage = """
                Based on the name and description of this project, I want you to give me a list of five \
                categories of people that would be interested in this project. Here is an example and how to structure:
                - Foodies;
                - Snack Enthusiasts;
                - Pickle Lovers;
                - Health-Conscious;
                - Construction workers;
                Your output must only contain the list, nothing else.""";
        return Application.utilizeApi(systemMessage, Pitch.getName() + " " + Pitch.getDescription());
    }
}
