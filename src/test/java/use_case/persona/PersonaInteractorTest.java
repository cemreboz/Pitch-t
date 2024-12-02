package use_case.persona;

import entity.Persona;
import entity.Pitch;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonaInteractorTest {

    private TestPersonaDataAccess testDataAccess;
    private TestPersonaPresenter testPresenter;
    private PersonaInteractor interactor;

    @BeforeEach
    void setUp() {
        testDataAccess = new TestPersonaDataAccess();
        testPresenter = new TestPersonaPresenter();
        interactor = new PersonaInteractor(testDataAccess, testPresenter);
    }

    @Test
    void successPersonaView() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        Persona persona = new Persona();
        persona.setName("Friendly Persona");
        persona.setAbout("Helpful and kind.");
        Pitch pitch = new Pitch("1", "Great Pitch", "src/",
                "This is a fantastic pitch.", new ArrayList<>());

        PersonaInputData inputData = new PersonaInputData(persona, pitch, username, password);

        // Act
        interactor.execute(inputData);

        // Assert
        assertNotNull(testPresenter.outputData);
        assertEquals(username, testPresenter.outputData.getUsername());
        assertEquals(password, testPresenter.outputData.getPassword());
        assertEquals(persona, testPresenter.outputData.getPersona());
        assertEquals(pitch, testPresenter.outputData.getPitch());
        assertFalse(testPresenter.outputData.isUseCaseFailed());
    }

    /**
     * Test stub for PersonaDataAccessInterface.
     */
    static class TestPersonaDataAccess implements PersonaDataAccessInterface {

        @Override
        public User getCurrentUser() {
            // Not directly used in this test, stub is provided for completeness.
            return null;
        }
    }

    /**
     * Test stub for PersonaOutputBoundary.
     */
    static class TestPersonaPresenter implements PersonaOutputBoundary {

        PersonaOutputData outputData;

        @Override
        public void prepareSuccessView(PersonaOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            fail("Failure path should not be triggered in this test.");
        }
    }
}
