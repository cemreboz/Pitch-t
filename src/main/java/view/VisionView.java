package view;

import entity.ChatMessage;
import entity.Persona;
import entity.Pitch;
import entity.Visual;
import interface_adapter.ViewManagerModel;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.chat_persona.ChatPersonaController;
import interface_adapter.chat_vision.ChatVisionController;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.ShowNewPitchController;
import interface_adapter.view_personas.ViewPersonasState;
import interface_adapter.vision.VisionController;
import interface_adapter.vision.VisionState;
import interface_adapter.vision.VisionViewModel;
import use_case.generate_visuals.GenerateVisualInputData;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

/**
 * The View for the Vision Page.
 */
public class VisionView extends JPanel implements PropertyChangeListener {
    private final String viewName = "vision";

    private final VisionViewModel visionViewModel;
    private Persona persona;
    private Pitch pitch;
    private Visual visual;
    private VisionController controller;
    private final ViewManagerModel viewManagerModel;
    private ChatVisionController chatVisionController;

    private JLabel adLabel;
    private HamburgerMenu hamburgerMenu;
    private JTextField messageInput;
    private JTextArea chatArea;

    public VisionView(VisionViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.visionViewModel = viewModel;
        this.visionViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        setLayout(new BorderLayout());

        // Build the header
        buildHeader();

        // Build the main content area
        buildMainContent();
    }

    private void buildHeader() {
        setSize(800, 600);

        final JPanel headerPanel = new JPanel(new BorderLayout());
        final JLabel headerLabel = new JLabel("Vision for Chosen Persona", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Left side: Hamburger menu and logo
        hamburgerMenu = createHamburgerMenu();
        final JLabel logo = new JLabel("Pitch!t");
        logo.setFont(new Font("Arial", Font.BOLD, 24));

        final JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.add(hamburgerMenu);
        logoPanel.add(logo);

        headerPanel.add(logoPanel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void buildMainContent() {
        // Main content panel with two columns (visionPanel and chatPanel)
        final JPanel mainContent = new JPanel(new GridLayout(1, 2, 10, 10));

        // Vision Panel
        final JPanel visionPanel = buildVisionPanel();
        mainContent.add(visionPanel);

        // Chat Panel
        final JPanel chatPanel = buildChatPanel();
        mainContent.add(chatPanel);

        // Add main content to the center of the layout
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel buildVisionPanel() {
        final JPanel visionPanel = new JPanel(new BorderLayout());
        visionPanel.setBorder(BorderFactory.createTitledBorder("Generated Visual"));

        adLabel = new JLabel("Visual is generating...", SwingConstants.CENTER);
        adLabel.setFont(new Font("Arial", Font.ITALIC, 16));

        final JButton regenerateButton = new JButton("Regenerate Visual");
        regenerateButton.addActionListener(e -> regenerateVisual());
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(regenerateButton);
        visionPanel.add(buttonPanel, BorderLayout.SOUTH);

        visionPanel.add(adLabel, BorderLayout.CENTER);

        return visionPanel;
    }

    private JPanel buildChatPanel() {
        final JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBorder(BorderFactory.createTitledBorder("Chat with Persona"));

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));

        final JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        // Message input area
        buildMessageInputArea(chatPanel);

        return chatPanel;
    }

    private void buildMessageInputArea(JPanel chatPanel) {
        // Footer Panel: Chat Bar
        final JPanel footerPanel = new JPanel(new BorderLayout());
        messageInput = new JTextField();
        messageInput.setFont(new Font("Arial", Font.PLAIN, 14));

        // Allow sending message with Enter key
        messageInput.addActionListener(event -> sendMessage());

        final JButton sendButton = new JButton("Send");
        sendButton.addActionListener(event -> sendMessage());

        footerPanel.add(messageInput, BorderLayout.CENTER);
        footerPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        final String userMessage = messageInput.getText().trim();
        if (!userMessage.isEmpty()) {
            if (persona != null && pitch != null && chatVisionController != null) {
                // Call the chat controller to send the message
                chatVisionController.startChat(userMessage, persona, pitch, visual);
                updateChatArea();
            }
            messageInput.setText("");
        }
        messageInput.requestFocusInWindow();
    }

    private void updateChatArea() {
        final StringBuilder chatContent = new StringBuilder();
        for (ChatMessage message : visionViewModel.getState().getChatHistory()) {
            final String sender;
            if ("user".equals(message.getRole())) {
                sender = "You";
            } else {
                sender = "System";
            }
            final String time = message.getTimestamp().format(DateTimeFormatter.ofPattern("hh:mm a"));
            chatContent.append(sender)
                    .append(" [").append(time).append("]: ")
                    .append(message.getContent())
                    .append("\n");
        }
        chatArea.setText(chatContent.toString());
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
        final GenerateVisualInputData inputData = new GenerateVisualInputData(prompt, persona, pitch);
        controller.generateImage(inputData);
    }

    private void regenerateVisual() {
        if (controller == null || persona == null || pitch == null) {
            System.err.println("Cannot regenerate visual: controller, persona, or pitch is null");
            return;
        }

        final String prompt = "Regenerate a visual tailored for persona: " + persona.getName() + " for the pitch "
                + pitch.getName();
        final GenerateVisualInputData inputData = new GenerateVisualInputData(prompt, persona, pitch);
        controller.generateImage(inputData);
    }

    public void setPersonaAndPitch(Persona persona, Pitch pitch) {
        this.persona = persona;
        this.pitch = pitch;

        if (controller != null) {
            generateInitialVisual();
        }
    }

    public void setVisual(Visual visual) {
        this.visual = visual;
    }

    public void setVisionController(VisionController controller) {
        this.controller = controller;

        if (persona != null && pitch != null) {
            generateInitialVisual();
        }
    }

    private HamburgerMenu createHamburgerMenu() {
        hamburgerMenu = new HamburgerMenu(visionViewModel);
        hamburgerMenu.setBackground(Color.WHITE);
        return hamburgerMenu;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            final VisionState state = (VisionState) evt.getNewValue();
            final String newImagePath = state.getGeneratedImageUrl();
            System.out.println("Received Image Path: " + newImagePath);

            updateImageDisplay(newImagePath);
        }
        else if ("errorMessage".equals(evt.getPropertyName())) {
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

    /**
     * Method to set hamburger menu login controller.
     * @param loginController login controller
     */
    public void setLoginController(LoginController loginController) {
        hamburgerMenu.setLoginController(loginController);
    }

    /**
     * Method to set hamburger menu account settings controller.
     * @param accountSettingsController account settings.
     */
    public void setAccountSettingsController(AccountSettingsController accountSettingsController) {
        hamburgerMenu.setAccountSettingsController(accountSettingsController);
    }

    /**
     * Method to set hamburger menu expert controller.
     * @param expertController expert controller
     */
    public void setExpertController(ExpertController expertController) {
        hamburgerMenu.setExpertController(expertController);
    }

    /**
     * Method to set hamburger menu new pitch controller.
     * @param newPitchController new pitch controller
     */
    public void setNewPitchController(ShowNewPitchController newPitchController) {
        hamburgerMenu.setNewPitchController(newPitchController);
    }

    public void setChatVisionController(ChatVisionController chatVisionController) {
        this.chatVisionController = chatVisionController;
    }
}
