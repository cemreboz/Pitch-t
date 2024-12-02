package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entity.Pitch;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.ShowNewPitchController;
import interface_adapter.pitch.PitchState;
import interface_adapter.pitch.PitchViewModel;
import interface_adapter.view_personas.ViewPersonasController;

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
    private ViewPersonasController viewPersonasController;

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

        // Add the "View Personas" button
        JButton viewPersonasButton = new JButton("View Personas");
        viewPersonasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleViewPersonasButton();
            }
        });

        this.add(viewPersonasButton);
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

            namePanel.revalidate();
            namePanel.repaint();
        }
    }

    // Start of View Personas user flow
    private void handleViewPersonasButton() {
        // Assuming you have an instance of PitchState
        PitchState pitchState = pitchViewModel.getState();
        Pitch pitch = pitchState.getPitch();

        if (pitch == null) {
            // Handle error scenario - pitch not loaded properly
            String error = pitchState.getPitchLoadError();
            JOptionPane.showMessageDialog(this, "Error loading pitch: " + error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.viewPersonasController.execute(pitch);
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
