package use_case.generate_visuals;

import app.ImageAnalyzer;
import data_access.InMemoryVisualDataAccessObject;
import use_case.generate_visuals.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateVisualInteractorTest {

    @Test
    void testGenerateVisual() {
        // 1. Create the Input Data object
        GenerateVisualInputData inputData = new GenerateVisualInputData(
                "A vibrant ad for a new tech product",
                "Tech Enthusiast",
                "Smartphone Pitch"
        );

        // 2. Create a Presenter implementing the Output Boundary
        GenerateVisualOutputBoundary presenter = new GenerateVisualOutputBoundary() {
            @Override
            public void present(GenerateVisualOutputData outputData) {
                // Validate the Output Data
                assertNotNull(outputData.getImagePath());
                assertEquals("Visual generated successfully!", outputData.getMessage());
            }
        };

        // 3. Create an in-memory DAO
        InMemoryVisualDataAccessObject visualDAO = new InMemoryVisualDataAccessObject();

        // 4. Create the Interactor, injecting the Presenter and DAO
        ImageAnalyzer imageAnalyzer = new ImageAnalyzer();
        GenerateVisualInteractor interactor = new GenerateVisualInteractor(visualDAO, imageAnalyzer);

        // 5. Invoke the Interactor with Input Data
        GenerateVisualOutputData outputData = interactor.generateVisual(inputData);

        // Assert the Output Data (via the Presenter)
        presenter.present(outputData);

        // Optional: Validate saved data in DAO
        assertEquals(1, visualDAO.getAllVisuals().size());
    }
}