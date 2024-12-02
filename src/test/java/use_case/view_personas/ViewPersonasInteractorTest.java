package use_case.view_personas;

import entity.Persona;
import entity.Pitch;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewPersonasInteractorTest {

    class MockGptDataAccess implements ViewPersonasGptDataAccessInterface {
        @Override
        public String utilizeApi(String systemMessage) {
            // Returning a mocked JSON response
            return """
                    {
                        "personas": [
                            {
                                "name": "John Doe",
                                "age": 30,
                                "gender": "Male",
                                "occupation": "Engineer",
                                "location": "New York",
                                "about": "John is a software engineer who loves solving complex problems."
                            },
                            {
                                "name": "Jane Smith",
                                "age": 28,
                                "gender": "Female",
                                "occupation": "Graphic Designer",
                                "location": "San Francisco",
                                "about": "Jane is a creative designer with a passion for art and visual storytelling."
                            }
                        ]
                    }
                    """;
        }
    }

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
    public void testExecute_success() {
        // Arrange
        Pitch pitch = new Pitch("1", "Test Pitch", "sample.jpg", "Test description", List.of("Young Adults", "Tech Enthusiasts"));
        MockGptDataAccess mockGptDataAccess = new MockGptDataAccess();
        MockOutputBoundary mockOutputBoundary = new MockOutputBoundary();
        ViewPersonasInteractor interactor = new ViewPersonasInteractor(mockGptDataAccess, mockOutputBoundary);

        // Act
        interactor.execute(new ViewPersonasInputData(pitch));

        // Assert
        assertTrue(mockOutputBoundary.successCalled);
        assertNotNull(mockOutputBoundary.successData);
        List<Persona> personas = mockOutputBoundary.successData.getPersonas();
        assertEquals(2, personas.size());
        assertEquals("John Doe", personas.get(0).getName());
        assertEquals("Jane Smith", personas.get(1).getName());
    }

    @Test
    public void testExecute_failure() {
        // Arrange
        Pitch pitch = new Pitch("1", "Test Pitch", "sample.jpg", "Test description", List.of("Young Adults", "Tech Enthusiasts"));
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
        assertTrue(mockOutputBoundary.failCalled);
        assertNotNull(mockOutputBoundary.errorMessage);
        assertTrue(mockOutputBoundary.errorMessage.contains("An error occurred"));
    }
}