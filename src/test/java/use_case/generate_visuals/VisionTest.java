package use_case.generate_visuals;

import entity.DBUser;
import entity.Persona;
import entity.Pitch;
import entity.Visual;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.vision.VisionPresenter;
import view.VisionView;
import interface_adapter.vision.VisionController;
import interface_adapter.vision.VisionViewModel;
import data_access.VisualDataAccessObject;
import data_access.FileVisualDataAccessObject;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;


public class VisionTest {
    public static void main(String[] args) {
        // Create a mock Persona
        Persona mockPersona = new Persona();
        mockPersona.setName("Tech Enthusiast");
        mockPersona.setAge(28);
        mockPersona.setOccupation("Software Engineer");

        List<String> targetAudienceList = Arrays.asList("Tech Enthusiasts", "Developers", "AI Researchers");

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
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        VisionPresenter visionPresenter = new VisionPresenter(visionViewModel);

        DBUser mockUser = new DBUser("testUser", "testPassword");

        VisualDataAccessObject mockVisualDataAccessObject = new VisualDataAccessObject() {
            @Override
            public void saveImage(Visual visual) {
                System.out.println("Mock Visual Saved: " + visual.getImagePath());
            }
        };

        FileVisualDataAccessObject mockFileVisualDataAccessObject = new FileVisualDataAccessObject() {
            @Override
            public String generateAndDownloadImage(String prompt, String outputFilePath) {
                System.out.println("Mock Image Generated for Prompt: " + prompt);
                return "mocked_generated_visual.png"; // Simulated file path for the generated image
            }
        };

        GenerateVisualInteractor generateVisualInteractor = new GenerateVisualInteractor(
                mockVisualDataAccessObject,
                new VisionDBDataAccessObject() {
                    @Override
                    public DBUser getCurrentUser() {
                        return mockUser; // Return the mock user
                    }
                },
                mockFileVisualDataAccessObject,
                visionPresenter
        );
        VisionController visionController = new VisionController(generateVisualInteractor);

        // Create and initialize the VisionView
        VisionView visionView = new VisionView(visionViewModel, viewManagerModel);
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