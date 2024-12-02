package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import entity.ChatMessage;
import interface_adapter.ViewManagerModel;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.chat_persona.ChatPersonaController;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.NewPitchController;
import interface_adapter.persona.PersonaViewModel;
import interface_adapter.vision.VisionController;

/**
 * View for chatting with a single defined persona.
 * Combines the hamburger menu, chat area, and message input, with a back button.
 */
public class PersonaChatView extends JPanel implements PropertyChangeListener {

    private final String viewName = "chat persona";
    private final ViewManagerModel viewManagerModel;
    private final PersonaViewModel personaViewModel;
    private ChatPersonaController chatPersonaController;

    private JLabel headerNameLabel;
    private JTextArea chatArea;
    private JTextField messageInput;
    private HamburgerMenu hamburgerMenu;

    /**
     * Constructs a PersonaChatView object.
     *
     * @param personaViewModel persona view model
     * @param viewManagerModel View manager model.
     */
    public PersonaChatView(PersonaViewModel personaViewModel, ViewManagerModel viewManagerModel) {
        this.personaViewModel = personaViewModel;
        this.personaViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        setLayout(new BorderLayout());

        // Build the header with a back button and persona name
        buildHeader(personaViewModel.getState().getPersona().getName());

        // Build the chat area
        buildChatArea();

        // Build the message input area
        buildMessageInputArea();
    }

    /**
     * Builds the header panel with a back button, hamburger menu, and persona name.
     * @param personaName the name of th persona for the header.
     */
    private void buildHeader(String personaName) {
        final JPanel headerPanel = new JPanel(new BorderLayout());

        // Left side: Back button and hamburger menu
        final JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JButton backButton = new JButton("Back");
        backButton.addActionListener(evt -> JOptionPane.showMessageDialog(backButton, "Back button not implemented."));
        leftPanel.add(backButton);

        hamburgerMenu = new HamburgerMenu(personaViewModel);
        hamburgerMenu.setBackground(Color.WHITE);
        leftPanel.add(hamburgerMenu);

        headerPanel.add(leftPanel, BorderLayout.WEST);

        // Center: Persona name
        headerNameLabel = new JLabel(personaName, SwingConstants.CENTER);
        headerNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(headerNameLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
    }

    /**
     * Builds the main chat area.
     */
    private void buildChatArea() {
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
        final JPanel footerPanel = new JPanel(new BorderLayout());
        messageInput = new JTextField();
        messageInput.setFont(new Font("Arial", Font.PLAIN, 14));

        // Allow sending messages with Enter key
        messageInput.addActionListener(event -> sendMessage());

        final JButton sendButton = new JButton("Send");
        sendButton.addActionListener(event -> sendMessage());

        footerPanel.add(messageInput, BorderLayout.CENTER);
        footerPanel.add(sendButton, BorderLayout.EAST);
        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Sends the user's message to the persona.
     */
    private void sendMessage() {
        final String userMessage = messageInput.getText().trim();
        if (!userMessage.isEmpty()) {
            personaViewModel.getState().getChatHistory().add(new ChatMessage("user", userMessage));

            chatPersonaController.startChat(userMessage,
                    personaViewModel.getState().getPersona(),
                    personaViewModel.getState().getPitch());

            updateChatArea();

            messageInput.setText("");
        }
        messageInput.requestFocusInWindow();
    }

    /**
     * Updates the chat area with the current chat history.
     */
    private void updateChatArea() {
        final StringBuilder chatContent = new StringBuilder();
        for (ChatMessage message : personaViewModel.getState().getChatHistory()) {
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
        headerNameLabel.setText(personaViewModel.getState().getPersona().getName());

        if (personaViewModel.getState().getChatHistory().isEmpty()) {
            // Initialize the assistant if chat history is empty
            chatPersonaController.startChat("",
                    personaViewModel.getState().getPersona(),
                    personaViewModel.getState().getPitch());
        }

        updateChatArea();
    }

    public void setChatPersonaController(ChatPersonaController chatPersonaController) {
        this.chatPersonaController = chatPersonaController;
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
    public void setNewPitchController(NewPitchController newPitchController) {
        hamburgerMenu.setNewPitchController(newPitchController);
    }

    public void setVisionController(VisionController visionController) {
    }
}
