package view;

import entity.ChatMessage;
import interface_adapter.ViewManagerModel;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.chat_expert.ChatExpertController;
import interface_adapter.expert.ExpertController;
import interface_adapter.expert.ExpertState;
import interface_adapter.expert.ExpertViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.NewPitchController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;

/**
 * Unified view for selecting an expert and chatting.
 * Combines the expert list, chat area, and message input.
 */
public class ExpertChatView extends JPanel implements PropertyChangeListener {

    private final String viewName = "chat expert";
    private final ExpertViewModel expertViewModel;
    private HamburgerMenu hamburgerMenu;
    private final ViewManagerModel viewManagerModel;
    private ChatExpertController chatExpertController;

    private JLabel headerNameLabel;
    private JLabel headerAvatarLabel;
    private JTextArea chatArea;
    private JTextField messageInput;
    private String selectedExpertId;

    /**
     * Constructs an ExpertChatView object.
     *
     * @param expertViewModel  expert view model
     * @param viewManagerModel view manager model
     */
    public ExpertChatView(ExpertViewModel expertViewModel, ViewManagerModel viewManagerModel) {
        this.expertViewModel = expertViewModel;
        this.expertViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        setLayout(new BorderLayout());

        // Build the header
        buildHeader();

        // Build the expert list panel
        buildExpertListPanel();

        // Build the chat area
        buildChatArea();

        // Build the message input area
        buildMessageInputArea();
    }

