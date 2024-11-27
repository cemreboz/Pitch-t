package view;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import interface_adapter.ViewManagerModel;
import interface_adapter.account_settings.AccountSettingsState;
import interface_adapter.chat_expert.ChatExpertState;
import interface_adapter.dashboard.DashboardState;

/**
 * The View Manager for the program. It listens for property change events
 * in the ViewManagerModel and updates which View should be visible.
 */
public class ViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;
    private final ViewManagerModel viewManagerModel;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.views = views;
        this.cardLayout = cardLayout;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final Object newState = evt.getNewValue();

            if (newState instanceof String) {
                // Handle navigation by string view name
                final String viewModelName = (String) newState;
                cardLayout.show(views, viewModelName);
            }
            else if (newState instanceof ChatExpertState) {
                // Handle navigation to ExpertChatView
                cardLayout.show(views, "ExpertChatView");
            }
            else if (newState instanceof DashboardState) {
                // Handle navigation to DashboardView if needed
                cardLayout.show(views, "DashboardView");
            }
            else if (newState instanceof AccountSettingsState) {
                // Handle navigation to AccountSettingsView if needed
                cardLayout.show(views, "AccountSettingsView");
            }
            else {
                // Handle unexpected states
                throw new IllegalArgumentException("Unknown state: " + newState);
            }
        }
    }
}
