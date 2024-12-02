package use_case.view_personas;

import entity.Persona;
import entity.Pitch;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Comprehensive test class for the ViewPersonasInteractor use case.
 */
public class ViewPersonasInteractorTest {

    // Mock GPT Data Access Interface
    class MockGptDataAccess implements ViewPersonasGptDataAccessInterface {
        @Override
        public String utilizeApi(String systemMessage) {
            // Return a mocked JSON response similar to what an API would return
            return """
                    {
                        "personas": [
                            {
                                "name": "Alice Smith",
                                "age": 32,
                                "gender": "Female",
                                "occupation": "Marketing Specialist",
                                "location": "Boston",
                                "about": "Alice is a marketing specialist passionate about consumer engagement."
                            },
                            {
                                "name": "Bob Johnson",
                                "age": 45,
                                "gender": "Male",
                                "occupation": "Project Manager",
                                "location": "Chicago",
                                "about": "Bob is a project manager with years of experience in construction."
                            }
                        ]
                    }
                    """;
        }
    }

    // Mock Output Boundary for View Personas
    class MockOutputBoundary implements ViewPersonasOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        ViewPersonasOutputData successData = null;
        String errorMessage = null;

        @Override
        public void prepareSuccessView(ViewPersonasOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            failCalled = true;
            errorMessage = error;
        }
    }

    @Test
    public void testExecute_withValidPitch() {
        // Arrange
        Pitch pitch = new Pitch("p1", "Eco-Friendly Products", "eco.jpg", "A product line focused on sustainability.", List.of("Environment Enthusiasts", "Millennials"));
        MockGptDataAccess mockGptDataAccess = new MockGptDataAccess();
        MockOutputBoundary mockOutputBoundary = new MockOutputBoundary();
        ViewPersonasInteractor interactor = new ViewPersonasInteractor(mockGptDataAccess, mockOutputBoundary);

        // Act
        interactor.execute(new ViewPersonasInputData(pitch));

        // Assert
        assertTrue(mockOutputBoundary.successCalled, "Success callback should have been called.");
        assertNotNull(mockOutputBoundary.successData, "Output data should not be null.");
        List<Persona> personas = mockOutputBoundary.successData.getPersonas();
        assertEquals(2, personas.size(), "Two personas should have been generated.");
        assertEquals("Alice Smith", personas.get(0).getName(), "First persona's name should be Alice Smith.");
        assertEquals("Bob Johnson", personas.get(1).getName(), "Second persona's name should be Bob Johnson.");
    }

    @Test
    public void testExecute_withApiFailure() {
        // Arrange
        Pitch pitch = new Pitch("p1", "Eco-Friendly Products", "eco.jpg", "A product line focused on sustainability.", List.of("Environment Enthusiasts", "Millennials"));
        ViewPersonasGptDataAccessInterface failingGptDataAccess = new ViewPersonasGptDataAccessInterface() {
            @Override
            public String utilizeApi(String systemMessage) {
                throw new RuntimeException("Mock API Failure");
            }
        };
        MockOutputBoundary mockOutputBoundary = new MockOutputBoundary();
        ViewPersonasInteractor interactor = new ViewPersonasInteractor(failingGptDataAccess, mockOutputBoundary);

        // Act
        interactor.execute(new ViewPersonasInputData(pitch));

        // Assert
        assertTrue(mockOutputBoundary.failCalled, "Failure callback should have been called.");
        assertNotNull(mockOutputBoundary.errorMessage, "Error message should not be null.");
        assertTrue(mockOutputBoundary.errorMessage.contains("An error occurred"), "Error message should indicate an error occurred.");
    }

    @Test
    public void testExecute_withEmptyResponse() {
        // Arrange
        Pitch pitch = new Pitch("p1", "Eco-Friendly Products", "eco.jpg", "A product line focused on sustainability.", List.of("Environment Enthusiasts", "Millennials"));
        ViewPersonasGptDataAccessInterface emptyResponseGptDataAccess = new ViewPersonasGptDataAccessInterface() {
            @Override
            public String utilizeApi(String systemMessage) {
                return "{\"personas\": []}"; // Returning an empty list of personas
            }
        };
        MockOutputBoundary mockOutputBoundary = new MockOutputBoundary();
        ViewPersonasInteractor interactor = new ViewPersonasInteractor(emptyResponseGptDataAccess, mockOutputBoundary);

        // Act
        interactor.execute(new ViewPersonasInputData(pitch));

        // Assert
        assertTrue(mockOutputBoundary.failCalled, "Failure callback should have been called.");
        assertNotNull(mockOutputBoundary.errorMessage, "Error message should not be null.");
        assertEquals("No personas generated.", mockOutputBoundary.errorMessage, "Error message should indicate that no personas were generated.");
    }
}
