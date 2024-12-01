package use_case.generate_visuals;

import entity.Persona;
import entity.Pitch;
import entity.Visual;
import interface_adapter.vision.VisionPresenter;
import view.VisionView;
import interface_adapter.vision.VisionController;
import interface_adapter.vision.VisionViewModel;
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

        VisionViewModel visionViewModel = new VisionViewModel();
        VisionPresenter visionPresenter = new VisionPresenter(visionViewModel);
        GenerateVisualInteractor generateVisualInteractor = new GenerateVisualInteractor(
                new VisualDataAccessObject() {
                    @Override
                    public void saveVisual(Visual visual) {
                        // Mock saving visual
                        System.out.println("Visual saved: " + visual.getImagePath());
                    }
                },
                new ImageAnalyzer(),
                visionPresenter // Pass the presenter here
        );

        VisionController visionController = new VisionController(generateVisualInteractor);


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
