package use_case.set_targetaudience;

import data_access.DetailedDataAccessObjectInterface;
import entity.Pitch;

/**
 * Public class for the Target Audience Interactor.
 */
public class TargetAudienceInteractor implements TargetAudienceDataAccessInterface {

    private final DetailedDataAccessObjectInterface dataAccessObject;

    public TargetAudienceInteractor(DetailedDataAccessObjectInterface dataAccessObject) {
        if (dataAccessObject == null) {
            throw new IllegalArgumentException("Data access object cannot be null.");
        }
        this.dataAccessObject = dataAccessObject;
    }

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

        // Use the injected instance of the DAO
        return dataAccessObject.utilizeApi(systemMessage, userMessage);
    }

    private void validatePitch(Pitch pitch) {
        if (pitch == null || pitch.getName() == null || pitch.getDescription() == null) {
            throw new IllegalArgumentException("Pitch, its name, or its description cannot be null.");
        }
    }
}
