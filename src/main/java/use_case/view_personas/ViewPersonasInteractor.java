package use_case.view_personas;

import entity.Persona;
import entity.Pitch;

import java.io.IOException;
import java.util.List;

/**
 * Interactor for the View Personas use case.
 */
public class ViewPersonasInteractor implements ViewPersonasInputBoundary {

    private final ViewPersonasOutputBoundary viewPersonasOutputBoundary;
    private final ViewPersonasGptDataAccessInterface gptAccessInterface;

    public ViewPersonasInteractor(ViewPersonasGptDataAccessInterface gptAccessInterface,
                                  ViewPersonasOutputBoundary outputBoundary) {
        this.gptAccessInterface = gptAccessInterface;
        this.viewPersonasOutputBoundary = outputBoundary;
    }

    @Override
    public void execute(ViewPersonasInputData inputData) {
        Pitch pitch = inputData.getPitch();
        String pitchName = pitch.getName();
        List<String> targetAudience = pitch.getTargetAudienceList();
        String pitchDescription = pitch.getDescription();

        try {
            // Generate personas using ChatGPT
            List<Persona> personas = gptAccessInterface.generatePersonas(pitchName, pitchDescription, targetAudience);

            if (personas == null || personas.isEmpty()) {
                // If no personas are generated, pass a failure message to the output boundary
                viewPersonasOutputBoundary.prepareFailView("No personas generated for the given pitch.");
            } else {
                // If personas are successfully generated, create output data and pass it to output boundary
                ViewPersonasOutputData outputData = new ViewPersonasOutputData(personas);
                viewPersonasOutputBoundary.prepareSuccessView(outputData);
            }
        } catch (IOException e) {
            // Handle IO Exception explicitly
            String errorMessage = "An I/O error occurred while generating personas: " + e.getMessage();
            viewPersonasOutputBoundary.prepareFailView(errorMessage);
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Handle Interrupted Exception explicitly
            String errorMessage = "The request was interrupted while generating personas: " + e.getMessage();
            viewPersonasOutputBoundary.prepareFailView(errorMessage);
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Re-interrupt the thread to maintain the interrupted status
        } catch (Exception e) {
            // Catch all other potential exceptions to ensure robustness
            String errorMessage = "An unexpected error occurred while generating personas: " + e.getMessage();
            viewPersonasOutputBoundary.prepareFailView(errorMessage);
            e.printStackTrace();
        }
    }
}