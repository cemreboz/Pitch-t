package view;

import interface_adapter.create_pitch.CreateNewPitchController;
import interface_adapter.create_pitch.CreateNewPitchState;
import interface_adapter.create_pitch.CreateNewPitchViewModel;
import interface_adapter.login.LoginController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

/**
 * The view for creating a new pitch.
 */
public class CreateNewPitchView extends JPanel implements PropertyChangeListener {

    private static final int HEIGHT1 = 100;
    private static final int SIZE = 16;
    private static final int FIELD_WIDTH = 400;
    private static final int FIELD_HEIGHT = 30;
    private static final int SMALLER_FIELD_HEIGHT = 20;
    private static final int TINY_HEIGHT = 5;
    private static final int FONT_SIZE = 28;
    private static final String FONT = "Arial";
    private static final int BORDER_LENGTH = 50;

    private final String viewName = "new pitch";
    private final CreateNewPitchViewModel newPitchViewModel;
    private CreateNewPitchController createNewPitchController;
    private LoginController loginController;

    private final JTextField nameField;
    private final JTextArea descriptionArea;
    private final JButton saveButton;
    private final JButton cancelButton;

    public CreateNewPitchView(CreateNewPitchViewModel newPitchViewModel) {
        this.newPitchViewModel = newPitchViewModel;
        this.newPitchViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Logo Panel
        final JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);
        final JLabel logoLabel = new JLabel();
        final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
        logoLabel.setIcon(logoIcon);
        logoPanel.add(logoLabel);

        // Form Panel
        final JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(
                SMALLER_FIELD_HEIGHT,
                BORDER_LENGTH,
                SMALLER_FIELD_HEIGHT,
                BORDER_LENGTH)
        );

        // Title Label
        final JLabel titleLabel = new JLabel("Create New Pitch", SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Name Field
        final JLabel nameLabel = new JLabel("Pitch Name:");
        nameLabel.setFont(new Font(FONT, Font.PLAIN, SIZE));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));

        // Description Field
        final JLabel descriptionLabel = new JLabel("Pitch Description:");
        descriptionLabel.setFont(new Font(FONT, Font.PLAIN, SIZE));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionArea = new JTextArea(TINY_HEIGHT, SMALLER_FIELD_HEIGHT);

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        final JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setMaximumSize(new Dimension(FIELD_WIDTH, HEIGHT1));

        // Buttons Panel
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        saveButton = new JButton("Save Pitch");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add components to form panel
        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(FIELD_HEIGHT));
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(SMALLER_FIELD_HEIGHT));
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionScroll);
        formPanel.add(Box.createVerticalStrut(FIELD_HEIGHT));
        formPanel.add(buttonPanel);

        // Add panels to main layout
        add(logoPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Action Listeners for Buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle save pitch
                try {
                    savePitch();
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            // Handle cancel action
            cancelPitch();
        });
    }

    /**
     * Handles the save pitch action.
     */
    private void savePitch() throws Exception {
        final String name = nameField.getText().trim();
        final String description = descriptionArea.getText().trim();
        final String image = "";
        final String targetAudience = "";

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields.",
                    "Warning", JOptionPane.WARNING_MESSAGE
            );
        }
        else {
            createNewPitchController.execute(name, description, image, targetAudience);
        }

    }

    /**
     * Handles the cancel action.
     */
    private void cancelPitch() {
        nameField.setText("");
        descriptionArea.setText("");

        // Navigate back to the dashboard or previous view
        loginController.execute(newPitchViewModel.getState().getCurrentUser().getName(),
                newPitchViewModel.getState().getCurrentUser().getPassword());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            final CreateNewPitchState state = (CreateNewPitchState) evt.getNewValue();
            setViewModelState(state);
        }
    }

    /**
     * Updates the view with the current state.
     * @param state The state to be displayed in the view.
     */
    private void setViewModelState(CreateNewPitchState state) {
        nameField.setText(state.getName());
        descriptionArea.setText(state.getDescription());
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Method to set the new pitch view controller.
     * @param createNewPitchController new pitch controller
     */
    public void setCreateNewPitchController(CreateNewPitchController createNewPitchController) {
        this.createNewPitchController = createNewPitchController;
    }

    /**
     * Method to set the login view controller.
     * @param loginController login controller
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
