package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.account_settings.AccountSettingsState;
import interface_adapter.account_settings.AccountSettingsViewModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.logout.LogoutController;
import interface_adapter.new_pitch.ShowNewPitchController;

/**
 * The View for when the user is logged into the program.
 */
public class AccountSettingsView extends JPanel implements PropertyChangeListener {
    private final String viewName = "account settings";

    // Controllers
    private final AccountSettingsViewModel accountSettingsViewModel;
    private LogoutController logoutController;
    private ChangePasswordController changePasswordController;
    // UI Elements
    private final JLabel username;
    private final JButton logOut;
    private final JTextField passwordInputField = new JTextField(15);
    private final JLabel passwordErrorField = new JLabel();
    private final JButton changePassword;
    private final HamburgerMenu hamburgerMenu;
    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));

    // Not sure why but okay
    private final int fifty = 50;
    private final int hundred = 100;
    private final int thousand = 1000;

    public AccountSettingsView(AccountSettingsViewModel accountSettingsViewModel) {
        this.accountSettingsViewModel = accountSettingsViewModel;
        this.accountSettingsViewModel.addPropertyChangeListener(this);
        this.hamburgerMenu = new HamburgerMenu(accountSettingsViewModel);
        this.hamburgerMenu.setBackground(Color.WHITE);

        // Title
        final JLabel title = new JLabel("Account Settings");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, AccountSettingsViewModel.TITLE_FONT));

        // Hamburger menu
        final JPanel menuWrapper = new JPanel();
        menuWrapper.setBackground(Color.WHITE);
        menuWrapper.setMaximumSize(new Dimension(fifty, fifty));
        menuWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuWrapper.add(hamburgerMenu, BorderLayout.CENTER);

        // Logo
        final JLabel logo = new JLabel(logoIcon);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Header
        final JPanel header = createHeaderPanel();

        // Password Panel
        final JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(Color.WHITE);

        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);
        passwordInfo.setBackground(Color.WHITE);
        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();
        passwordPanel.add(passwordInfo);
        passwordPanel.add(usernameInfo);

        // Buttons
        final JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        logOut = new JButton("Log Out");
        changePassword = new JButton("Change Password");
        buttons.add(changePassword);
        buttons.add(logOut);

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final AccountSettingsState currentState = accountSettingsViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                accountSettingsViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        changePassword.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(changePassword)) {
                        final AccountSettingsState currentState = accountSettingsViewModel.getState();

                        this.changePasswordController.execute(
                                currentState.getUsername(),
                                currentState.getPassword()
                        );
                    }
                }
        );

        logOut.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(logOut)) {
                        // 1. get the state out of the loggedInViewModel. It contains the username.
                        final AccountSettingsState currentState = accountSettingsViewModel.getState();
                        // 2. Execute the logout Controller.
                        this.logoutController.execute(currentState.getUsername());
                    }
                }
        );

        // Put together the frame
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.add(header);
        this.add(title, BorderLayout.CENTER);

        this.add(usernameInfo);

        this.add(username);
        this.add(Box.createVerticalStrut(AccountSettingsViewModel.DEFUALT_HEIGHT));
        this.add(passwordInfo);
        this.add(Box.createVerticalStrut(AccountSettingsViewModel.DEFUALT_HEIGHT));
        this.add(passwordErrorField);
        this.add(Box.createVerticalStrut(AccountSettingsViewModel.DEFUALT_HEIGHT));
        this.add(buttons);
    }

    private JPanel createHeaderPanel() {
        final JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        final JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setOpaque(true);

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setMaximumSize(new Dimension(thousand, hundred));

        final JLabel title = new JLabel("Menu");

        final JPanel menuWrapper = new JPanel();
        menuWrapper.setLayout(new BorderLayout());
        menuWrapper.setMaximumSize(new Dimension(fifty, fifty));
        menuWrapper.setBackground(Color.WHITE);

        menuWrapper.add(hamburgerMenu, BorderLayout.CENTER);
        headerPanel.add(menuWrapper, BorderLayout.WEST);
        headerPanel.add(logoLabel, BorderLayout.EAST);
        headerPanel.add(title, BorderLayout.CENTER);

        return headerPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final AccountSettingsState state = (AccountSettingsState) evt.getNewValue();
            username.setText(state.getUsername());
        }
        else if (evt.getPropertyName().equals("password")) {
            final AccountSettingsState state = (AccountSettingsState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "password updated for " + state.getUsername());
        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
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

}
