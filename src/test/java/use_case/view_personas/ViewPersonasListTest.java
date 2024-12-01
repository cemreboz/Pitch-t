package use_case.view_personas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import entity.Persona;
import entity.Pitch;
import interface_adapter.compare_personas.ComparePersonasController;
import interface_adapter.compare_personas.ComparePersonasViewModel;
import interface_adapter.view_personas.ViewPersonasState;
import interface_adapter.view_personas.ViewPersonasViewModel;
import org.junit.jupiter.api.Test;
import view.PersonaListView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewPersonasListTest {

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

                // Display the UI for verification
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Persona List Test");
                    ViewPersonasViewModel viewModel = new ViewPersonasViewModel();
                    viewModel.setState(new ViewPersonasState(outputData.getPersonas()));
                    PersonaListView personaListView = new PersonaListView(outputData.getPersonas(), new ComparePersonasController(null), new ComparePersonasViewModel());

                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(personaListView);
                    frame.pack();
                    frame.setVisible(true);
                });

                // Add a sleep to keep the JFrame open for manual verification
                try {
                    Thread.sleep(100000); // Keep the UI open for 10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
}