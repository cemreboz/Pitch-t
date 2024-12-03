package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import interface_adapter.targetaudience.DetailedTargetAudiencePageViewModel;
import interface_adapter.targetaudience.DetailedController;
import entity.DetailedTargetAudience;
import interface_adapter.targetaudience.DetailedTargetAudienceState;

/**
 * The view for displaying detailed target audience information.
 */
public class DetailedView extends JPanel implements PropertyChangeListener {

    private final String viewName = "Detailed Target Audience";
    private final DetailedTargetAudiencePageViewModel viewModel;
    private DetailedController controller;
    private DetailedTargetAudience detailedTargetAudience;

    private final JPanel contentPanel;

    private static final int TITLE_FONT_SIZE = 24;
    private static final int FIELD_HEIGHT = 20;

    public DetailedView(DetailedTargetAudiencePageViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        // Title Label
        final JLabel titleLabel = new JLabel("Detailed Target Audience", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));
        add(titleLabel);
        add(Box.createVerticalStrut(FIELD_HEIGHT));

        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        final JScrollPane contentScrollPane = new JScrollPane(contentPanel);
        add(contentScrollPane);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            System.out.println("Property change detected for: " + evt.getPropertyName());

            // Get the updated state directly from the view model
            final DetailedTargetAudienceState updatedState = viewModel.getState();
            List<DetailedTargetAudience> detailedAudienceList = updatedState.getDetailedTargetAudience();

