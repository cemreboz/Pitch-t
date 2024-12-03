package use_case.generate_visuals;

import java.util.logging.Level;
import java.util.logging.Logger;

import data_access.VisualDataAccessObject;
import entity.Visual;

/**
 * The Generate Visual Interactor for handling the visual generation use case.
 */
public class GenerateVisualInteractor implements GenerateVisualInputBoundary {

    private static final Logger LOGGER = Logger.getLogger(GenerateVisualInteractor.class.getName());
    private final VisualDataAccessObject visualDataAccessObject;
    private final VisionDBDataAccessObject userDBAccessObject;
    private final ImageGeneratorInterface imageGenerator;
    private final GenerateVisualOutputBoundary presenter;

    public GenerateVisualInteractor(VisualDataAccessObject visualDataAccessObject,
                                    VisionDBDataAccessObject visionDBDataAccessObject,
                                    ImageGeneratorInterface imageGenerator,
                                    GenerateVisualOutputBoundary presenter) {
        this.visualDataAccessObject = visualDataAccessObject;
        this.imageGenerator = imageGenerator;
        this.presenter = presenter;
        this.userDBAccessObject = visionDBDataAccessObject;
    }

    /**
     * Executes the Generate Visuals Use Case.
     *
     * @param inputData the input data for this use case
     */

    public void execute(GenerateVisualInputData inputData) {

        try {
            System.out.println("Interactor execution started with prompt: " + inputData.getPrompt());
            // Generate the visual prompt
            final String fullPrompt = inputData.getPrompt() + " tailored to persona " + inputData.getPersonaName();

            // Generate and download the image
            final String imagePath = imageGenerator.generateImage(fullPrompt, "generated_visual.png");

            System.out.println("Image generated: " + imagePath);

            // Save the generated visual to the database (or file system)
            final Visual visual = new Visual(imagePath, fullPrompt);
            visualDataAccessObject.saveImage(visual);

            // Prepare the output data (successful case)
            final GenerateVisualOutputData outputData = new GenerateVisualOutputData(imagePath,
                    "Visual generated successfully!", userDBAccessObject.getCurrentUser().getName(),
                    userDBAccessObject.getCurrentUser().getPassword());

            // Call the presenter to update the view
            presenter.prepareSuccessView(outputData);

            LOGGER.info("Interactor: Generated image path = " + outputData.getImagePath());
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating visual: " + e.getMessage(), e);
            // In case of failure, prepare an error message
            final String errorMessage = "Error generating visual: " + e.getMessage();

            // Call the presenter to notify the failure
            presenter.prepareFailView(errorMessage);
        }

    }
}
