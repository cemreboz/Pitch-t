package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.*;

import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.NewPitchController;
import interface_adapter.pitch.PitchState;
import interface_adapter.pitch.PitchViewModel;

/**
 * The view for when a user wants to view the details of a specific pitch.
 */
public class PitchView extends JPanel implements PropertyChangeListener {

    private final String viewName = "pitch";
    private final PitchViewModel pitchViewModel;
    private HamburgerMenu hamburgerMenu;

    private final int fifty = 50;
    private final int hundred = 100;
    private final int thousand = 1000;

    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
    private JPanel namePanel;

    public PitchView(PitchViewModel pitchViewModel) {
        this.pitchViewModel = pitchViewModel;
        this.pitchViewModel.addPropertyChangeListener(this);

        final JPanel headerPanel = createHeaderPanel();

        final PitchState state = pitchViewModel.getState();
        final String pitchName = state.getPitch().getName();

        final JLabel nameLabel = new JLabel(pitchName);

        namePanel = new JPanel();
        namePanel.add(nameLabel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(headerPanel);
        this.add(namePanel);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(() -> {
            final PitchState state = (PitchState) evt.getNewValue();
            if (state.getPitchLoadError() != null) {
                JOptionPane.showMessageDialog(null, state.getPitchLoadError());
            } else if (state.getDetailedTaLoadError() != null) {
                JOptionPane.showMessageDialog(null, state.getDetailedTaLoadError());
            } else {
                // Debug statement to check pitch name and target audience values
                System.out.println("Property Change Triggered");
                System.out.println("Updated Pitch Name: " + state.getPitch().getName());
                System.out.println("Updated Target Audience: " + state.getPitch().getTargetAudienceList());

                // Update pitch name
                final String newPitchName = state.getPitch().getName();
                namePanel.removeAll();
                final JLabel nameLabel = new JLabel("Pitch Name: " + newPitchName);
                namePanel.add(nameLabel);

                final JPanel targetAudiencePanel = new JPanel();
                // Update target audience details
                final List<String> newTargetAudience = state.getPitch().getTargetAudienceList();
                targetAudiencePanel.removeAll();
                if (newTargetAudience != null && !newTargetAudience.isEmpty()) {
                    targetAudiencePanel.setLayout(new BoxLayout(targetAudiencePanel, BoxLayout.Y_AXIS));
                    targetAudiencePanel.add(new JLabel("Target Audience:"));
                    for (String audience : newTargetAudience) {
                        final JLabel audienceLabel = new JLabel("- " + audience);
                        targetAudiencePanel.add(audienceLabel);
                    }
                }
                else {
                    final JLabel targetAudienceLabel = new JLabel("Target Audience: Not available");
                    targetAudiencePanel.add(targetAudienceLabel);
                }

                // Revalidate and repaint to update the UI
                namePanel.revalidate();
                namePanel.repaint();
                targetAudiencePanel.revalidate();
                targetAudiencePanel.repaint();
            }
        });
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
}
