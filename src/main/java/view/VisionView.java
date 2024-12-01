package view;

import entity.Persona;
import entity.Pitch;
import interface_adapter.login.LoginController;
import interface_adapter.vision.VisionController;
import interface_adapter.vision.VisionState;
import interface_adapter.vision.VisionViewModel;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

/**
 * The View for the Vision Page.
 */
public class VisionView extends JFrame implements PropertyChangeListener {
    private final String viewName = "vision";

    private final VisionViewModel visionViewModel;
    private final Persona persona;
    private final Pitch pitch;
    private VisionController controller;

    // UI Components
    private JLabel adLabel;
    private JTextArea chatTextArea;
    private JTextField messageField;

    public VisionView(Persona persona, Pitch pitch, VisionController controller, VisionViewModel viewModel) {
        this.persona = persona;
        this.pitch = pitch;
        this.controller = controller;
        this.visionViewModel = viewModel;

        initializeUserInterface();
        attachViewModelListeners();
    }

    private void initializeUserInterface() {
        setTitle("Vision Feature for " + persona.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Header Panel
        final JPanel headerPanel = new JPanel(new BorderLayout());
        final JLabel headerLabel = new JLabel("Vision for Persona: " + persona.getName(), SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Center Panel (Split into Visuals and Chat)
        final JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Left Panel: Generated AD Display
        final JPanel adPanel = new JPanel(new BorderLayout());
        adPanel.setBorder(BorderFactory.createTitledBorder("Generated Visual"));
        adLabel = new JLabel("Visual is generating...", SwingConstants.CENTER);
        adLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        adPanel.add(adLabel, BorderLayout.CENTER);
        centerPanel.add(adPanel);

        // Right Panel: Chatbox with Persona
        final JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBorder(BorderFactory.createTitledBorder("Chat with Persona"));
        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        chatTextArea.setLineWrap(true);
        chatTextArea.setWrapStyleWord(true);

        final JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        messageField = new JTextField();
        chatPanel.add(messageField, BorderLayout.SOUTH);

        centerPanel.add(chatPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Footer Panel
        final JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton regenerateButton = new JButton("Regenerate Visual");
        regenerateButton.addActionListener(e -> regenerateVisual());
        footerPanel.add(regenerateButton);

        final JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        footerPanel.add(backButton);

        add(footerPanel, BorderLayout.SOUTH);

        // Trigger the initial visual generation
        generateInitialVisual();
    }

    private void attachViewModelListeners() {
        visionViewModel.addPropertyChangeListener(this);
    }

    private void generateInitialVisual() {
        // Set loading state and trigger the image generation process
        final VisionState state = visionViewModel.getState();
        state.setLoading(true);
        visionViewModel.updateView(state);

        final String prompt = "Create a visual tailored for persona: " + persona.getName() + "for the pitch"
                + pitch.getName();
        controller.generateImage(prompt, persona.getName());
    }

    private void regenerateVisual() {
        final String prompt = "Regenerate a visual tailored for persona: " + persona.getName()
                + "for the pitch" + pitch.getName();
        controller.generateImage(prompt, persona.getName());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("imagePath".equals(evt.getPropertyName())) {
            // Update the UI with the generated image
            updateImageDisplay((String) evt.getNewValue());
        }
        else if ("errorMessage".equals(evt.getPropertyName())) {
            // Handle errors
            updateErrorMessage((String) evt.getNewValue());
        }
    }

    private void updateImageDisplay(String imagePath) {
        if (imagePath != null) {
            final ImageIcon imageIcon = new ImageIcon(imagePath);
            final Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            adLabel.setIcon(new ImageIcon(image));
            adLabel.setText(null);
        } else {
            adLabel.setIcon(null);
            adLabel.setText("No image available.");
        }
    }

    private void updateErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public String getViewName() {
        return viewName;
    }

    public void setVisionController(VisionController visionController) {
        this.controller = visionController;
    }
}