    /**
     * Builds the header panel with logo, hamburger menu, and expert info.
     */
    private void buildHeader() {
        // Header Panel with Hamburger Menu and Logo
        final JPanel headerPanel = new JPanel(new BorderLayout());

        // Left side: Hamburger menu and logo
        hamburgerMenu = createHamburgerMenu();
        final JLabel logo = new JLabel("Pitch!t");
        logo.setFont(new Font("Arial", Font.BOLD, 24));

        final JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.add(hamburgerMenu);
        logoPanel.add(logo);

        headerPanel.add(logoPanel, BorderLayout.WEST);

        // Right side: Expert info (avatar and name)
        final JPanel expertInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerNameLabel = new JLabel("Select an Expert");
        headerNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerAvatarLabel = new JLabel();
        headerAvatarLabel.setPreferredSize(new Dimension(50, 50));

        expertInfoPanel.add(headerNameLabel);
        expertInfoPanel.add(headerAvatarLabel);

        headerPanel.add(expertInfoPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }

    /**
     * Builds the expert list panel on the left.
     */
    private void buildExpertListPanel() {
        // Left Panel: Expert List
        final JPanel expertListPanel = new JPanel();
        expertListPanel.setLayout(new BoxLayout(expertListPanel, BoxLayout.Y_AXIS));

        for (String[] expert : expertViewModel.getState().getExperts()) {
            final String expertId = expert[0];
            final String name = expert[1];
            final String description = expert[2];

            final JPanel expertPanel = new JPanel(new BorderLayout());
            expertPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            final JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

            final JButton infoButton = new JButton("Info");
            infoButton.addActionListener(event -> JOptionPane.showMessageDialog(
                    this, description, name + " Info", JOptionPane.INFORMATION_MESSAGE));

            final JButton selectButton = new JButton("Select");
            selectButton.addActionListener(event -> {
                headerNameLabel.setText(name);
                headerAvatarLabel.setIcon(loadAvatar(expert[3]));
                selectedExpertId = expertId;
                chatExpertController.startChat(expertId, "Hi! I'd like to discuss my idea.");
                updateChatArea();
            });

            // Buttons Panel
            final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonsPanel.add(infoButton);
            buttonsPanel.add(selectButton);

            expertPanel.add(nameLabel, BorderLayout.CENTER);
            expertPanel.add(buttonsPanel, BorderLayout.EAST);

            expertListPanel.add(expertPanel);
            expertListPanel.add(new JSeparator());
        }

        final JScrollPane expertScrollPane = new JScrollPane(expertListPanel);
        expertScrollPane.setPreferredSize(new Dimension(200, 0));
        add(expertScrollPane, BorderLayout.WEST);
    }

    /**
     * Builds the main chat area.
     */
    private void buildChatArea() {
        // Main Chat Area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));

        final JScrollPane chatScrollPane = new JScrollPane(chatArea);
        add(chatScrollPane, BorderLayout.CENTER);
    }

    /**
     * Builds the message input area at the bottom.
     */
    private void buildMessageInputArea() {
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
        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Sends the user's message to the expert.
     */
    private void sendMessage() {
        final String userMessage = messageInput.getText().trim();
        if (!userMessage.isEmpty()) {
            if (selectedExpertId != null) {
                chatExpertController.startChat(selectedExpertId, userMessage);
                updateChatArea();
                messageInput.setText("");
            }
            else {
                JOptionPane.showMessageDialog(this, "Please select an expert before sending a message.",
                        "No Expert Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
        messageInput.requestFocusInWindow(); // Set focus back to the input field
    }

    /**
     * Loads an avatar image from the resources folder.
     *
     * @param avatarFileName The filename of the avatar image.
     * @return An ImageIcon representing the avatar, or null if not found.
     */
    private ImageIcon loadAvatar(String avatarFileName) {
        final String path = "/" + avatarFileName;
        System.err.println("Loading avatar: " + path);
        final java.net.URL resource = getClass().getResource(path);

        if (resource != null) {
            final ImageIcon originalIcon = new ImageIcon(resource);
            // Scale the image to desired size, e.g., 50x50 pixels
            final Image image = originalIcon.getImage();
            final Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        else {
            System.err.println("Avatar not found: " + path);
            // Load the default avatar image from the classpath
            final java.net.URL defaultResource = getClass().getResource("/images/avatars/default.png");
            if (defaultResource != null) {
                final ImageIcon defaultIcon = new ImageIcon(defaultResource);
                final Image image = defaultIcon.getImage();
                final Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
            else {
                System.err.println("Default avatar not found.");
                return null;
            }
        }
    }

    /**
     * Creates a hamburger menu button.
     *
     * @return The hamburger menu button.
     */
    private HamburgerMenu createHamburgerMenu() {
        hamburgerMenu = new HamburgerMenu(expertViewModel);
        hamburgerMenu.setBackground(Color.WHITE);
        return hamburgerMenu;
    }

    /**
     * Updates the chat area with the current chat history.
     */
    private void updateChatArea() {
        final StringBuilder chatContent = new StringBuilder();
        for (ChatMessage message : expertViewModel.getState().getChatHistory()) {
            final String sender;
            if ("user".equals(message.getRole())) {
                sender = "You";
            }
            else if ("assistant".equals(message.getRole())) {
                sender = headerNameLabel.getText();
            }
            else {
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateChatArea();
    }

    public void setChatExpertController(ChatExpertController chatExpertController) {
        this.chatExpertController = chatExpertController;
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Method to set hamburger menu login controller.
     *
     * @param loginController login controller
     */
    public void setLoginController(LoginController loginController) {
        hamburgerMenu.setLoginController(loginController);
    }

    /**
     * Method to set hamburger menu account settings controller.
     *
     * @param accountSettingsController account settings.
     */
    public void setAccountSettingsController(AccountSettingsController accountSettingsController) {
        hamburgerMenu.setAccountSettingsController(accountSettingsController);
    }

    /**
     * Method to set hamburger menu expert controller.
     *
     * @param expertController expert controller
     */
    public void setExpertController(ExpertController expertController) {
        hamburgerMenu.setExpertController(expertController);
    }

    /**
     * Method to set hamburger menu new pitch controller.
     *
     * @param newPitchController new pitch controller
     */
    public void setNewPitchController(NewPitchController newPitchController) {
        hamburgerMenu.setNewPitchController(newPitchController);
    }
}
