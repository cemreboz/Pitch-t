package view;

import entity.Persona;
import entity.Pitch;
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
public class VisionView extends JPanel implements PropertyChangeListener {
    private final String viewName = "vision";

    private final VisionViewModel visionViewModel;
    private Persona persona;
    private Pitch pitch;
    private VisionController controller;

    private JLabel adLabel;
    private JTextArea chatTextArea;
    private JTextField messageField;

    public VisionView(VisionViewModel viewModel) {
        this.visionViewModel = viewModel;

        initializeUserInterface();
        attachViewModelListeners();
    }

    private void initializeUserInterface() {
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Header Panel
        final JPanel headerPanel = new JPanel(new BorderLayout());
        final JLabel headerLabel = new JLabel("Vision for Persona", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Center Panel
        final JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        final JPanel adPanel = new JPanel(new BorderLayout());
        adPanel.setBorder(BorderFactory.createTitledBorder("Generated Visual"));
        adLabel = new JLabel("Visual is generating...", SwingConstants.CENTER);
        adLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        adPanel.add(adLabel, BorderLayout.CENTER);
        centerPanel.add(adPanel);

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
        footerPanel.add(backButton);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void attachViewModelListeners() {
        visionViewModel.addPropertyChangeListener(this);
    }

    private void generateInitialVisual() {
        if (controller == null) {
            System.err.println("Controller is not set yet");
            return;
        }

        if (persona == null || pitch == null) {
            System.err.println("Persona or Pitch is not set yet");
            return;
        }

        final VisionState state = visionViewModel.getState();
        state.setLoading(true);
        visionViewModel.updateView(state);

        final String prompt = "Create a visual tailored for persona: " + persona.getName() + " for the pitch " + pitch.getName();
        controller.generateImage(prompt, persona.getName(), pitch.getName());
    }

    private void regenerateVisual() {
        if (controller == null || persona == null || pitch == null) {
            System.err.println("Cannot regenerate visual: controller, persona, or pitch is null");
            return;
        }

        final String prompt = "Regenerate a visual tailored for persona: " + persona.getName() + " for the pitch " + pitch.getName();
        controller.regenerateImage(prompt, persona.getName(), pitch.getName());
    }

    public void setPersonaAndPitch(Persona persona, Pitch pitch) {
        this.persona = persona;
        this.pitch = pitch;

        if (controller != null) {
            generateInitialVisual();
        }
    }

    public void setVisionController(VisionController controller) {
        this.controller = controller;

        if (persona != null && pitch != null) {
            generateInitialVisual();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("imagePath".equals(evt.getPropertyName())) {
            updateImageDisplay((String) evt.getNewValue());
        } else if ("errorMessage".equals(evt.getPropertyName())) {
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

        revalidate();
        repaint();
    }

    private void updateErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public String getViewName() {
        return viewName;
    }
}
