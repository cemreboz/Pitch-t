package use_case.generate_visuals;

import data_access.VisualDataAccessObject;
import entity.DBUser;
import entity.Persona;
import entity.Pitch;
import entity.User;
import entity.Visual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Generate Visual Use Case.
 */
class GenerateVisualInteractorTest {

    private TestVisionDBDataAccessObject testDBAccessObject;
    private TestGenerateVisualOutputBoundary testPresenter;
    private TestImageGenerator testImageGenerator;
    private TestVisualDataAccessObject testVisualDataAccess;
    private GenerateVisualInteractor interactor;

    @BeforeEach
    void setUp() {
        testDBAccessObject = new TestVisionDBDataAccessObject();
        testPresenter = new TestGenerateVisualOutputBoundary();
        testImageGenerator = new TestImageGenerator();
        testVisualDataAccess = new TestVisualDataAccessObject();
        interactor = new GenerateVisualInteractor(testVisualDataAccess, testDBAccessObject, testImageGenerator, testPresenter);
    }

    @Test
    void successGenerateVisual() {
        // Mock user and set in test data access
        User mockUser = new DBUser("testUser", "testPassword");
        testDBAccessObject.setCurrentUser(mockUser);

        // Create mock Persona and Pitch
        Persona persona = new Persona();
        persona.setName("Tech Enthusiast");
        Pitch pitch = new Pitch("pitch123", "Smart Assistant", "image_url", "A description", null);

        // Input data for the interactor
        String prompt = "Create a professional advertisement for a smart home assistant.";
        GenerateVisualInputData inputData = new GenerateVisualInputData(prompt, persona, pitch);

        // Execute the interactor
        interactor.execute(inputData);

        // Assertions for success
        assertNotNull(testPresenter.outputData);
        assertEquals("Visual generated successfully!", testPresenter.outputData.getMessage());
        assertTrue(testPresenter.outputData.getImagePath().contains("mock_generated_visual.png"));
        assertEquals(mockUser.getName(), testPresenter.outputData.getUsername());
        assertEquals(mockUser.getPassword(), testPresenter.outputData.getPassword());
    }

    @Test
    void failGenerateVisual() {
        // Simulate an exception in the image generator
        testImageGenerator.setShouldFail(true);

        // Create mock Persona and Pitch
        Persona persona = new Persona();
        persona.setName("Tech Enthusiast");
        Pitch pitch = new Pitch("pitch123", "Smart Assistant", "image_url", "A description", null);

        // Input data for the interactor
        String prompt = "Create a professional advertisement for a smart home assistant.";
        GenerateVisualInputData inputData = new GenerateVisualInputData(prompt, persona, pitch);

        // Execute the interactor
        interactor.execute(inputData);

        // Assertions for failure
        assertNull(testPresenter.outputData);
        assertNotNull(testPresenter.errorMessage);
        assertTrue(testPresenter.errorMessage.startsWith("Error generating visual:"));
    }

    /**
     * Test stub for VisionDBDataAccessObject.
     */
    static class TestVisionDBDataAccessObject implements VisionDBDataAccessObject {
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
     * Test stub for GenerateVisualOutputBoundary.
     */
    static class TestGenerateVisualOutputBoundary implements GenerateVisualOutputBoundary {
        GenerateVisualOutputData outputData;
        String errorMessage;

        @Override
        public void prepareSuccessView(GenerateVisualOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            this.errorMessage = error;
        }
    }

    /**
     * Test stub for ImageGeneratorInterface.
     */
    static class TestImageGenerator implements ImageGeneratorInterface {
        private boolean shouldFail = false;

        public void setShouldFail(boolean shouldFail) {
            this.shouldFail = shouldFail;
        }

        @Override
        public String generateImage(String prompt, String filePath) throws Exception {
            if (shouldFail) {
                throw new RuntimeException("Mock image generation failure.");
            }
            return "mock_generated_visual.png";
        }
    }

    /**
     * Test stub for VisualDataAccessObject.
     */
    static class TestVisualDataAccessObject extends VisualDataAccessObject {
        @Override
        public void saveImage(Visual visual) {
            // Mock saving visual
            System.out.println("Mock Visual saved: " + visual.getImagePath());
        }
    }
}
