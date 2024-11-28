package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import entity.Pitch;
import interface_adapter.ViewManagerModel;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.NewPitchController;

/**
 * The view for when the user is logged in and at the dashboard page.
 */
public class DashboardView extends JPanel implements PropertyChangeListener {

    private final String viewName = "dashboard";
    private final DashboardViewModel dashboardViewModel;
    private HamburgerMenu hamburgerMenu;

    private JButton newPitch;
    private JButton experts;
    private final int fifty = 50;
    private final int hundred = 100;
    private final int thousand = 1000;

    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));

    private DashboardController dashboardController;
    private NewPitchController newPitchController;
    private ExpertController expertController;

    private final JPanel pitchHistoryPanel = new JPanel();

    public DashboardView(DashboardViewModel dashboardViewModel) {

        this.dashboardViewModel = dashboardViewModel;
        this.dashboardViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        final JPanel headerPanel = createHeaderPanel();
        final JPanel footerPanel = createFooterPanel();

        pitchHistoryPanel.setLayout(new BoxLayout(pitchHistoryPanel, BoxLayout.Y_AXIS));
        pitchHistoryPanel.setBackground(Color.WHITE);
        pitchHistoryPanel.setMaximumSize(new Dimension(thousand, thousand));
        final JScrollPane scrollPane = new JScrollPane(pitchHistoryPanel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(footerPanel);
        this.add(scrollPane);
    }

    private JPanel createHeaderPanel() {
        final JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        final JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setMaximumSize(new Dimension(thousand, hundred));

        hamburgerMenu = new HamburgerMenu(dashboardViewModel);
        hamburgerMenu.setBackground(Color.WHITE);

        final JLabel title = new JLabel("Dashboard");

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
        final JPanel buttons = new JPanel();
        newPitch = new JButton("New Pitch");
        buttons.add(newPitch);
        experts = new JButton("Experts");
        buttons.add(experts);
        buttons.setMaximumSize(new Dimension(thousand, hundred));
        buttons.setBackground(Color.WHITE);

        newPitch.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        final DashboardState state = dashboardViewModel.getState();
                        newPitchController.execute(state.getUsername(), state.getPassword());
                    }
                }
        );

        experts.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        final DashboardState state = dashboardViewModel.getState();
                        expertController.execute(state.getUsername(), state.getPassword());
                    }
                }
        );

        return buttons;
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
     * Method to set the dashboard pitch view controller.
     * @param dashboardController dashboard controller
     */
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    /**
     * Method to set the new pitch view controller.
     * @param newPitchController new pitch controller
     */
    public void setNewPitchController(NewPitchController newPitchController) {
        this.newPitchController = newPitchController;
        hamburgerMenu.setNewPitchController(newPitchController);
    }

    /**
     * Method to set the expert controller.
     * @param expertController expert controller
     */
    public void setExpertController(ExpertController expertController) {
        this.expertController = expertController;
        hamburgerMenu.setExpertController(expertController);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            final DashboardState state = (DashboardState) evt.getNewValue();
            if (state.getPitchLoadError() == null) {
                updatePitchHistory(state.getPitches());
            }
            else {
                JOptionPane.showMessageDialog(this, state.getPitchLoadError());
            }
        }
    }

    private void updatePitchHistory(List<Pitch> pitches) {
        pitchHistoryPanel.removeAll();
        if (pitches.isEmpty()) {
            pitchHistoryPanel.add(new JLabel("No pitches found"));
        }
        for (Pitch pitch : pitches) {
            final JButton pitchButton = new JButton(pitch.getName());
            pitchButton.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            final DashboardState state = dashboardViewModel.getState();
                            dashboardController.execute(pitch, state.getUsername(), state.getPassword());
                        }
                    });
            pitchHistoryPanel.add(pitchButton);
        }
        pitchHistoryPanel.revalidate();
        pitchHistoryPanel.repaint();
    }

    public String getViewName() {
        return viewName;
    }
}
