package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import entity.DetailedTargetAudience;
import interface_adapter.targetaudience.DetailedController;
import interface_adapter.targetaudience.DetailedTargetAudiencePageViewModel;

/**
 * The view page for Detailed Target Audience.
 */
public class DetailedView extends JFrame implements PropertyChangeListener {
    private JPanel contentPanel;
    private final DetailedTargetAudiencePageViewModel viewModel;
    private final DetailedController controller;

    public DetailedView(DetailedTargetAudiencePageViewModel viewModel, DetailedController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        // Register as a listener to the ViewModel
        this.viewModel.addPropertyChangeListener(this);

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Detailed Target Audience - Loading...");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 400));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Detailed Target Audience - Loading...", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Set main panel as content pane
        setContentPane(mainPanel);

        // Attempt to load data via controller
        try {
            controller.generateDetailed(null); // Replace `null` with appropriate DetailedInputData if needed
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("detailedTA".equals(evt.getPropertyName())) {
            // Update the UI when ViewModel's state changes
            DetailedTargetAudience updatedTA = viewModel.getState().getDetailedTargetAudience().get(0); // Assuming the list contains a single target audience
            updateDetailedTA(updatedTA);
        }
    }

    // Updates the UI when new data is loaded
    public void updateDetailedTA(DetailedTargetAudience detailedTA) {
        SwingUtilities.invokeLater(() -> {
            this.detailedTA = detailedTA;
            setTitle("Detailed Target Audience - " + detailedTA.getName());
            populateContent();
        });
    }

    private void populateContent() {
        contentPanel.removeAll(); // Clear existing content

        contentPanel.add(createAttributesPanel("Demographic Attributes", getDemographicAttributes()));
        contentPanel.add(createAttributesPanel("Psychographic Attributes", getPsychographicAttributes()));
        contentPanel.add(createAttributesPanel("Behavioral Attributes", getBehavioralAttributes()));
        contentPanel.add(createAttributesPanel("Other Attributes", getOtherAttributes()));

        contentPanel.revalidate();
        contentPanel.repaint();
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
            JLabel label = new JLabel(attr[0] + ": ");
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            panel.add(label, gbc);

            gbc.gridx++;
            gbc.weightx = 0.7;
            JLabel value = new JLabel(attr[1]);
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
        attrs.add(new String[]{"Age Range", detailedTA != null ? detailedTA.getMinAge() + " - " + detailedTA.getMaxAge() : "Loading..."});
        attrs.add(new String[]{"Gender", safeString(detailedTA != null ? detailedTA.getGender() : null)});
        attrs.add(new String[]{"Education Level", safeString(detailedTA != null ? detailedTA.getEducationLevel() : null)});
        attrs.add(new String[]{"Occupation", safeString(detailedTA != null ? detailedTA.getOccupation() : null)});
        attrs.add(new String[]{"Income Level", safeString(detailedTA != null ? detailedTA.getIncomeLevel() : null)});
        attrs.add(new String[]{"Geographic Location", safeString(detailedTA != null ? detailedTA.getGeographicLocation() : null)});
        return attrs;
    }

    private List<String[]> getPsychographicAttributes() {
        List<String[]> attrs = new ArrayList<>();
        attrs.add(new String[]{"Interests and Passions", String.join(", ", safeList(detailedTA != null ? detailedTA.getInterestsAndPassions() : null))});
        attrs.add(new String[]{"Values", String.join(", ", safeList(detailedTA != null ? detailedTA.getValues() : null))});
        attrs.add(new String[]{"Personality Traits", String.join(", ", safeList(detailedTA != null ? detailedTA.getPersonalityTraits() : null))});
        attrs.add(new String[]{"Lifestyle", safeString(detailedTA != null ? detailedTA.getLifestyle() : null)});
        return attrs;
    }

    private List<String[]> getBehavioralAttributes() {
        List<String[]> attrs = new ArrayList<>();
        attrs.add(new String[]{"Early Adopter", detailedTA != null ? (detailedTA.isEarlyAdopter() ? "Yes" : "No") : "Loading..."});
        attrs.add(new String[]{"Tech Savviness", safeString(detailedTA != null ? detailedTA.getTechSavviness() : null)});
        attrs.add(new String[]{"Gadget Ownership", String.join(", ", safeList(detailedTA != null ? detailedTA.getGadgetOwnership() : null))});
        attrs.add(new String[]{"Media Consumption", String.join(", ", safeList(detailedTA != null ? detailedTA.getMediaConsumption() : null))});
        attrs.add(new String[]{"Online Engagement", String.join(", ", safeList(detailedTA != null ? detailedTA.getOnlineEngagement() : null))});
        attrs.add(new String[]{"Influencer", detailedTA != null ? (detailedTA.isInfluencer() ? "Yes" : "No") : "Loading..."});
        return attrs;
    }

    private List<String[]> getOtherAttributes() {
        List<String[]> attrs = new ArrayList<>();
        attrs.add(new String[]{"Event Participation", String.join(", ", safeList(detailedTA != null ? detailedTA.getEventParticipation() : null))});
        attrs.add(new String[]{"Hobbies", String.join(", ", safeList(detailedTA != null ? detailedTA.getHobbies() : null))});
        attrs.add(new String[]{"Brand Affinity", String.join(", ", safeList(detailedTA != null ? detailedTA.getBrandAffinity() : null))});
        attrs.add(new String[]{"Environmental Concerns", detailedTA != null ? (detailedTA.isEnvironmentalConcerns() ? "Yes" : "No") : "Loading..."});
        attrs.add(new String[]{"Global Perspective", detailedTA != null ? (detailedTA.isGlobalPerspective() ? "Yes" : "No") : "Loading..."});
        attrs.add(new String[]{"Multilingual Abilities", detailedTA != null ? (detailedTA.isMultilingualAbilities() ? "Yes" : "No") : "Loading..."});
        return attrs;
    }

    // Helper methods to handle null values
    private String safeString(String value) {
        return value != null ? value : "Loading...";
    }

    private List<String> safeList(List<String> list) {
        return list != null ? list : new ArrayList<>();
    }
}
