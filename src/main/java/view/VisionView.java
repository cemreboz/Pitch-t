package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import entity.Persona;
import entity.Pitch;
import interface_adapter.vision.VisionController;
import interface_adapter.vision.VisionViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * VisionView is the user interface for generating and viewing AI-generated visuals
 * for a pitch and interacting with a persona.
 */
public class VisionView extends JFrame implements PropertyChangeListener {
    private final Persona persona;
    private final Pitch pitch;
    private final VisionController controller;
    private final VisionViewModel viewModel;

    // UI Components
    private JLabel adLabel;
    private JTextArea chatTextArea;
    private JTextField messageField;

    public VisionView(Persona persona, Pitch pitch, VisionController controller, VisionViewModel viewModel) {
        this.persona = persona;
        this.pitch = pitch;
        this.controller = controller;
        this.viewModel = viewModel;

        initializeUserInterface();
        attachViewModelListeners();
    }

    /**
     * Initializes the VisionView UI components.
     */
    private void initializeUserInterface() {
        setTitle("Vision Feature for " + pitch.getName());
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

    /**
     * Attaches listeners to the ViewModel to update the UI on changes.
     */
    private void attachViewModelListeners() {
        viewModel.addImagePathListener(this::updateImageDisplay);
        viewModel.addErrorMessageListener(this::updateErrorMessage);
    }

    /**
     * Generates the initial visual based on the persona and pitch.
     */
    private void generateInitialVisual() {
        controller.generateImage(persona.getName(), pitch.getName());
    }

    /**
     * Regenerates the visual with the same inputs.
     */
    private void regenerateVisual() {
        controller.generateImage(persona.getName(), pitch.getName());
    }

    /**
     * Updates the UI to display the generated image.
     * @param event the new image path.
     */
    private void updateImageDisplay(PropertyChangeEvent event) {
        final String newImagePath = (String) event.getNewValue();
        if (newImagePath != null) {
            final ImageIcon imageIcon = new ImageIcon(newImagePath);
            final Image scaledImage = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            adLabel.setIcon(new ImageIcon(scaledImage));
            adLabel.setText(null);
        }
        else {
            adLabel.setIcon(null);
            adLabel.setText("No image available.");
        }
    }

    /**
     * Updates the UI to display an error message.
     * @param event The error message to display.
     */
    private void updateErrorMessage(PropertyChangeEvent event) {
        final String errorMessage = (String) event.getNewValue();
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Handle updates from the ViewModel (imagePath and errorMessage)
        if (evt.getPropertyName().equals("imagePath")) {
            updateImageDisplay(evt);
        } else if (evt.getPropertyName().equals("errorMessage")) {
            updateErrorMessage(evt);
        }
    }
}