package use_case.generate_visuals;

import app.ImageAnalyzer;
import data_access.VisualDataAccessObject;
import entity.Visual;

/**
 * The interactor for the Generate Visual Use Case.
 */
public class GenerateVisualInteractor implements GenerateVisualInputBoundary {

    private final VisualDataAccessObject visualDataAccessObject;
    private final ImageAnalyzer imageAnalyzer;

    public GenerateVisualInteractor(VisualDataAccessObject visualDataAccessObject, ImageAnalyzer imageAnalyzer) {
        this.visualDataAccessObject = visualDataAccessObject;
        this.imageAnalyzer = imageAnalyzer;
    }

    /**
     * Executes the Generate Visuals Use Case.
     * @param inputData the input data for this use case
     * @return output data with generated image information
     * @throws RuntimeException if visual is not generated
     */
    public GenerateVisualOutputData execute(GenerateVisualInputData inputData) {
        try {
            final String fullPrompt = inputData.getPrompt() + " tailored to persona " + inputData.getPersonaName();
            final String imagePath = imageAnalyzer.generateAndDownloadImage(fullPrompt, "generated_visual.png");

            final Visual visual = new Visual(imagePath, fullPrompt);
            visualDataAccessObject.saveVisual(visual);

            return new GenerateVisualOutputData(imagePath, "Visual generated successfully!");
        }
        catch (Exception e) {
            throw new RuntimeException("Error generating visual: " + e.getMessage());
        }
    }
}
