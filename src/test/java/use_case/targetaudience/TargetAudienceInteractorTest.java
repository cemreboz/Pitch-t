package use_case.targetaudience;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.json.JSONException;
import use_case.set_targetaudience.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TargetAudienceInteractor.
 */
class TargetAudienceInteractorTest {

    @Test
    void executeFailureTest() throws Exception {
        // Create input data
        String pitchName = "Organic Pickles";
        String pitchDescription = "A project that provides homemade organic pickles.";
        TargetAudienceInputData inputData = new TargetAudienceInputData(pitchName, pitchDescription);

        // Create a mock data access object that throws an exception
        TargetAudienceDataAccessInterface dataAccessObject = new TargetAudienceDataAccessInterface() {
            @Override
            public String generateTargetAudience(String systemMessage, String userMessage) throws Exception {
                throw new JSONException("Test JSON Exception");
            }
        };

        // Create a mock output boundary
        TargetAudienceInteractor interactor = getInteractor(dataAccessObject);

        // Execute the use case
        interactor.execute(inputData);
    }

    @NotNull
    private static TargetAudienceInteractor getInteractor(TargetAudienceDataAccessInterface dataAccessObject) {
        TargetAudienceOutputBoundary outputBoundary = new TargetAudienceOutputBoundary() {
            @Override
            public void prepareSuccessView(TargetAudienceOuputData outputData) {
                fail("Output boundary should not be called for a failure case.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                // Assertions to verify the error message
                assertEquals("Error with getting the Detailed Target Audience", errorMessage);
            }
        };

        // Create the interactor
        return new TargetAudienceInteractor(dataAccessObject, outputBoundary);
    }

    @Test
    void executeWithNullInputTest() {
        // Arrange
        TargetAudienceInputData inputData = null;

        // Create a mock data access object
        TargetAudienceDataAccessInterface dataAccessObject = new TargetAudienceDataAccessInterface() {
            @Override
            public String generateTargetAudience(String systemMessage, String userMessage) throws Exception {
                return null;
            }
        };

        // Create a mock output boundary
        TargetAudienceInteractor interactor = getTargetAudienceInteractor(dataAccessObject);

        // Expect an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            interactor.execute(inputData);
        });

        assertEquals("inputData must not be null", exception.getMessage());
    }

    @NotNull
    private static TargetAudienceInteractor getTargetAudienceInteractor(TargetAudienceDataAccessInterface dataAccessObject) {
        TargetAudienceOutputBoundary outputBoundary = new TargetAudienceOutputBoundary() {
            @Override
            public void prepareSuccessView(TargetAudienceOuputData outputData) {
                fail("Output boundary should not be called when input is null.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Output boundary should not be called when input is null.");
            }
        };

        // Create the interactor
        return new TargetAudienceInteractor(dataAccessObject, outputBoundary);
    }

    @Test
    void executeWithEmptyInputTest() throws Exception {
        // Create input data with empty fields
        String pitchName = "";
        String pitchDescription = "";
        TargetAudienceInputData inputData = new TargetAudienceInputData(pitchName, pitchDescription);

        // Create a mock data access object
        TargetAudienceDataAccessInterface dataAccessObject = new TargetAudienceDataAccessInterface() {
            @Override
            public String generateTargetAudience(String systemMessage, String userMessage) throws Exception {
                // Return a mock response for empty input
                return "- General Audience;";
            }
        };

        // Create a mock output boundary
        TargetAudienceInteractor interactor = getAudienceInteractor(dataAccessObject);

        // Execute the use case
        interactor.execute(inputData);
    }

    @NotNull
    private static TargetAudienceInteractor getAudienceInteractor(TargetAudienceDataAccessInterface dataAccessObject) {
        TargetAudienceOutputBoundary outputBoundary = new TargetAudienceOutputBoundary() {
            @Override
            public void prepareSuccessView(TargetAudienceOuputData outputData) {
                // Assertions to verify the output data
                assertEquals("- General Audience;", outputData.getTargetAudience());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Output boundary should not be called for empty input.");
            }
        };

        // Create the interactor
        return new TargetAudienceInteractor(dataAccessObject, outputBoundary);
    }
}
