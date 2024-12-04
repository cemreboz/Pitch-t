package interface_adapter.vision;

import entity.Persona;
import entity.Pitch;
import use_case.generate_visuals.GenerateVisualInputBoundary;
import use_case.generate_visuals.GenerateVisualInputData;

/**
 * Controller for managing vision-related features.
 */
public class VisionController {
    private final GenerateVisualInputBoundary generateVisualInteractor;

    public VisionController(GenerateVisualInputBoundary generateVisualInteractor) {
        this.generateVisualInteractor = generateVisualInteractor;
    }

    /**
     * Generate image method.
     * @param inputData generateVisualInputData
     */
    public void generateImage(GenerateVisualInputData inputData) {
        generateVisualInteractor.execute(inputData);
    }

    /**
     * Regenerate image method.
     * @param inputData generateVisualInputData
     */
    public void regenerateImage(GenerateVisualInputData inputData) {
        // Simply call generateImage with the same inputs
        generateImage(inputData);
    }

}