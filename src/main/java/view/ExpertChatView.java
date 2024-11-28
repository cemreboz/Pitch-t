package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat_expert.ChatExpertController;
import interface_adapter.expert.ExpertState;
import interface_adapter.expert.ExpertViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Unified view for selecting an expert and chatting.
 * Combines the expert list, chat area, and message input.
 */
public class ExpertChatView extends JPanel implements PropertyChangeListener {

    private final String viewName = "expert chat";
    private final ExpertViewModel expertViewModel;
    private HamburgerMenu hamburgerMenu;
    private final ViewManagerModel viewManagerModel;
    private ChatExpertController chatExpertController;

    private final JLabel headerNameLabel;
    private final JLabel headerAvatarLabel;
    private final JTextArea chatArea;
    private final JTextField messageInput;

    private JButton selectedButton;

    /**
     * Constructs an ExpertChatView object.
     * @param expertViewModel expert view model
     * @param viewManagerModel view maanger model
     */
    public ExpertChatView(ExpertViewModel expertViewModel, ViewManagerModel viewManagerModel) {
        this.expertViewModel = expertViewModel;
        this.expertViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        setLayout(new BorderLayout());

        // Header Panel with Hamburger Menu and Logo
        final JPanel headerPanel = new JPanel(new BorderLayout());
        hamburgerMenu = createHamburgerMenu();
        final JLabel logo = new JLabel("Pitch!t", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 24));
        headerAvatarLabel = new JLabel(); // Placeholder for expert avatar
        headerNameLabel = new JLabel("Select an Expert", SwingConstants.LEFT);
        headerNameLabel.setFont(new Font("Arial", Font.BOLD, 18));

        final JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.add(hamburgerMenu);
        logoPanel.add(logo);

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(headerAvatarLabel, BorderLayout.CENTER);
        headerPanel.add(headerNameLabel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Left Panel: Expert List
        final JPanel expertListPanel = new JPanel();
        expertListPanel.setLayout(new BoxLayout(expertListPanel, BoxLayout.Y_AXIS));
        for (String[] expert : expertViewModel.getState().getExperts()) {
            final String expertId = expert[0];
            final String name = expert[1];
            final String description = expert[2];

            final JPanel expertPanel = new JPanel(new BorderLayout());
            final JLabel nameLabel = new JLabel(name);
            final JButton infoButton = new JButton("Info");
            infoButton.addActionListener(event -> JOptionPane.showMessageDialog(
                    this, description, name + " Info", JOptionPane.INFORMATION_MESSAGE));

            final JButton selectButton = new JButton("Select");
            selectButton.addActionListener(event -> {
                headerNameLabel.setText(name);
                headerAvatarLabel.setIcon(loadAvatar(expert[3]));
                chatExpertController.startChat(expertId, "Hi! I'd like to discuss my idea.");
                updateChatArea();
            });

            expertPanel.add(nameLabel, BorderLayout.WEST);
            expertPanel.add(infoButton, BorderLayout.CENTER);
            expertPanel.add(selectButton, BorderLayout.EAST);
            expertListPanel.add(expertPanel);
        }

        final JScrollPane expertScrollPane = new JScrollPane(expertListPanel);
        add(expertScrollPane, BorderLayout.WEST);

        // Main Chat Area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Footer Panel: Chat Bar
        final JPanel footerPanel = new JPanel(new BorderLayout());
        messageInput = new JTextField();
        final JButton sendButton = new JButton("Send");

        sendButton.addActionListener(e -> {
            final String userMessage = messageInput.getText().trim();
            if (!userMessage.isEmpty()) {
                chatExpertController.startChat(headerNameLabel.getText(), userMessage);
                updateChatArea();
                messageInput.setText("");
            }
        });

        footerPanel.add(messageInput, BorderLayout.CENTER);
        footerPanel.add(sendButton, BorderLayout.EAST);
        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Loads an avatar image from the resources folder.
     *
     * @param avatarFileName The filename of the avatar image.
     * @return An ImageIcon representing the avatar, or null if not found.
     */
    private ImageIcon loadAvatar(String avatarFileName) {
        final String path = "main/resources/avatar_experts/" + avatarFileName;
        final java.net.URL resource = getClass().getResource(path);

        if (resource != null) {
            return new ImageIcon(resource);
        }
        else {
            System.err.println("Avatar not found: " + path);
            return new ImageIcon("src/main/resources/images/avatars/default.png"); // Use a default placeholder
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
        for (String message : expertViewModel.getState().getChatHistory()) {
            chatContent.append(message).append("\n");
        }
        chatArea.setText(chatContent.toString());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ExpertState state = (ExpertState) evt.getNewValue();

        updateChatArea();
    }

    public void setChatExpertController(ChatExpertController chatExpertController) {
        this.chatExpertController = chatExpertController;
    }
}
