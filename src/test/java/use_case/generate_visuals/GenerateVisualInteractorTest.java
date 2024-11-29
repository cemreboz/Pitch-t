package use_case.generate_visuals;

import app.ImageAnalyzer;
import data_access.InMemoryVisualDataAccessObject;
import data_access.VisualDataAccessObject;
import use_case.generate_visuals.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateVisualInteractorTest {

    @Test
    void successTest() {
        // Step 1: Create Input Data (GenerateVisualInputData)
        String prompt = "Generate a visual for a product pitch.";
        String personaName = "Tech Enthusiast";
        String pitchName = "Amazing Product";
        GenerateVisualInputData inputData = new GenerateVisualInputData(prompt, personaName, pitchName);

        // Step 2: Create a Mock for the Presenter
        GenerateVisualOutputBoundary successPresenter = new GenerateVisualOutputBoundary() {
            @Override
            public void prepareSuccessView(GenerateVisualOutputData outputData) {
                // Verify that the output data is correct
                assertEquals("Visual generated successfully!", outputData.getMessage());
                assertTrue(outputData.getImagePath().contains("generated_visual"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        // Step 3: Create an Interactor and inject the Presenter
        VisualDataAccessObject visualDataAccessObject = new InMemoryVisualDataAccessObject(); // You can use an in-memory DAO
        ImageAnalyzer imageAnalyzer = new ImageAnalyzer();
        GenerateVisualInteractor interactor = new GenerateVisualInteractor(visualDataAccessObject, imageAnalyzer);

        // Step 4: Execute the Interactor
        interactor.execute(inputData);
    }

    @Test
    void failureTest() {
        // Simulate a failure scenario

        // Step 1: Create Input Data (GenerateVisualInputData)
        String prompt = "Generate a visual for a product pitch.";
        String personaName = "Non-Existing Persona";
        String pitchName = "Amazing Product";
        GenerateVisualInputData inputData = new GenerateVisualInputData(prompt, personaName, pitchName);

        // Step 2: Create a Mock for the Presenter
        GenerateVisualOutputBoundary failurePresenter = new GenerateVisualOutputBoundary() {
            @Override
            public void prepareSuccessView(GenerateVisualOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                // Validate the error message
                assertEquals("Error generating visual.", error);
            }
        };

        // Step 3: Create an Interactor and inject the Presenter
        VisualDataAccessObject visualDataAccessObject = new InMemoryVisualDataAccessObject(); // Mocked DAO
        ImageAnalyzer imageAnalyzer = new ImageAnalyzer(); // Mocked ImageAnalyzer
        GenerateVisualInteractor interactor = new GenerateVisualInteractor(visualDataAccessObject, imageAnalyzer);

        // Step 4: Execute the Interactor (this should call the failure presenter)
        interactor.execute(inputData);
    }

}