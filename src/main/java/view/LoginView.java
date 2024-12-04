package view;

// Too many jswing elements to import at once
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// Too many jswing elements to import at once
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "log in";

    private final LoginViewModel loginViewModel;
    private LoginController loginController;

    // Input elements
    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    // UI Elements
    private final JButton logIn;
    private final JButton cancel;
    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        // Title
        final JLabel title = new JLabel(LoginViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, LoginViewModel.TITLE_FONT));

        // Logo
        final JLabel logo = new JLabel(logoIcon);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input fields
        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);
        usernameInfo.setBackground(Color.WHITE);

        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);
        passwordInfo.setBackground(Color.WHITE);

        // Buttons
        final JPanel buttons = new JPanel();
        logIn = new JButton("Log in");
        cancel = new JButton("Cancel");
        final Dimension buttonSize = new Dimension(
                LoginViewModel.DEFUALT_WIDTH,
                LoginViewModel.DEFUALT_HEIGHT
        );
        logIn.setPreferredSize(buttonSize);
        cancel.setPreferredSize(buttonSize);
        buttons.setBackground(Color.WHITE);
        buttons.add(logIn);
        buttons.add(cancel);

        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            final LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(), loginViewModel
                            );
                        }
                    }
                }
        );

        cancel.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        loginController.switchToSignupView();
                    }
                }
        );

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
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

        // Construct Panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.add(logo);
        this.add(title);
        this.add(Box.createVerticalStrut(LoginViewModel.DEFUALT_HEIGHT));
        this.add(usernameInfo);
        this.add(usernameErrorField);
        this.add(passwordInfo);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
