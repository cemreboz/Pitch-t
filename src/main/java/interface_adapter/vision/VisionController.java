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
    public void generateImage(String pitchName, String personaName, javax.swing.JLabel adLabel) {
        final String prompt = "Generate an advertisement for the pitch '" + pitchName + "' based on persona '" + personaName + "'.";
        final GenerateVisualInputData inputData = new GenerateVisualInputData(prompt, personaName, pitchName);

        generateVisualInteractor.execute(inputData);
    }

    /**
     * Regenerate image method.
     */
    public void regenerateImage(String pitchName, String personaName, javax.swing.JLabel adLabel) {
        // Simply call generateImage with the same inputs
        generateImage(pitchName, personaName, adLabel);
    }

}