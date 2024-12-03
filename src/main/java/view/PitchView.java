package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import entity.Pitch;
import interface_adapter.ViewManagerModel;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.ShowNewPitchController;
import interface_adapter.pitch.PitchState;
import interface_adapter.pitch.PitchViewModel;
import interface_adapter.targetaudience.DetailedController;
import interface_adapter.targetaudience.DetailedTargetAudiencePageViewModel;
import interface_adapter.view_personas.ViewPersonasController;
import use_case.set_targetaudience.DetailedInputData;

/**
 * The view for when a user wants to view the details of a specific pitch.
 */
public class PitchView extends JPanel implements PropertyChangeListener {

    private final String viewName = "pitch";
    private final PitchViewModel pitchViewModel;
    private final ViewManagerModel viewManagerModel;
    private HamburgerMenu hamburgerMenu;

    private final int fifty = 50;
    private final int hundred = 100;
    private final int threeHundred = 300;
    private final int thousand = 1000;
    private final int fourHundred = 400;
    private final int sixHundred = 600;

    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
    private JPanel namePanel;
    private ViewPersonasController viewPersonasController;
    private DetailedController detailedController;
    private JComboBox<String> audienceDropdown;
    private ExpertController expertController;

    public PitchView(PitchViewModel pitchViewModel, ViewManagerModel viewManagerModel) {
        this.pitchViewModel = pitchViewModel;
        this.pitchViewModel.addPropertyChangeListener(this);

        this.viewManagerModel = viewManagerModel;

        final JPanel headerPanel = createHeaderPanel();

        final PitchState state = pitchViewModel.getState();
        final String pitchName = state.getPitch().getName();

        final JLabel nameLabel = new JLabel(pitchName);

        namePanel = new JPanel();
        namePanel.add(nameLabel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(headerPanel);
        this.add(namePanel);

        // Add the "View Personas" button
        final JButton viewPersonasButton = new JButton("View Personas");
        viewPersonasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleViewPersonasButton();
            }
        });

        this.add(viewPersonasButton);

        final JButton viewTargetAudienceButton = new JButton("View Detailed Target Audience");
        viewTargetAudienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleViewTargetAudienceButton();
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.add(viewTargetAudienceButton);
    }

    private void handleViewTargetAudienceButton() throws Exception {
        final PitchState pitchState = pitchViewModel.getState();
        final Pitch pitch = pitchState.getPitch();

        if (pitch == null) {
            // Handle error scenario - pitch not loaded properly
            final String error = pitchState.getPitchLoadError();
            JOptionPane.showMessageDialog(this, "Error loading pitch: " + error, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Get the selected target audience category from the JComboBox
            final String selectedAudienceCategory = (String) audienceDropdown.getSelectedItem();

            if (selectedAudienceCategory == null || selectedAudienceCategory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a target audience category.", "No Selection", JOptionPane.WARNING_MESSAGE);
            } else {
                // Create DetailedInputData with the selected audience category
                final DetailedInputData inputData = new DetailedInputData(pitch.getName(), pitch.getDescription(), selectedAudienceCategory);
                if (detailedController != null) {
                    // Generate the detailed target audience data
                    detailedController.generateDetailed(inputData);

                    // Assuming detailedController correctly updates the ViewModel
                    // Create an instance of DetailedView and display it in a pop-up
                    SwingUtilities.invokeLater(() -> showDetailedViewPopup());
                }
            }
        }
    }

    private void showDetailedViewPopup() {
        // Create an instance of DetailedView
        DetailedTargetAudiencePageViewModel detailedViewModel = new DetailedTargetAudiencePageViewModel();
        DetailedView detailedView = new DetailedView(detailedViewModel);
        detailedView.setController(detailedController);

        // Create a dialog to wrap the DetailedView
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Detailed Target Audience", true);
        dialog.getContentPane().add(detailedView);
        dialog.setSize(sixHundred, fourHundred);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        final JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        final JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setMaximumSize(new Dimension(thousand, hundred));

        hamburgerMenu = new HamburgerMenu(pitchViewModel);
        hamburgerMenu.setBackground(Color.WHITE);

        final JLabel title = new JLabel("Pitch View");

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

    private JPanel createFooterPanel() {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        final JButton personaButton = new JButton("Personas");
        buttonPanel.add(personaButton);

        final JButton askExpertsButton = new JButton("Ask Experts");
        buttonPanel.add(askExpertsButton);

        personaButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        final PitchState state = pitchViewModel.getState();
                        expertController.execute(state.getUsername(), state.getPassword());
                    }
                }
        );

        askExpertsButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        final PitchState state = pitchViewModel.getState();
                        expertController.execute(state.getUsername(), state.getPassword());
                    }
                }
        );

        return buttonPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final PitchState state = (PitchState) evt.getNewValue();
        if (state.getPitchLoadError() != null) {
            JOptionPane.showMessageDialog(null, state.getPitchLoadError());
        }
        else if (state.getDetailedTaLoadError() != null) {
            JOptionPane.showMessageDialog(null, state.getDetailedTaLoadError());
        }
        else {

            final String newPitchName = state.getPitch().getName();
            namePanel.removeAll();

            final JLabel nameLabel = new JLabel(newPitchName);
            namePanel.add(nameLabel);

            final String newPitchDescription = state.getPitch().getDescription();
            final JLabel descriptionLabel = new JLabel(newPitchDescription);
            descriptionLabel.setPreferredSize(new Dimension(threeHundred, descriptionLabel.getPreferredSize().height));
            namePanel.add(descriptionLabel);

            final String newPitchImage = state.getPitch().getImage();
            if (newPitchImage != null) {
                final JLabel imageLabel = new JLabel(newPitchImage);
                namePanel.add(imageLabel);
            }

            final JPanel audiencePanel = new JPanel();
            audiencePanel.setLayout(new BoxLayout(audiencePanel, BoxLayout.Y_AXIS));
            audiencePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Target Audiences"));

            // Create a JComboBox for the target audiences
            audienceDropdown = new JComboBox<>(state.getPitch().getTargetAudienceList().toArray(new String[0]));
            audiencePanel.add(new JLabel("Select Audience:"));
            audiencePanel.add(audienceDropdown);
            namePanel.add(audiencePanel);
        }

        namePanel.revalidate();
        namePanel.repaint();

    }

    // Start of View Personas user flow
    private void handleViewPersonasButton() {
        // Assuming you have an instance of PitchState
        final PitchState pitchState = pitchViewModel.getState();
        final Pitch pitch = pitchState.getPitch();

        if (pitch == null) {
            // Handle error scenario - pitch not loaded properly
            final String error = pitchState.getPitchLoadError();
            JOptionPane.showMessageDialog(this, "Error loading pitch: " + error, "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            this.viewPersonasController.execute(pitch);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewPersonasController(ViewPersonasController controller) {
        this.viewPersonasController = controller;
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
        this.expertController = expertController;
        hamburgerMenu.setExpertController(expertController);
    }

    /**
     * Method to set hamburger menu new pitch controller.
     * @param newPitchController new pitch controller
     */
    public void setNewPitchController(ShowNewPitchController newPitchController) {
        hamburgerMenu.setNewPitchController(newPitchController);
    }

    public void setDetailedController(DetailedController controller) {
        this.detailedController = controller;
    }

}
