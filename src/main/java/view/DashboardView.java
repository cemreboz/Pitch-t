package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entity.Pitch;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.login.LoginController;

/**
 * The view for when the user is logged in and at the dashboard page.
 */
public class DashboardView extends JPanel implements PropertyChangeListener {

    private final String viewName = "dashboard";
    private final DashboardViewModel dashboardViewModel;
    private final HamburgerMenu hamburgerMenu;

    private final JButton newPitch;
    private final JButton experts;
    private final int fifty = 50;
    private final int hundred = 100;
    private final int thousand = 1000;

    // private DashboardController dashboardController;
    // private NewPitchController newPitchController;
    // private ExpertController expertController;
    private LoginController loginController;

    private final JPanel pitchHistoryPanel = new JPanel();

    public DashboardView(DashboardViewModel dashboardViewModel) {

        this.dashboardViewModel = dashboardViewModel;
        this.dashboardViewModel.addPropertyChangeListener(this);

        // TODO Logo

        final JPanel headerPanel = new JPanel();

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setMaximumSize(new Dimension(thousand, hundred));

        hamburgerMenu = new HamburgerMenu(dashboardViewModel);

        final JLabel title = new JLabel("Dashboard");

        final JPanel menuWrapper = new JPanel();
        menuWrapper.setLayout(new BorderLayout());
        menuWrapper.setMaximumSize(new Dimension(fifty, fifty));
        menuWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        menuWrapper.add(hamburgerMenu, BorderLayout.CENTER);
        headerPanel.add(menuWrapper, BorderLayout.WEST);
        headerPanel.add(title, BorderLayout.CENTER);

        final JPanel buttons = new JPanel();
        newPitch = new JButton("New Pitch");
        buttons.add(newPitch);
        experts = new JButton("Experts");
        buttons.add(experts);
        buttons.setMaximumSize(new Dimension(thousand, hundred));

        pitchHistoryPanel.setLayout(new BoxLayout(pitchHistoryPanel, BoxLayout.Y_AXIS));
        final JScrollPane scrollPane = new JScrollPane(pitchHistoryPanel);

        newPitch.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) { JOptionPane
                            .showMessageDialog(newPitch, "go to new pitch");
                    }
                }
        );

        experts.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) { JOptionPane
                            .showMessageDialog(experts, "go to experts");
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(buttons);
        this.add(scrollPane);

    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
        hamburgerMenu.setLoginController(loginController);
    }
    /*
    public void setNewPitchController(NewPitchController newPitchController) {
        this.newPitchController = newPitchController;
    }

    public void setExpertController(ExpertController expertController) {
        this.expertController = expertController;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
    */
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
                            JOptionPane.showMessageDialog(pitchButton, "go to" + pitch.getName());
                            // replace messagedialog ^ with this: DashboardController.execute(pitch)
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
