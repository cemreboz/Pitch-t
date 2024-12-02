package use_case.set_targetaudience;

import org.json.JSONException;

/**
 * Public class for the Target Audience Interactor.
 */
public class TargetAudienceInteractor implements TargetAudienceInputBoundary {

    private final TargetAudienceDataAccessInterface dataAccessObject;
    private final TargetAudienceOutputBoundary outputBoundary;

    public TargetAudienceInteractor(TargetAudienceDataAccessInterface dataAccessObject,
                                    TargetAudienceOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
        this.dataAccessObject = dataAccessObject;
    }

    /**
     * Method for executing the DetailedTA based on the input Data.
     *
     * @param inputData from the input data class.
     */
    @Override
    public String execute(TargetAudienceInputData inputData) {
        if (inputData == null) {
            throw new IllegalArgumentException("inputData must not be null");
        }
        String response = "";
        final String systemMessage = """
            Based on the name and description of this project, I want you to give me a list of five \
            categories of people that would be interested in this project. Here is an example and how to structure:
            - Foodies;
            - Snack Enthusiasts;
            - Pickle Lovers;
            - Health-Conscious;
            - Construction workers;
            Your output must only contain the list, nothing else.""";
        final String userMessage = inputData.getPitchname() + " " + inputData.getPitchdescription();
        try {
            response = dataAccessObject.generateTargetAudience(systemMessage, userMessage);
            final TargetAudienceOuputData outputData = new TargetAudienceOuputData(response);
            outputBoundary.prepareSuccessView(outputData);
        }
        catch (JSONException exception) {
            outputBoundary.prepareFailView("Error with getting the Detailed Target Audience");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

}