            if (detailedAudienceList == null || detailedAudienceList.isEmpty()) {
                System.out.println("Received updatedState is empty or null");
            } else {
                DetailedTargetAudience updatedTargetAudience = detailedAudienceList.get(0);
                System.out.println("Received updatedTargetAudience: " + updatedTargetAudience);
                updateDetailed(updatedTargetAudience);
            }
        }
    }

    // Updates the UI when new data is loaded
    public void updateDetailed(DetailedTargetAudience detailedTargetAudience) {
        SwingUtilities.invokeLater(() -> {
            if (detailedTargetAudience == null) {
                System.out.println("updateDetailed: Received null DetailedTargetAudience!");
            } else {
                System.out.println("updateDetailed: Received DetailedTargetAudience: " + detailedTargetAudience);

                // Update the reference and populate content
                this.detailedTargetAudience = detailedTargetAudience;
                populateContent();
            }
        });
    }

    private void populateContent() {
        SwingUtilities.invokeLater(() -> {
            contentPanel.removeAll(); // Clear existing content

            if (detailedTargetAudience == null) {
                System.out.println("populateContent: No data available for detailed target audience.");
                JLabel noDataLabel = new JLabel("No detailed target audience data available.", SwingConstants.CENTER);
                noDataLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
                contentPanel.add(noDataLabel);
            } else {
                System.out.println("populateContent: Populating with detailedTargetAudience: " + detailedTargetAudience);

                contentPanel.add(createAttributesPanel("Demographic Attributes", getDemographicAttributes()));
                contentPanel.add(createAttributesPanel("Psychographic Attributes", getPsychographicAttributes()));
                contentPanel.add(createAttributesPanel("Behavioral Attributes", getBehavioralAttributes()));
                contentPanel.add(createAttributesPanel("Other Attributes", getOtherAttributes()));
            }

            contentPanel.revalidate(); // Revalidate the content panel to refresh its layout
            contentPanel.repaint();    // Repaint the content panel to show the new data
        });
    }

    private JPanel createAttributesPanel(String title, List<String[]> attributes) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(title));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (String[] attr : attributes) {
            if (attr == null || attr.length < 2) {
                continue; // Ensure attr has both key and value
            }

            JLabel label = new JLabel(attr[0] + ": ");
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            panel.add(label, gbc);

            gbc.gridx++;
            gbc.weightx = 0.7;

            // Create a label for the attribute value (concatenate array values if there are multiple)
            String valueText = String.join(", ", attr);
            System.out.println("Formatted attribute value: " + valueText);  // Debug output
            JLabel value = new JLabel(valueText);
            value.setFont(new Font("SansSerif", Font.PLAIN, 14));
            panel.add(value, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.weightx = 0.3;
        }

        return panel;
    }

    private List<String[]> getDemographicAttributes() {
        List<String[]> attrs = new ArrayList<>();
        if (detailedTargetAudience != null) {
            attrs.add(new String[]{"Age Range", detailedTargetAudience.getMinAge() + " - " + detailedTargetAudience.getMaxAge()});
            attrs.add(new String[]{"Gender", safeString(detailedTargetAudience.getGender())});
            attrs.add(new String[]{"Education Level", safeString(detailedTargetAudience.getEducationLevel())});
            attrs.add(new String[]{"Occupation", safeString(detailedTargetAudience.getOccupation())});
            attrs.add(new String[]{"Income Level", safeString(detailedTargetAudience.getIncomeLevel())});
            attrs.add(new String[]{"Geographic Location", safeString(detailedTargetAudience.getGeographicLocation())});
        }
        return attrs;
    }

    private List<String[]> getPsychographicAttributes() {
        List<String[]> attrs = new ArrayList<>();
        if (detailedTargetAudience != null) {
            attrs.add(new String[]{"Interests and Passions", String.join(", ", safeList(detailedTargetAudience.getInterestsAndPassions()))});
            attrs.add(new String[]{"Values", String.join(", ", safeList(detailedTargetAudience.getValues()))});
            attrs.add(new String[]{"Personality Traits", String.join(", ", safeList(detailedTargetAudience.getPersonalityTraits()))});
            attrs.add(new String[]{"Lifestyle", safeString(detailedTargetAudience.getLifestyle())});
        }
        return attrs;
    }

    private List<String[]> getBehavioralAttributes() {
        List<String[]> attrs = new ArrayList<>();
        if (detailedTargetAudience != null) {
            attrs.add(new String[]{"Early Adopter", detailedTargetAudience.isEarlyAdopter() ? "Yes" : "No"});
            attrs.add(new String[]{"Tech Savviness", safeString(detailedTargetAudience.getTechSavviness())});
            attrs.add(new String[]{"Gadget Ownership", String.join(", ", safeList(detailedTargetAudience.getGadgetOwnership()))});
            attrs.add(new String[]{"Media Consumption", String.join(", ", safeList(detailedTargetAudience.getMediaConsumption()))});
            attrs.add(new String[]{"Online Engagement", String.join(", ", safeList(detailedTargetAudience.getOnlineEngagement()))});
            attrs.add(new String[]{"Influencer", detailedTargetAudience.isInfluencer() ? "Yes" : "No"});
        }
        return attrs;
    }

    private List<String[]> getOtherAttributes() {
        List<String[]> attrs = new ArrayList<>();
        if (detailedTargetAudience != null) {
            attrs.add(new String[]{"Event Participation", String.join(", ", safeList(detailedTargetAudience.getEventParticipation()))});
            attrs.add(new String[]{"Hobbies", String.join(", ", safeList(detailedTargetAudience.getHobbies()))});
            attrs.add(new String[]{"Brand Affinity", String.join(", ", safeList(detailedTargetAudience.getBrandAffinity()))});
            attrs.add(new String[]{"Environmental Concerns", detailedTargetAudience.isEnvironmentalConcerns() ? "Yes" : "No"});
            attrs.add(new String[]{"Global Perspective", detailedTargetAudience.isGlobalPerspective() ? "Yes" : "No"});
            attrs.add(new String[]{"Multilingual Abilities", detailedTargetAudience.isMultilingualAbilities() ? "Yes" : "No"});
        }
        return attrs;
    }

    // Helper methods to handle null values
    private String safeString(String value) {
        return value != null ? value : "N/A";
    }

    private List<String> safeList(List<String> list) {
        return list != null ? list : new ArrayList<>();
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Method to set the detailed target audience controller.
     *
     * @param controller detailed target audience controller
     */
    public void setController(DetailedController controller) {
        this.controller = controller;
    }

    /**
     * Fires a property change event to inform listeners that the viewModel has updated.
     */
    public void firePropertyChanged() {
        DetailedTargetAudienceState state = new DetailedTargetAudienceState();
        System.out.println("Firing property change event for detailedTargetAudience");
        firePropertyChange("state", null, state);
    }
}
