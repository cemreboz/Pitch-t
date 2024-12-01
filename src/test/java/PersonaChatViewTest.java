import entity.Persona;
import entity.Pitch;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat_persona.ChatPersonaController;
import interface_adapter.chat_persona.ChatPersonaViewModel;
import use_case.chat_persona.ChatPersonaInteractor;
import use_case.chat_persona.ChatPersonaOutputBoundary;
import use_case.chat_persona.ChatPersonaOutputData;
import data_access.ChatgptDataAccessObject;
import view.PersonaChatView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * Test class for PersonaChatView.
 */
public class PersonaChatViewTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Sample Persona
        Persona testPersona = new Persona();
        testPersona.setName("John Doe");
        testPersona.setAge(35);
        testPersona.setOccupation("Product Manager");
        testPersona.setLocation("San Francisco");
        testPersona.setAbout("An experienced product manager with a love for technology.");

        // Sample Pitch
        Pitch testPitch = new Pitch(
                "pitch1",
                "Green Energy Solutions",
                null,
                "A sustainable solution for renewable energy.",
                List.of("Environmentalists", "Tech Enthusiasts")
        );

        // Set up ViewModel and State
        ChatPersonaViewModel viewModel = new ChatPersonaViewModel();
        viewModel.getState().setPersona(testPersona);
        viewModel.getState().setPitch(testPitch);

        // Set up Data Access Object
        ChatgptDataAccessObject chatgptDao = new ChatgptDataAccessObject();

        // Set up Output Boundary
        ChatPersonaOutputBoundary outputBoundary = new ChatPersonaOutputBoundary() {
            @Override
            public void prepareSuccessView(ChatPersonaOutputData outputData) {
                viewModel.getState().setChatHistory(outputData.getChatHistory());
                viewModel.firePropertyChanged();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                System.err.println("Error: " + errorMessage);
            }
        };

        // Set up Interactor
        ChatPersonaInteractor interactor = new ChatPersonaInteractor(chatgptDao, outputBoundary);

        // Set up Controller
        ChatPersonaController controller = new ChatPersonaController(interactor);

        // Create a mock ViewManagerModel
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        // Create PersonaChatView
        PersonaChatView chatView = new PersonaChatView(controller, viewModel, testPersona, testPitch, viewManagerModel);

        // Create and display JFrame
        JFrame frame = new JFrame("Persona Chat Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chatView, BorderLayout.CENTER);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
}
