package interface_adapter.vision;

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
     */
    public void generateImage(String prompt, String personaName, String pitchName) {
        System.out.println("VisionController: generateImage() called with prompt: " + prompt);
        GenerateVisualInputData inputData = new GenerateVisualInputData(prompt, personaName, pitchName);
        generateVisualInteractor.execute(inputData);
    }

    /**
     * Regenerate image method.
     */
    public void regenerateImage(String prompt, String personaName, String pitchName) {
        // Simply call generateImage with the same inputs
        generateImage(prompt, personaName, pitchName);
    }

}