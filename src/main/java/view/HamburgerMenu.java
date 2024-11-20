package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
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
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;

/**
 * A panel for the hamburger menu.
 */
public class HamburgerMenu extends JPanel {

    private LoginController loginController;
    private AccountSettingsController accountSettingsController;
    private ViewModel viewModel;

    public HamburgerMenu(ViewModel viewModel) {
        this.viewModel = viewModel;

        final JLabel menuIcon = new JLabel("\u2630");
        menuIcon.setFont(new Font("Arial", Font.PLAIN, 24));
        menuIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add mouse listener to display the popup menu
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
        // Create the popup menu
        final JPopupMenu menu = new JPopupMenu();

        final JMenuItem dashboardItem = new JMenuItem("Dashboard");
        dashboardItem.addActionListener(evt -> {
            if (viewModel.getState() instanceof DashboardState) {
                final DashboardState currentState = (DashboardState) viewModel.getState();
                loginController.execute(currentState.getUsername(), currentState.getPassword());
            }
            else if (viewModel.getState() instanceof AccountSettingsState) {
                final AccountSettingsState currentState = (AccountSettingsState) viewModel.getState();
                loginController.execute(currentState.getUsername(), currentState.getConfirmedPassword());
            }
            else {
                JOptionPane.showMessageDialog(this, "error");
            }
        });
        menu.add(dashboardItem);

        final JMenuItem newPitchItem = new JMenuItem("New Pitch");
        newPitchItem.addActionListener(evt -> {
            JOptionPane.showMessageDialog(this, "lol");
        });
        menu.add(newPitchItem);

        final JMenuItem expertsItem = new JMenuItem("Experts");
        expertsItem.addActionListener(evt -> {
            JOptionPane.showMessageDialog(this, "lol");
        });
        menu.add(expertsItem);

        final JMenuItem accountSettingsItem = new JMenuItem("Account Settings");
        accountSettingsItem.addActionListener(evt -> {
            if (viewModel.getState() instanceof DashboardState) {
                final DashboardState currentState = (DashboardState) viewModel.getState();
                accountSettingsController.execute(currentState.getUsername(), currentState.getPassword());
            }
            else if (viewModel.getState() instanceof AccountSettingsState) {
                final AccountSettingsState currentState = (AccountSettingsState) viewModel.getState();
                accountSettingsController.execute(currentState.getUsername(), currentState.getConfirmedPassword());
            }
            else {
                JOptionPane.showMessageDialog(this, "error");
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

}
