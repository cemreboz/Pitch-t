package use_case.generate_visuals;

import entity.Persona;
import entity.Pitch;
import entity.Visual;
import view.VisionView;
import interface_adapter.vision.VisionController;
import interface_adapter.vision.VisionViewModel;
import use_case.generate_visuals.GenerateVisualInteractor;
import data_access.VisualDataAccessObject;
import app.ImageAnalyzer;

import java.util.List;

public class VisionTest {
    public static void main(String[] args) {
        // Create a mock Persona
        Persona mockPersona = new Persona();
        mockPersona.setName("Tech Enthusiast");
        mockPersona.setAge(28);
        mockPersona.setOccupation("Software Engineer");

        // Create a mock Pitch
        Pitch mockPitch = new Pitch(
                "Smart AI Assistant",
                "An AI-powered assistant designed to simplify daily tasks.",
                "Technology",
                "Global Market",
                List.of("User-Friendly", "AI-Driven", "Scalable")
        );

        // Set up VisionController and VisionViewModel
        VisionController visionController = new VisionController(
                new GenerateVisualInteractor(new VisualDataAccessObject() {
                    @Override
                    public void saveVisual(Visual visual) {

                    }
                }, new ImageAnalyzer())
        );
        VisionViewModel visionViewModel = new VisionViewModel();

        // Print persona details to ensure getters are working
        System.out.println("Testing Persona:");
        System.out.println("Name: " + mockPersona.getName());
        System.out.println("Age: " + mockPersona.getAge());
        System.out.println("Occupation: " + mockPersona.getOccupation());

        // Instantiate and display the VisionView
        VisionView visionView = new VisionView(mockPersona, mockPitch, visionController, visionViewModel);
        visionView.setVisible(true);
    }
}
