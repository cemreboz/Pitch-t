package use_case.view_personas;

import entity.Persona;
import entity.Pitch;

import java.io.IOException;
import java.util.List;

/**
 * Interactor for the View Personas use case.
 */
public class ViewPersonasInteractor implements ViewPersonasInputBoundary {

    private final ViewPersonasOutputBoundary outputBoundary;
    private final ViewPersonasGptDataAccessInterface gptAccessInterface;

    public ViewPersonasInteractor(ViewPersonasGptDataAccessInterface gptAccessInterface,
                                  ViewPersonasOutputBoundary outputBoundary) {
        this.gptAccessInterface = gptAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ViewPersonasInputData inputData) {
        try {
            Pitch pitch = inputData.getPitch();
            String targetAudience = pitch.getTargetAudience();
            String projectDescription = pitch.getDescription();

            // Generate personas using ChatGPT
            List<Persona> personas = gptAccessInterface.generatePersonas(targetAudience, projectDescription, pitch);

            if (personas == null || personas.isEmpty()) {
                outputBoundary.prepareFailView("No personas generated.");
            } else {
                ViewPersonasOutputData outputData = new ViewPersonasOutputData(personas);
                outputBoundary.prepareSuccessView(outputData);
            }
        } catch (IOException | InterruptedException e) {
            outputBoundary.prepareFailView("An error occurred while generating personas.");
            e.printStackTrace();
        }
    }
}