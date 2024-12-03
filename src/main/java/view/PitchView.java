package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entity.Pitch;
import interface_adapter.ViewManagerModel;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.ShowNewPitchController;
import interface_adapter.pitch.PitchState;
import interface_adapter.pitch.PitchViewModel;
import interface_adapter.view_personas.ViewPersonasController;

/**
 * The view for displaying the details of a specific pitch.
 */
public class PitchView extends JPanel implements PropertyChangeListener {

    public static final String FONT = "Arial";
    public static final int FONT_SIZE = 24;
    public static final int SPACING = 10;
    public static final int REGULAR_FONT = 16;
    public static final int TARGETAUDIENCE_FONT = 18;
    public static final int BIGGER_SPACING = 20;
    public static final int PITCH_NAME_SIZE = 22;
    private final String viewName = "pitch";
    private final PitchViewModel pitchViewModel;
    private final ViewManagerModel viewManagerModel;
    private HamburgerMenu hamburgerMenu;

    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
    private ViewPersonasController viewPersonasController;
    private ExpertController expertController;

    private JPanel contentPanel;

    /**
     * Constructs a new PitchView.
     *
     * @param pitchViewModel   the ViewModel for the pitch
     * @param viewManagerModel the ViewManagerModel for navigation
     */
    public PitchView(PitchViewModel pitchViewModel, ViewManagerModel viewManagerModel) {
        this.pitchViewModel = pitchViewModel;
        this.pitchViewModel.addPropertyChangeListener(this);

        this.viewManagerModel = viewManagerModel;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        // Create Header Panel
        final JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Create Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.WEST);

        // Load initial state
        updateContent();
    }

    private JPanel createHeaderPanel() {
        final JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        final JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BorderLayout());

        hamburgerMenu = new HamburgerMenu(pitchViewModel);
        hamburgerMenu.setBackground(Color.WHITE);

        final JLabel title = new JLabel("Pitch View");
        title.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        final JPanel menuWrapper = new JPanel();
        menuWrapper.setLayout(new BorderLayout());
        menuWrapper.setBackground(Color.WHITE);
        menuWrapper.add(hamburgerMenu, BorderLayout.CENTER);

        headerPanel.add(menuWrapper, BorderLayout.WEST);
        headerPanel.add(title, BorderLayout.CENTER);
        headerPanel.add(logoLabel, BorderLayout.EAST);

        return headerPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            updateContent();
        }
    }

    private void updateContent() {
        contentPanel.removeAll();

        final PitchState state = pitchViewModel.getState();
        final Pitch pitch = state.getPitch();

        if (pitch == null) {
            String error = state.getPitchLoadError();
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading pitch: " + error,
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Pitch Name (on the left)
        final JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(Color.WHITE);
        final JLabel nameLabel = new JLabel(pitch.getName());
        nameLabel.setFont(new Font(FONT, Font.BOLD, PITCH_NAME_SIZE));
        namePanel.add(nameLabel);
        contentPanel.add(namePanel);

        // Spacing between name and description
        contentPanel.add(Box.createVerticalStrut(SPACING));

        // Pitch Description
        final JLabel descriptionLabel = new JLabel(pitch.getDescription());
        descriptionLabel.setFont(new Font(FONT, Font.PLAIN, REGULAR_FONT));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(descriptionLabel);

        // Spacing between description and target audiences
        contentPanel.add(Box.createVerticalStrut(BIGGER_SPACING));

        // Target Audiences
        final List<String> targetAudiences = pitch.getTargetAudienceList();
        if (targetAudiences != null && !targetAudiences.isEmpty()) {
            final JLabel audienceLabel = new JLabel("Target Audiences:");
            audienceLabel.setFont(new Font(FONT, Font.BOLD, TARGETAUDIENCE_FONT));
            audienceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(audienceLabel);

            contentPanel.add(Box.createVerticalStrut(SPACING));

            for (String audience : targetAudiences) {
                final JLabel audienceItem = new JLabel("- " + audience);
                audienceItem.setFont(new Font(FONT, Font.PLAIN, REGULAR_FONT));
                audienceItem.setAlignmentX(Component.LEFT_ALIGNMENT);
                contentPanel.add(audienceItem);
            }
        }

        // Spacing before the button
        contentPanel.add(Box.createVerticalStrut(BIGGER_SPACING));

        // Add the "View Personas" button
        final JButton viewPersonasButton = new JButton("View Personas");
        viewPersonasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleViewPersonasButton();
            }
        });

        contentPanel.add(viewPersonasButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Handle View Personas Button Click
    private void handleViewPersonasButton() {
        final PitchState pitchState = pitchViewModel.getState();
        final Pitch pitch = pitchState.getPitch();

        if (pitch == null) {
            final String error = pitchState.getPitchLoadError();
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading pitch: " + error,
                    "Error", JOptionPane.ERROR_MESSAGE);
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
     * Sets the login controller for the hamburger menu.
     *
     * @param loginController the login controller
     */
    public void setLoginController(LoginController loginController) {
        hamburgerMenu.setLoginController(loginController);
    }

    /**
     * Sets the account settings controller for the hamburger menu.
     *
     * @param accountSettingsController the account settings controller
     */
    public void setAccountSettingsController(AccountSettingsController accountSettingsController) {
        hamburgerMenu.setAccountSettingsController(accountSettingsController);
    }

    /**
     * Sets the expert controller for the hamburger menu.
     *
     * @param expertController the expert controller
     */
    public void setExpertController(ExpertController expertController) {
        this.expertController = expertController;
        hamburgerMenu.setExpertController(expertController);
    }

    /**
     * Sets the new pitch controller for the hamburger menu.
     *
     * @param newPitchController the new pitch controller
     */
    public void setNewPitchController(ShowNewPitchController newPitchController) {
        hamburgerMenu.setNewPitchController(newPitchController);
    }
}