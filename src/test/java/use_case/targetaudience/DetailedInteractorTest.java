package use_case.targetaudience;

import data_access.ChatgptDataAccessObject;
import org.junit.jupiter.api.Test;
import org.json.JSONException;
import use_case.set_targetaudience.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DetailedInteractor.
 */
public class DetailedInteractorTest {

    @Test
    void executeSuccessTest() throws Exception {
        // Create input data
        String pitchName = "Organic Pickles";
        String pitchDescription = "A project that provides homemade organic pickles.";
        String audienceCategory = "Health-Conscious";
        DetailedInputData inputData = new DetailedInputData(pitchName, pitchDescription, audienceCategory);

        // Create a mock data access object
        DetailedtaDataAccessInterface dataAccessObject = new ChatgptDataAccessObject();

        // Create a mock output boundary
        DetailedOutputBoundary outputBoundary = new DetailedOutputBoundary() {
            @Override
            public void prepareSuccessView(DetailedOutputData outputData) {
                // Assertions to verify the output data
                assertEquals("Health-Conscious Individuals", outputData.getDetailedTargetAudience().get(0).getName());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Output boundary should not be called for a successful case.");
            }
        };

        // Create the interactor
        DetailedInteractor interactor = new DetailedInteractor(dataAccessObject, outputBoundary);

        // Execute the use case
        interactor.execute(inputData);
    }

    @Test
    void executeFailureTest() throws Exception {
        // Create input data
        String pitchName = "Organic Pickles";
        String pitchDescription = "A project that provides homemade organic pickles.";
        String audienceCategory = "Health-Conscious";
        DetailedInputData inputData = new DetailedInputData(pitchName, pitchDescription, audienceCategory);

        // Create a mock data access object that throws an exception
        DetailedtaDataAccessInterface dataAccessObject = new DetailedtaDataAccessInterface() {
            @Override
            public String utilizeApi(String systemMessage, String userMessage) throws Exception {
                throw new JSONException("Test JSON Exception");
            }
        };

        // Create a mock output boundary
        DetailedOutputBoundary outputBoundary = new DetailedOutputBoundary() {
            @Override
            public void prepareSuccessView(DetailedOutputData outputData) {
                fail("Output boundary should not be called for a failure case.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                // Assertions to verify the error message
                assertEquals("Error with getting the Detailed Target Audience", errorMessage);
            }
        };

        // Create the interactor
        DetailedInteractor interactor = new DetailedInteractor(dataAccessObject, outputBoundary);

        // Execute the use case
        interactor.execute(inputData);
    }

    @Test
    void executeWithNullInputTest() {
        // Arrange
        DetailedInputData inputData = null;

        // Create a mock data access object
        DetailedtaDataAccessInterface dataAccessObject = new DetailedtaDataAccessInterface() {
            @Override
            public String utilizeApi(String systemMessage, String userMessage) throws Exception {
                return null;
            }
        };

        // Create a mock output boundary
        DetailedOutputBoundary outputBoundary = new DetailedOutputBoundary() {
            @Override
            public void prepareSuccessView(DetailedOutputData outputData) {
                fail("Output boundary should not be called when input is null.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Output boundary should not be called when input is null.");
            }
        };

        // Create the interactor
        DetailedInteractor interactor = new DetailedInteractor(dataAccessObject, outputBoundary);

        // Expect an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            interactor.execute(inputData);
        });

        assertEquals("inputData must not be null", exception.getMessage());
    }

    @Test
    void executeWithEmptyCategoryTest() throws Exception {
        // Create input data with empty category
        String pitchName = "Organic Pickles";
        String pitchDescription = "A project that provides homemade organic pickles.";
        String audienceCategory = "";
        DetailedInputData inputData = new DetailedInputData(pitchName, pitchDescription, audienceCategory);

        // Create a mock data access object
        DetailedtaDataAccessInterface dataAccessObject = new DetailedtaDataAccessInterface() {
            @Override
            public String utilizeApi(String systemMessage, String userMessage) throws Exception {
                // Return a mock response for empty input
                return "{" +
                        "\"detailedTargetAudiences\": [" +
                        "{" +
                        "\"Name\": \"General Health Enthusiasts\"}" +
                        "]}";
            }
        };

        // Create a mock output boundary
        DetailedOutputBoundary outputBoundary = new DetailedOutputBoundary() {
            @Override
            public void prepareSuccessView(DetailedOutputData outputData) {
                // Assertions to verify the output data
                assertEquals("General Health Enthusiasts", outputData.getDetailedTargetAudience().get(0).getName());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Output boundary should not be called for empty category input.");
            }
        };

        // Create the interactor
        DetailedInteractor interactor = new DetailedInteractor(dataAccessObject, outputBoundary);

        // Execute the use case
        interactor.execute(inputData);
    }
}
