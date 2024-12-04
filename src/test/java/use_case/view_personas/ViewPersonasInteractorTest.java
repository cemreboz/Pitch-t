package use_case.view_personas;

import entity.DBUser;
import entity.Persona;
import entity.Pitch;
import entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewPersonasInteractorTest {

    private TestViewPersonasGptDataAccess testGptAccess;
    private TestViewPersonasDataAccess testDataAccess;
    private TestViewPersonasPresenter testPresenter;
    private ViewPersonasInteractor interactor;
    private Pitch testPitch;

    @BeforeEach
    void setUp() {
        // Initialize stubs and interactor
        testGptAccess = new TestViewPersonasGptDataAccess();
        testDataAccess = new TestViewPersonasDataAccess();
        testPresenter = new TestViewPersonasPresenter();
        interactor = new ViewPersonasInteractor(testGptAccess, testPresenter, testDataAccess);

        // Initialize a pitch
        testPitch = new Pitch("1", "Test Pitch", "Image", "Innovative product.",
                Arrays.asList("Tech enthusiasts", "Managers"));
    }

    @Test
    void testExecuteWithPreExistingPersonas() {
        // Set up personas already in the pitch
        Persona persona1 = new Persona();
        persona1.setName("Persona 1");
        Persona persona2 = new Persona();
        persona2.setName("Persona 2");

        testPitch.setPersonas(Arrays.asList(persona1, persona2));
        ViewPersonasInputData inputData = new ViewPersonasInputData(testPitch);

        // Execute interactor
        interactor.execute(inputData);

        // Assertions
        assertNotNull(testPresenter.outputData);
        assertEquals(2, testPresenter.outputData.getPersonas().size());
        assertEquals("Persona 1", testPresenter.outputData.getPersonas().get(0).getName());
    }

    @Test
    void testExecuteGeneratesPersonas() {
        // Set up pitch with no personas
        ViewPersonasInputData inputData = new ViewPersonasInputData(testPitch);

        // Set up mock GPT response
        testGptAccess.setJsonResponse(generateMockPersonasJson(2));

        // Execute interactor
        interactor.execute(inputData);

        // Assertions
        assertNotNull(testPresenter.outputData);
        assertEquals(2, testPresenter.outputData.getPersonas().size());
        assertEquals("Generated Persona 1", testPresenter.outputData.getPersonas().get(0).getName());
    }

    @Test
    void testExecuteFailsWhenPersonasCannotBeGenerated() {
        // Simulate GPT API failure by enabling exception in the stub
        testGptAccess.setThrowException(true);

        // Set up the input data
        ViewPersonasInputData inputData = new ViewPersonasInputData(testPitch);

        // Execute interactor
        Exception exception = assertThrows(IllegalStateException.class, () -> interactor.execute(inputData));

        // Assertions
        assertEquals("An error occurred while generating personas: Simulated GPT failure",
                exception.getMessage());
        assertEquals("An error occurred while generating personas: Simulated GPT failure",
                testPresenter.failMessage);
    }

    @Test
    void testExecuteFailsWhenPersonasListIsNullOrEmpty() {
        // Simulate GPT API returning an empty persona list
        testGptAccess.setJsonResponse("[]"); // Mock response with no personas

        // Input data with a pitch
        ViewPersonasInputData inputData = new ViewPersonasInputData(testPitch);

        // Execute interactor
        interactor.execute(inputData);

        // Assertions
        assertNull(testPresenter.outputData); // No success output should be generated
        assertEquals("No personas generated.", testPresenter.failMessage); // Verify fail message
    }

    @Test
    void testViewPersonasOutputDataGetters() {
        // Set up dummy data
        Persona persona1 = new Persona();
        persona1.setName("Persona 1");

        Persona persona2 = new Persona();
        persona2.setName("Persona 2");

        List<Persona> personas = Arrays.asList(persona1, persona2);
        Pitch pitch = new Pitch("1", "Test Pitch", "Image", "Innovative product.",
                Arrays.asList("Tech enthusiasts", "Managers"));
        String username = "testUser";
        String password = "testPassword";

        // Create an instance of ViewPersonasOutputData
        ViewPersonasOutputData outputData = new ViewPersonasOutputData(personas, pitch, username, password);

        // Assertions to ensure getters are covered
        assertEquals(personas, outputData.getPersonas());
        assertEquals(pitch, outputData.getPitch());
        assertEquals(username, outputData.getUsername());
        assertEquals(password, outputData.getPassword());
    }

    @Test
    void testExecuteFailsWhenPersonasAreNull() {
        // Setup pitch with no personas
        Pitch pitch = new Pitch("1", "Test Pitch", "Image", "Innovative product.", Arrays.asList("Tech enthusiasts"));
        pitch.setPersonas(null); // Explicitly set personas to null

        ViewPersonasInputData inputData = new ViewPersonasInputData(pitch);

        // Execute interactor
        interactor.execute(inputData);

        // Assertions
        assertNull(testPresenter.outputData); // No success output
        assertEquals("No personas generated.", testPresenter.failMessage); // Failure message
    }

    @Test
    void testExecuteFailsWhenPersonasAreEmpty() {
        // Setup pitch with empty personas
        Pitch pitch = new Pitch("1", "Test Pitch", "Image", "Innovative product.", Arrays.asList("Tech enthusiasts"));
        pitch.setPersonas(new ArrayList<>()); // Set empty list

        ViewPersonasInputData inputData = new ViewPersonasInputData(pitch);

        // Execute interactor
        interactor.execute(inputData);

        // Assertions
        assertNull(testPresenter.outputData); // No success output
        assertEquals("No personas generated.", testPresenter.failMessage); // Failure message
    }

    @Test
    void testExecuteFailsWhenGptResponseReturnsEmptyPersonas() {
        // Simulate GPT API returning an empty persona list
        testGptAccess.setJsonResponse("[]"); // Mock empty response from GPT

        // Execute interactor
        interactor.execute(new ViewPersonasInputData(testPitch));

        // Assertions
        assertNull(testPresenter.outputData); // No success output
        assertEquals("No personas generated.", testPresenter.failMessage); // Verify fail message
    }

    /**
     * Generates a mock JSON response for GPT API.
     */
    private String generateMockPersonasJson(int count) {
        JSONArray personasArray = new JSONArray();
        for (int i = 1; i <= count; i++) {
            JSONObject personaJson = new JSONObject();
            personaJson.put("name", "Generated Persona " + i);
            personaJson.put("age", 30);
            personaJson.put("gender", "Male");
            personaJson.put("education", "Bachelor's Degree");
            personaJson.put("salaryRange", "50k-70k");
            personaJson.put("about", "A tech-savvy individual.");
            personaJson.put("stats", "Sample stats");
            personaJson.put("location", "City, Country");
            personaJson.put("occupation", "Software Engineer");
            personaJson.put("interests", Arrays.asList("Coding", "Reading"));
            personasArray.put(personaJson);
        }
        return personasArray.toString();
    }


    // Stub Classes...

    static class TestViewPersonasGptDataAccess implements ViewPersonasGptDataAccessInterface {
        private boolean throwException = false;
        private String jsonResponse;

        public void setThrowException(boolean throwException) {
            this.throwException = throwException;
        }

        public void setJsonResponse(String response) {
            this.jsonResponse = response;
        }

        @Override
        public String utilizeApi(String systemMessage) throws Exception {
            if (throwException) {
                throw new Exception("Simulated GPT failure");
            }
            return jsonResponse != null ? jsonResponse : "[]";
        }
    }

    static class TestViewPersonasDataAccess implements ViewPersonasDataAccessInterface {
        private DBUser currentUser;

        @Override
        public User getCurrentUser() {
            if (currentUser == null) {
                currentUser = new DBUser("Test User", "password123");
                currentUser.addPitch(new Pitch("1", "Test Pitch", "Image", "Innovative product.", Arrays.asList("Tech enthusiasts", "Managers")));
            }
            return currentUser;
        }

        public void setCurrentUser(DBUser user) {
            this.currentUser = user;
        }
    }

    static class TestViewPersonasPresenter implements ViewPersonasOutputBoundary {
        ViewPersonasOutputData outputData;
        String failMessage;

        @Override
        public void prepareSuccessView(ViewPersonasOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failMessage = errorMessage;
        }
    }
}
