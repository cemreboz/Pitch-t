package use_case.generate_visuals;

import entity.Persona;
import entity.Pitch;
import entity.Visual;
import interface_adapter.vision.VisionPresenter;
import view.VisionView;
import interface_adapter.vision.VisionController;
import interface_adapter.vision.VisionViewModel;
import data_access.VisualDataAccessObject;
import data_access.FileVisualDataAccessObject;

import javax.swing.*;
import java.util.List;


public class VisionTest {
    public static void main(String[] args) {
        // Create a mock Persona
        Persona mockPersona = new Persona();
        mockPersona.setName("Tech Enthusiast");
        mockPersona.setAge(28);
        mockPersona.setOccupation("Software Engineer");

        String targetAudienceList = String.join(", ", "User-Friendly", "AI-Driven", "Scalable");

        // Create a mock Pitch
        Pitch mockPitch = new Pitch(
                "pitch123",
                "Smart AI Assistant",
                "image_url",
                "An AI-powered assistant designed to simplify daily tasks.",
                targetAudienceList
        );

        // Create ViewModel, Presenter, and Interactor
        VisionViewModel visionViewModel = new VisionViewModel();
        VisionPresenter visionPresenter = new VisionPresenter(visionViewModel);

        GenerateVisualInteractor generateVisualInteractor = new GenerateVisualInteractor(
                new VisualDataAccessObject() {
                    public void saveVisual(Visual visual) {
                        // Mock saving visual
                        System.out.println("Visual saved: " + visual.getImagePath());
                    }
                },
                new FileVisualDataAccessObject(),
                visionPresenter
        );

        VisionController visionController = new VisionController(generateVisualInteractor);

        // Create and initialize the VisionView
        VisionView visionView = new VisionView(visionViewModel);
        visionView.setPersonaAndPitch(mockPersona, mockPitch);
        visionView.setVisionController(visionController);

        // Display the VisionView
        JFrame frame = new JFrame("Vision Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(visionView);
        frame.pack();
        frame.setVisible(true);
    }
}