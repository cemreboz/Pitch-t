package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import interface_adapter.create_pitch.CreateNewPitchController;
import interface_adapter.create_pitch.CreateNewPitchViewModel;
import interface_adapter.create_pitch.CreateNewPitchState;

/**
 * The view for creating a new pitch.
 */
public class CreateNewPitchView extends JPanel implements PropertyChangeListener {

    private final String viewName = "new pitch";
    private final CreateNewPitchViewModel newPitchViewModel;
    private CreateNewPitchController createNewPitchController;

    private final JTextField nameField;
    private final JTextArea descriptionArea;
    private final JTextField targetAudienceField;
    private final JTextField imageField;
    private final JButton saveButton;
    private final JButton cancelButton;

    private static final int FIELD_WIDTH = 300;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;

    public CreateNewPitchView(CreateNewPitchViewModel newPitchViewModel) {
        this.newPitchViewModel = newPitchViewModel;
        this.newPitchViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Create New Pitch", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Name Field
        JLabel nameLabel = new JLabel("Pitch Name:");
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(FIELD_WIDTH, 30));

        // Description Field
        JLabel descriptionLabel = new JLabel("Pitch Description:");
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);

        // Target Audience Field
        JLabel targetAudienceLabel = new JLabel("Target Audience:");
        targetAudienceField = new JTextField();
        targetAudienceField.setPreferredSize(new Dimension(FIELD_WIDTH, 30));

        // Image Field
        JLabel imageLabel = new JLabel("Image URL:");
        imageField = new JTextField();
        imageField.setPreferredSize(new Dimension(FIELD_WIDTH, 30));

        // Buttons (Save and Cancel)
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save Pitch");
        cancelButton = new JButton("Cancel");

        saveButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        cancelButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Adding components to layout
        add(titleLabel);
        add(Box.createVerticalStrut(20));
        add(nameLabel);
        add(nameField);
        add(Box.createVerticalStrut(10));
        add(descriptionLabel);
        add(descriptionScroll);
        add(Box.createVerticalStrut(10));
        add(targetAudienceLabel);
        add(targetAudienceField);
        add(Box.createVerticalStrut(10));
        add(imageLabel);
        add(imageField);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);

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

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle cancel action
                cancelPitch();
            }
        });
    }

    /**
     * Handles the save pitch action.
     */
    private void savePitch() throws Exception {
        String name = nameField.getText();
        String description = descriptionArea.getText();
        String targetAudience = targetAudienceField.getText();
        String image = imageField.getText();
        // TODO Rainy when you add your generating target audience you should be able to remove this field and parameter
        // the target audience field should be removed from the view too but its their temporarily for IDk what reason
        // viktor put it there
        // i.e. the controller below shouldnt't need to take targetAudience, it should be generated in the interactor

        createNewPitchController.execute(name, description, image, targetAudience);
    }

    /**
     * Handles the cancel action.
     */
    private void cancelPitch() {
        nameField.setText("");
        descriptionArea.setText("");
        targetAudienceField.setText("");
        imageField.setText("");

        // Optionally, close the view or navigate elsewhere
        // For example, calling a method to switch back to the DashboardView
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            CreateNewPitchState state = (CreateNewPitchState) evt.getNewValue();
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
        targetAudienceField.setText(String.valueOf(state.getTargetAudience()));
        imageField.setText(state.getImage());
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
}