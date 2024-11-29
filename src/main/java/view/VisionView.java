package view;

import entity.Persona;
import entity.Pitch;
import interface_adapter.vision.VisionController;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * VisionView is the user interface for generating and viewing AI-generated visuals
 * for a pitch and interacting with a persona.
 */
public class VisionView extends JFrame {

    private static Persona persona = null;
    private final Pitch currentPitch;

    // UI Components
    private JLabel adLabel;

    // Controller
    private final VisionController controller;

    public VisionView(Persona persona, Pitch pitch, boolean isFromPersonaPage, VisionController controller) {
        this.persona = persona;
        this.currentPitch = pitch;
        this.isFromPersonaPage = isFromPersonaPage;
        this.controller = controller;
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        // Frame settings
        setTitle("Pitch!t - Vision for " + currentPitch.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Build the UI components
        createHeader(mainPanel);
        createCenterPanel(mainPanel);
        createFooter(mainPanel);
    }

    private void createHeader(JPanel mainPanel) {
        final JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left: Hamburger Menu and Logo
        final JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setBackground(Color.WHITE);

        // Add logo
        final ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pitch-t-logo.png")));
        final JLabel logoLabel = new JLabel(logoIcon);
        leftPanel.add(logoLabel);

        headerPanel.add(leftPanel, BorderLayout.WEST);

        // Center: Project Name and Persona Name
        final JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(Color.WHITE);

        final JLabel projectLabel = new JLabel(currentPitch.getName(), SwingConstants.CENTER);
        projectLabel.setFont(new Font("Inter", Font.BOLD, 32));
        centerPanel.add(projectLabel);

        final JLabel personaLabel = new JLabel("Persona: " + persona.getName(), SwingConstants.CENTER);
        personaLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        centerPanel.add(personaLabel);

        headerPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createCenterPanel(JPanel mainPanel) {
        final JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left: Panel for Generated AD
        final JPanel adPanel = new JPanel(new BorderLayout());
        adPanel.setBackground(Color.WHITE);
    }

    private void createFooter(JPanel mainPanel) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose(); // Close the current window
            // Add logic to navigate back to the previous view if needed
        });
        footerPanel.add(backButton);

        JButton regenerateButton = new JButton("Regenerate");
        regenerateButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Regenerating AD...");
            controller.regenerateImage(currentPitch.getName(), persona.getName(), adLabel);
        });
        footerPanel.add(regenerateButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }
}

