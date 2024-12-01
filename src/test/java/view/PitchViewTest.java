package view;

import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.NewPitchController;
import interface_adapter.pitch.PitchState;
import interface_adapter.pitch.PitchViewModel;
import interface_adapter.view_personas.ViewPersonasController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The view for when a user wants to view the details of a specific pitch.
 */
public class PitchViewTest extends JPanel implements PropertyChangeListener {

    private final String viewName = "pitch";
    private final PitchViewModel pitchViewModel;
    private HamburgerMenu hamburgerMenu;
    private ViewPersonasController viewPersonasController;

    private final int fifty = 50;
    private final int hundred = 100;
    private final int thousand = 1000;

    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
    private JPanel namePanel;
    private JButton viewPersonasButton;

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

        // Add "View Personas" button
        viewPersonasButton = new JButton("View Personas");
        viewPersonasButton.setAlignmentX(CENTER_ALIGNMENT);
        viewPersonasButton.addActionListener(e -> handleViewPersonasButton());
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

    private void handleViewPersonasButton() {
        if (viewPersonasController != null) {
            PitchState state = pitchViewModel.getState();
            viewPersonasController.execute(state.getPitch());
        }
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

    /**
     * Method to set view personas controller.
     * @param viewPersonasController view personas controller
     */
    public void setViewPersonasController(ViewPersonasController viewPersonasController) {
        this.viewPersonasController = viewPersonasController;
    }
}