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

/**
 * A panel for the hamburger menu.
 */
public class HamburgerMenu extends JPanel {

    public HamburgerMenu() {
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
            JOptionPane.showMessageDialog(this, "lol");
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
            JOptionPane.showMessageDialog(this, "lol");
        });
        menu.add(accountSettingsItem);

        // Show the popup menu
        menu.show(invoker, 0, invoker.getHeight());
    }
}
