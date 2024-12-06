package use_case.compare_personas;

import entity.Persona;
import entity.Pitch;
import entity.User;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComparePersonasInteractorTest {

    private static Persona persona1;
    private static Persona persona2;
    private static Pitch pitch;

    private TestComparePersonasGptAccess testGptAccess;
    private TestComparePersonasPresenter testPresenter;
    private TestComparePersonasDataAccess testDataAccess;
    private ComparePersonasInteractor interactor;

    @BeforeAll
    static void setUpTestData() {
        // Common persona setup
        persona1 = new Persona();
        persona1.setName("Persona 1");
        persona1.setAge(30);
        persona1.setGender("Female");
        persona1.setOccupation("Engineer");
        persona1.setAbout("Tech-savvy individual.");

        persona2 = new Persona();
        persona2.setName("Persona 2");
        persona2.setAge(40);
        persona2.setGender("Male");
        persona2.setOccupation("Manager");
        persona2.setAbout("Focused on productivity.");

        // Common pitch setup
        pitch = new Pitch("1", "Pitch 1", "Image", "Innovative product.", Arrays.asList("Tech enthusiasts", "Managers"));
    }

    @BeforeEach
    void setUp() {
        testGptAccess = new TestComparePersonasGptAccess();
        testPresenter = new TestComparePersonasPresenter();
        testDataAccess = new TestComparePersonasDataAccess();
        interactor = new ComparePersonasInteractor(testGptAccess, testPresenter, testDataAccess);

        // Set up the current user
        testDataAccess.setCurrentUser(new User() {
            @Override
            public String getName() {
                return "testUser";
            }

            @Override
            public String getPassword() {
                return "testPassword";
            }
        });
    }

    @Test
    void testExecuteSuccessfulComparison() {
        ComparePersonasInputData inputData = new ComparePersonasInputData(persona1, persona2, pitch);

        // Execute interactor
        interactor.execute(inputData);

        // Assertions
        assertNotNull(testPresenter.outputData);
        assertEquals(persona1, testPresenter.outputData.getPersona1());
        assertEquals(persona2, testPresenter.outputData.getPersona2());
        assertEquals("testUser", testPresenter.outputData.getUsername());
        assertEquals("testPassword", testPresenter.outputData.getPassword());
        assertFalse(testPresenter.outputData.getSimilarities().isEmpty());
        assertFalse(testPresenter.outputData.getDifferences().isEmpty());
    }

    @Test
    void testExecuteFailsWithInvalidPersonaCount() {
        // Test case with one null persona
        ComparePersonasInputData inputDataNullPersona2 = new ComparePersonasInputData(persona1, null, pitch);
        interactor.execute(inputDataNullPersona2);

        assertNull(testPresenter.outputData, "Output data should be null for invalid input.");
        assertEquals("Exactly two personas must be selected for comparison.", testPresenter.failMessage);

        // Test case with both personas null
        ComparePersonasInputData inputDataBothNull = new ComparePersonasInputData(null, null, pitch);
        interactor.execute(inputDataBothNull);

        assertNull(testPresenter.outputData, "Output data should be null for invalid input.");
        assertEquals("Exactly two personas must be selected for comparison.", testPresenter.failMessage);
    }

    @Test
    void testExecuteFailsOnInvalidJsonResponse() {
        ComparePersonasInputData inputData = new ComparePersonasInputData(persona1, persona2, pitch);

        testGptAccess.setJsonResponse("Invalid JSON");

        // Execute interactor
        interactor.execute(inputData);

        // Assertions
        assertNull(testPresenter.outputData);
        assertTrue(testPresenter.failMessage.startsWith("Unable to parse response"));
    }

    @Test
    void testComparePersonasOutputDataAllGetters() {
        String p1Opinion = "Likes: Great features";
        String p2Opinion = "Dislikes: High cost";

        List<String> similarities = List.of("Both appreciate innovation");
        List<String> differences = List.of("Persona 1 prefers simplicity");

        ComparePersonasOutputData outputData = new ComparePersonasOutputData(
                persona1, persona2, p1Opinion, p2Opinion, similarities, differences, "testUser", "testPassword"
        );

        // Assertions for all getters
        assertEquals(persona1, outputData.getPersona1());
        assertEquals(persona2, outputData.getPersona2());
        assertEquals(p1Opinion, outputData.getPersona1Opinion());
        assertEquals(p2Opinion, outputData.getPersona2Opinion());
        assertEquals(similarities, outputData.getSimilarities());
        assertEquals(differences, outputData.getDifferences());
        assertEquals("testUser", outputData.getUsername());
        assertEquals("testPassword", outputData.getPassword());
    }

    @Test
    void testExecuteFailsWithNullPersonas() {
        ComparePersonasInputData inputDataNullPersona1 = new ComparePersonasInputData(null, persona2, pitch);
        interactor.execute(inputDataNullPersona1);
        assertEquals("Exactly two personas must be selected for comparison.", testPresenter.failMessage);

        ComparePersonasInputData inputDataNullPersona2 = new ComparePersonasInputData(persona1, null, pitch);
        interactor.execute(inputDataNullPersona2);
        assertEquals("Exactly two personas must be selected for comparison.", testPresenter.failMessage);
    }

    @Test
    void testExecuteHandlesGeneralException() {
        ComparePersonasInputData inputData = new ComparePersonasInputData(persona1, persona2, pitch);

        testGptAccess = new TestComparePersonasGptAccess() {
            @Override
            public String utilizeApi(String message) {
                throw new RuntimeException("Simulated API failure");
            }
        };

        interactor = new ComparePersonasInteractor(testGptAccess, testPresenter, testDataAccess);

        // Execute interactor
        interactor.execute(inputData);

        // Assertions
        assertEquals("Unable to retrieve data: Simulated API failure", testPresenter.failMessage);
    }

    /**
     * Test stub for ComparePersonasGptAccessInterface.
     */
    static class TestComparePersonasGptAccess implements ComparePersonasGptAccessInterface {
        private String jsonResponse;

        public void setJsonResponse(String response) {
            this.jsonResponse = response;
        }

        @Override
        public String utilizeApi(String message) {
            if (jsonResponse == null) {
                JSONObject response = new JSONObject();
                response.put("persona1_opinion", new JSONObject().put("likes", List.of("Innovative design")).put("dislikes", List.of("High cost")));
                response.put("persona2_opinion", new JSONObject().put("likes", List.of("Productivity features")).put("dislikes", List.of("Complex UI")));
                response.put("comparison", new JSONObject().put("similarities", List.of("Appreciate functionality")).put("differences", List.of("UI preferences")));
                return response.toString();
            }
            return jsonResponse;
        }
    }

    /**
     * Test stub for ComparePersonasDataAccessInterface.
     */
    static class TestComparePersonasDataAccess implements ComparePersonasDataAccessInterface {
        private User currentUser;

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        public void setCurrentUser(User user) {
            this.currentUser = user;
        }
    }

    /**
     * Test stub for ComparePersonasOutputBoundary.
     */
    static class TestComparePersonasPresenter implements ComparePersonasOutputBoundary {
        ComparePersonasOutputData outputData;
        String failMessage;

        @Override
        public void prepareFailView(String errorMessage) {
            this.failMessage = errorMessage;
        }

        @Override
        public void prepareSuccessView(ComparePersonasOutputData outputData) {
            this.outputData = outputData;
        }
    }
}
