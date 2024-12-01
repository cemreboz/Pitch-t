package use_case.compare_personas;

import entity.Persona;
import interface_adapter.compare_personas.ComparePersonasState;
import interface_adapter.compare_personas.ComparePersonasViewModel;
import org.junit.jupiter.api.Test;
import view.ComparePersonasView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComparePersonasViewTest {

    @Test
    void displayComparisonSuccessTest() throws InterruptedException {
        // Set up personas
        Persona persona1 = new Persona();
        persona1.setName("Persona 1");
        persona1.setAbout("Persona 1 is a young professional who enjoys technology.");
        persona1.setAge(25);
        persona1.setEducation("Bachelor's Degree");
        persona1.setOccupation("Software Developer");

        Persona persona2 = new Persona();
        persona2.setName("Persona 2");
        persona2.setAbout("Persona 2 is a seasoned manager interested in business strategy.");
        persona2.setAge(40);
        persona2.setEducation("Master's Degree");
        persona2.setOccupation("Project Manager");

        // Set up similarities and differences
        List<String> similarities = new ArrayList<>();
        similarities.add("Both are interested in technology.");
        List<String> differences = new ArrayList<>();
        differences.add("Persona 1 is younger and works in a technical role.");
        differences.add("Persona 2 is experienced in management.");

        // Create state
        ComparePersonasState state = new ComparePersonasState();
        state.setPersona1(persona1);
        state.setPersona2(persona2);
        state.setSimilarities(similarities);
        state.setDifferences(differences);

        // Create ViewModel and set state
        ComparePersonasViewModel viewModel = new ComparePersonasViewModel();
        viewModel.setState(state);

        // Create the view
        ComparePersonasView comparePersonasView = new ComparePersonasView(viewModel);

        // Set up the frame
        JFrame frame = new JFrame("Compare Personas Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(comparePersonasView);
        frame.pack();
        frame.setVisible(true);

        // Wait to keep UI open for visual verification
        Thread.sleep(10000); // Keep the UI open for 10 seconds for visual inspection

    }
}
