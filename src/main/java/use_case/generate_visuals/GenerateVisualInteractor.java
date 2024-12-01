package use_case.generate_visuals;

import data_access.VisualDataAccessObject;
import entity.Visual;

/**
 * The Generate Visual Interactor for handling the visual generation use case.
 */
public class GenerateVisualInteractor implements GenerateVisualInputBoundary {

    private final VisualDataAccessObject visualDataAccessObject;
    private final ImageGeneratorInterface imageGenerator;
    private final GenerateVisualOutputBoundary presenter;

    public GenerateVisualInteractor(VisualDataAccessObject visualDataAccessObject,
                                    ImageGeneratorInterface imageGenerator,
                                    GenerateVisualOutputBoundary presenter) {
        this.visualDataAccessObject = visualDataAccessObject;
        this.imageGenerator = imageGenerator;
        this.presenter = presenter;
    }

    /**
     * Executes the Generate Visuals Use Case.
     *
     * @param inputData the input data for this use case
     */

    public void execute(GenerateVisualInputData inputData) {
        try {
            // Generate the visual prompt
            final String fullPrompt = inputData.getPrompt() + " tailored to persona " + inputData.getPersonaName();

            // Generate and download the image
            final String imagePath = imageGenerator.generateImage(fullPrompt, "generated_visual.png");

            // Save the generated visual to the database (or file system)
            final Visual visual = new Visual(imagePath, fullPrompt);
            visualDataAccessObject.saveImage(visual);

            // Prepare the output data (successful case)
            final GenerateVisualOutputData outputData = new GenerateVisualOutputData(imagePath, "Visual generated successfully!");

            // Call the presenter to update the view
            presenter.prepareSuccessView(outputData);

        }
        catch (Exception e) {
            // In case of failure, prepare an error message
            final String errorMessage = "Error generating visual: " + e.getMessage();

            // Call the presenter to notify the failure
            presenter.prepareFailView(errorMessage);
        }
    }
}