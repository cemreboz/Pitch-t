package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import interface_adapter.ViewModel;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.account_settings.AccountSettingsState;
import interface_adapter.compare_personas.ComparePersonasState;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.expert.ExpertController;
import interface_adapter.expert.ExpertState;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.ShowNewPitchController;
import interface_adapter.persona.PersonaState;
import interface_adapter.pitch.PitchState;
import interface_adapter.vision.VisionState;

/**
 * A panel for the hamburger menu.
 */
public class HamburgerMenu extends JPanel {

    private static final String ERROR_FIELD = "error";

    private LoginController loginController;
    private AccountSettingsController accountSettingsController;
    private ExpertController expertController;
    private ShowNewPitchController newPitchController;
    private ViewModel viewModel;

    private final int hamburgerSize = 24;

    public HamburgerMenu(ViewModel viewModel) {
        this.viewModel = viewModel;

        final JLabel menuIcon = new JLabel("\u2630");
        menuIcon.setFont(new Font("Arial", Font.PLAIN, hamburgerSize));
        menuIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        menuIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMenu(e.getComponent());
            }
        });

        setLayout(new BorderLayout());
        add(menuIcon, BorderLayout.CENTER);
    }

    private void showMenu(Component invoker) {
        final JPopupMenu menu = new JPopupMenu();

        final JMenuItem dashboardItem = new JMenuItem("Dashboard");
        dashboardItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (viewModel.getState() instanceof DashboardState) {
                    final DashboardState currentState = (DashboardState) viewModel.getState();
                    loginController.execute(currentState.getUsername(), currentState.getPassword(), viewModel);
                }
                else if (viewModel.getState() instanceof AccountSettingsState) {
                    final AccountSettingsState currentState = (AccountSettingsState) viewModel.getState();
                    loginController.execute(currentState.getUsername(), currentState.getConfirmedPassword(), viewModel);
                }
                else if (viewModel.getState() instanceof PitchState) {
                    final PitchState currentState = (PitchState) viewModel.getState();
                    loginController.execute(currentState.getUsername(), currentState.getPassword(), viewModel);
                }
                else if (viewModel.getState() instanceof ExpertState) {
                    final ExpertState currentState = (ExpertState) viewModel.getState();
                    loginController.execute(currentState.getUsername(), currentState.getPassword(), viewModel);
                }
                else if (viewModel.getState() instanceof PersonaState) {
                    final PersonaState currentState = (PersonaState) viewModel.getState();
                    loginController.execute(currentState.getUsername(), currentState.getPassword(), viewModel);
                }
                else if (viewModel.getState() instanceof VisionState) {
                    final VisionState currentState = (VisionState) viewModel.getState();
                    loginController.execute(currentState.getUsername(), currentState.getPassword(), viewModel);
                }
                else if (viewModel.getState() instanceof ComparePersonasState) {
                    final ComparePersonasState currentState = (ComparePersonasState) viewModel.getState();
                    loginController.execute(currentState.getUsername(), currentState.getPassword(), viewModel);
                }
                else {
                    JOptionPane.showMessageDialog(dashboardItem, ERROR_FIELD);
                }
            }
        });
        menu.add(dashboardItem);

        final JMenuItem newPitchItem = new JMenuItem("New Pitch");
        newPitchItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (viewModel.getState() instanceof DashboardState) {
                    final DashboardState currentState = (DashboardState) viewModel.getState();
                    newPitchController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof AccountSettingsState) {
                    final AccountSettingsState currentState = (AccountSettingsState) viewModel.getState();
                    newPitchController.execute(currentState.getUsername(), currentState.getConfirmedPassword());
                }
                else if (viewModel.getState() instanceof PitchState) {
                    final PitchState currentState = (PitchState) viewModel.getState();
                    newPitchController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof ExpertState) {
                    final ExpertState currentState = (ExpertState) viewModel.getState();
                    newPitchController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof PersonaState) {
                    final PersonaState currentState = (PersonaState) viewModel.getState();
                    newPitchController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof VisionState) {
                    final VisionState currentState = (VisionState) viewModel.getState();
                    newPitchController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof ComparePersonasState) {
                    final ComparePersonasState currentState = (ComparePersonasState) viewModel.getState();
                    newPitchController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else {
                    JOptionPane.showMessageDialog(newPitchItem, ERROR_FIELD);
                }
            }
        });
        menu.add(newPitchItem);

        final JMenuItem expertsItem = new JMenuItem("Experts");
        expertsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (viewModel.getState() instanceof DashboardState) {
                    final DashboardState currentState = (DashboardState) viewModel.getState();
                    expertController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof AccountSettingsState) {
                    final AccountSettingsState currentState = (AccountSettingsState) viewModel.getState();
                    expertController.execute(currentState.getUsername(), currentState.getConfirmedPassword());
                }
                else if (viewModel.getState() instanceof PitchState) {
                    final PitchState currentState = (PitchState) viewModel.getState();
                    expertController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof ExpertState) {
                    final ExpertState currentState = (ExpertState) viewModel.getState();
                    expertController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof PersonaState) {
                    final PersonaState currentState = (PersonaState) viewModel.getState();
                    expertController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof VisionState) {
                    final VisionState currentState = (VisionState) viewModel.getState();
                    expertController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof ComparePersonasState) {
                    final ComparePersonasState currentState = (ComparePersonasState) viewModel.getState();
                    expertController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else {
                    JOptionPane.showMessageDialog(expertsItem, ERROR_FIELD);
                }
            }
        });
        menu.add(expertsItem);

        final JMenuItem accountSettingsItem = new JMenuItem("Account Settings");
        accountSettingsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (viewModel.getState() instanceof DashboardState) {
                    final DashboardState currentState = (DashboardState) viewModel.getState();
                    accountSettingsController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof AccountSettingsState) {
                    final AccountSettingsState currentState = (AccountSettingsState) viewModel.getState();
                    accountSettingsController.execute(currentState.getUsername(), currentState.getConfirmedPassword());
                }
                else if (viewModel.getState() instanceof PitchState) {
                    final PitchState currentState = (PitchState) viewModel.getState();
                    accountSettingsController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof ExpertState) {
                    final ExpertState currentState = (ExpertState) viewModel.getState();
                    accountSettingsController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof PersonaState) {
                    final PersonaState currentState = (PersonaState) viewModel.getState();
                    accountSettingsController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof VisionState) {
                    final VisionState currentState = (VisionState) viewModel.getState();
                    accountSettingsController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else if (viewModel.getState() instanceof ComparePersonasState) {
                    final ComparePersonasState currentState = (ComparePersonasState) viewModel.getState();
                    accountSettingsController.execute(currentState.getUsername(), currentState.getPassword());
                }
                else {
                    JOptionPane.showMessageDialog(accountSettingsItem, ERROR_FIELD);
                }
            }
        });
        menu.add(accountSettingsItem);

        // Show the popup menu
        menu.show(invoker, 0, invoker.getHeight());
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setAccountSettingsController(AccountSettingsController accountSettingsController) {
        this.accountSettingsController = accountSettingsController;
    }

    public void setExpertController(ExpertController expertController) {
        this.expertController = expertController;
    }

    public void setNewPitchController(ShowNewPitchController newPitchController) {
        this.newPitchController = newPitchController;
    }

}
