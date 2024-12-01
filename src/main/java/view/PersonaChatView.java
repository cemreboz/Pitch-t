package view;

import entity.Persona;
import entity.Pitch;
import entity.ChatMessage;
import interface_adapter.ViewManagerModel;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.chat_persona.ChatPersonaController;
import interface_adapter.chat_persona.ChatPersonaViewModel;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.NewPitchController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

/**
 * View for chatting with a single defined persona.
 */
public class PersonaChatView extends JPanel implements PropertyChangeListener {
    private final ChatPersonaController controller;
    private final ChatPersonaViewModel viewModel;

    private JLabel headerNameLabel;
    private JTextArea chatArea;
    private JTextField messageInput;
    private HamburgerMenu hamburgerMenu;

    /**
     * Constructs a PersonaChatView object.
     *
     * @param controller ChatPersonaController for handling user input.
     * @param viewModel ChatPersonaViewModel for state management.
     * @param persona The persona for the chat.
     * @param pitch The pitch being discussed.
     */
    public PersonaChatView(ChatPersonaController controller,
                           ChatPersonaViewModel viewModel,
                           Persona persona,
                           Pitch pitch,
                           ViewManagerModel viewManagerModel) throws IOException, InterruptedException {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        buildHeader(persona.getName(), viewManagerModel);
        buildChatArea();
        buildMessageInputArea();

        // Trigger the first API call for the persona's opinion
        controller.sendMessage("", persona, pitch);
    }

    /**
     * Builds the header with a persona name.
     */
    private void buildHeader(String personaName, ViewManagerModel viewManagerModel) {
        final JPanel headerPanel = new JPanel(new BorderLayout());

        // Left: Hamburger menu and logo
        hamburgerMenu = new HamburgerMenu(viewManagerModel);
        final JLabel logo = new JLabel("Pitch!t");
        logo.setFont(new Font("Arial", Font.BOLD, 24));

        final JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.add(hamburgerMenu);
        logoPanel.add(logo);

        headerPanel.add(logoPanel, BorderLayout.WEST);

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
        messageInput.addActionListener(event -> {
            try {
                sendMessage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        final JButton sendButton = new JButton("Send");
        sendButton.addActionListener(event -> {
            try {
                sendMessage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        footerPanel.add(messageInput, BorderLayout.CENTER);
        footerPanel.add(sendButton, BorderLayout.EAST);
        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Sends the user's message to the persona.
     */
    private void sendMessage() throws IOException, InterruptedException {
        final String userMessage = messageInput.getText().trim();
        if (!userMessage.isEmpty()) {
            controller.sendMessage(userMessage, viewModel.getState().getPersona(), viewModel.getState().getPitch());
            messageInput.setText("");
        }
        messageInput.requestFocusInWindow();
    }

    /**
     * Updates the chat area with the current chat history.
     */
    private void updateChatArea() {
        final StringBuilder chatContent = new StringBuilder();
        final List<ChatMessage> chatHistory = viewModel.getState().getChatHistory();

        for (ChatMessage message : chatHistory) {
            // Only display user and assistant messages
            if ("user".equalsIgnoreCase(message.getRole())) {
                chatContent.append("You: ").append(message.getContent()).append("\n");
            }
            else if ("assistant".equalsIgnoreCase(message.getRole())) {
                chatContent.append(viewModel.getState().getPersona().getName()).append(": ")
                        .append(message.getContent()).append("\n");
            }
        }
        chatArea.setText(chatContent.toString());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateChatArea();
    }

    public void setLoginController(LoginController loginController) {
        hamburgerMenu.setLoginController(loginController);
    }

    public void setAccountSettingsController(AccountSettingsController accountSettingsController) {
        hamburgerMenu.setAccountSettingsController(accountSettingsController);
    }

    public void setExpertController(ExpertController expertController) {
        hamburgerMenu.setExpertController(expertController);
    }

    public void setNewPitchController(NewPitchController newPitchController) {
        hamburgerMenu.setNewPitchController(newPitchController);
    }

}
