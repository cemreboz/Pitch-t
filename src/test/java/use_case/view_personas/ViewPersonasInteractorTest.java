package use_case.view_personas;

import entity.Persona;
import entity.Pitch;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewPersonasInteractorTest {

    @Test
    void generatePersonasSuccessTest() {
        // Create input data
        Pitch pitch = new Pitch("Pitch1", "Test Pitch", "test_image.png", "This is a test pitch", Collections.singletonList("Audience 1"));
        ViewPersonasInputData inputData = new ViewPersonasInputData(pitch);

        // Create a mock GPT data access object
        ViewPersonasGptDataAccessInterface gptAccessInterface = new ViewPersonasGptDataAccessInterface() {
            @Override
            public List<Persona> generatePersonas(String pitchName, String pitchDescription, List<String> targetAudience) throws Exception {
                // Return a mock list of personas
                List<Persona> personas = new ArrayList<>();
                Persona persona1 = new Persona();
                persona1.setName("Persona 1");
                Persona persona2 = new Persona();
                persona2.setName("Persona 2");
                personas.add(persona1);
                personas.add(persona2);
                return personas;
            }
        };

        // Create a mock output boundary
        ViewPersonasOutputBoundary outputBoundary = new ViewPersonasOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewPersonasOutputData outputData) {
                // Assertions to verify the output data
                assertNotNull(outputData);
                assertEquals(2, outputData.getPersonas().size());
                assertEquals("Persona 1", outputData.getPersonas().get(0).getName());
                assertEquals("Persona 2", outputData.getPersonas().get(1).getName());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure view should not be called in a success scenario.");
            }
        };

        // Create the interactor
        ViewPersonasInteractor interactor = new ViewPersonasInteractor(gptAccessInterface, outputBoundary);

        // Execute the use case
        interactor.execute(inputData);
    }

    @Test
    void generatePersonasNoResultTest() {
        // Create input data
        Pitch pitch = new Pitch("Pitch1", "Test Pitch", "test_image.png", "This is a test pitch", Collections.singletonList("Audience 1"));
        ViewPersonasInputData inputData = new ViewPersonasInputData(pitch);

        // Create a mock GPT data access object that returns an empty list
        ViewPersonasGptDataAccessInterface gptAccessInterface = new ViewPersonasGptDataAccessInterface() {
            @Override
            public List<Persona> generatePersonas(String pitchName, String pitchDescription, List<String> targetAudience) throws Exception {
                return new ArrayList<>(); // No personas generated
            }
        };

        // Create a mock output boundary
        ViewPersonasOutputBoundary outputBoundary = new ViewPersonasOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewPersonasOutputData outputData) {
                fail("Success view should not be called when no personas are generated.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                // Assertions to verify the error message
                assertEquals("No personas generated.", errorMessage);
            }
        };

        // Create the interactor
        ViewPersonasInteractor interactor = new ViewPersonasInteractor(gptAccessInterface, outputBoundary);

        // Execute the use case
        interactor.execute(inputData);
    }

    @Test
    void generatePersonasApiExceptionTest() {
        // Create input data
        Pitch pitch = new Pitch("Pitch1", "Test Pitch", "test_image.png", "This is a test pitch", Collections.singletonList("Audience 1"));
        ViewPersonasInputData inputData = new ViewPersonasInputData(pitch);

        // Create a mock GPT data access object that throws an exception
        ViewPersonasGptDataAccessInterface gptAccessInterface = new ViewPersonasGptDataAccessInterface() {
            @Override
            public List<Persona> generatePersonas(String pitchName, String pitchDescription, List<String> targetAudience) throws Exception {
                throw new Exception("API error");
            }
        };

        // Create a mock output boundary
        ViewPersonasOutputBoundary outputBoundary = new ViewPersonasOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewPersonasOutputData outputData) {
                fail("Success view should not be called when an exception occurs.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                // Assertions to verify the error message
                assertEquals("An error occurred while generating personas.", errorMessage);
            }
        };

        // Create the interactor
        ViewPersonasInteractor interactor = new ViewPersonasInteractor(gptAccessInterface, outputBoundary);

        // Execute the use case
        interactor.execute(inputData);
    }
}