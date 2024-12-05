package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import entity.DetailedTargetAudience;
import interface_adapter.targetaudience.DetailedController;
import interface_adapter.targetaudience.DetailedTargetAudiencePageViewModel;
import interface_adapter.targetaudience.DetailedTargetAudienceState;

/**
 * The view for displaying detailed target audience information.
 */
public class DetailedView extends JPanel implements PropertyChangeListener {

    private final String viewName = "Detailed Target Audience";
    private final DetailedTargetAudiencePageViewModel viewModel;
    private DetailedTargetAudience detailedTargetAudience;
    private DetailedController controller;
    private final String font = "SansSerif";
    private final int fourteen = 14;

    private final JPanel contentPanel;

    private final int twentyfour = 24;

    public DetailedView(DetailedTargetAudiencePageViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title Label
        final JLabel titleLabel = new JLabel("Detailed Target Audience", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, twentyfour));
        add(titleLabel, BorderLayout.NORTH);

        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        final JScrollPane contentScrollPane = new JScrollPane(contentPanel);
        add(contentScrollPane, BorderLayout.CENTER);

        // Populate with default content initially
        showNoDataMessage();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {

            final DetailedTargetAudienceState updatedState = viewModel.getState();
            final List<DetailedTargetAudience> detailedAudienceList = updatedState.getDetailedTargetAudience();

            if (detailedAudienceList == null || detailedAudienceList.isEmpty()) {
                updateDetailed(null);
            }
            else {
                final DetailedTargetAudience updatedTargetAudience = detailedAudienceList.get(0);
                updateDetailed(updatedTargetAudience);
            }
        }
    }

    private void updateDetailed(DetailedTargetAudience detailedTargetAudience) {
        this.detailedTargetAudience = detailedTargetAudience;
        populateContent();
    }

    private void populateContent() {
        SwingUtilities.invokeLater(() -> {
            contentPanel.removeAll();
            if (detailedTargetAudience == null) {
                showNoDataMessage();
            }
            else {
                addAttributesPanel("Demographic Attributes", getDemographicAttributes());
                addAttributesPanel("Psychographic Attributes", getPsychographicAttributes());
                addAttributesPanel("Behavioral Attributes", getBehavioralAttributes());
                addAttributesPanel("Other Attributes", getOtherAttributes());
            }
            contentPanel.revalidate();
            contentPanel.repaint();
        });
    }

    private void showNoDataMessage() {
        JLabel noDataLabel = new JLabel("No detailed target audience data available.", SwingConstants.CENTER);
        noDataLabel.setFont(new Font(font, Font.PLAIN, 16));
        contentPanel.add(noDataLabel);
    }

    private void addAttributesPanel(String title, List<String[]> attributes) {
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
                continue;
            }

            final JLabel label = new JLabel(attr[0] + ": ");
            label.setFont(new Font(font, Font.BOLD, fourteen));
            panel.add(label, gbc);

            gbc.gridx++;
            gbc.weightx = 0.7;

            JLabel value = new JLabel(attr[1]);
            value.setFont(new Font(font, Font.PLAIN, fourteen));
            panel.add(value, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.weightx = 0.3;
        }

        contentPanel.add(panel);
    }

    private List<String[]> getDemographicAttributes() {
        final List<String[]> attributes;
        if (detailedTargetAudience == null) {
            attributes = new ArrayList<>();
        }
        else {
            attributes = List.of(
                    new String[]{"Age Range", detailedTargetAudience.getMinAge() + " - " + detailedTargetAudience.getMaxAge()},
                    new String[]{"Gender", safeString(detailedTargetAudience.getGender())},
                    new String[]{"Education Level", safeString(detailedTargetAudience.getEducationLevel())},
                    new String[]{"Occupation", safeString(detailedTargetAudience.getOccupation())},
                    new String[]{"Income Level", safeString(detailedTargetAudience.getIncomeLevel())},
                    new String[]{"Geographic Location", safeString(detailedTargetAudience.getGeographicLocation())}
            );
        }
        return attributes;
    }

    private List<String[]> getPsychographicAttributes() {
        if (detailedTargetAudience == null) return List.of();

        return List.of(
                new String[]{"Interests and Passions", String.join(", ", safeList(detailedTargetAudience.getInterestsAndPassions()))},
                new String[]{"Values", String.join(", ", safeList(detailedTargetAudience.getValues()))},
                new String[]{"Personality Traits", String.join(", ", safeList(detailedTargetAudience.getPersonalityTraits()))},
                new String[]{"Lifestyle", safeString(detailedTargetAudience.getLifestyle())}
        );
    }

    private List<String[]> getBehavioralAttributes() {
        if (detailedTargetAudience == null) return List.of();

        return List.of(
                new String[]{"Early Adopter", detailedTargetAudience.isEarlyAdopter() ? "Yes" : "No"},
                new String[]{"Tech Savviness", safeString(detailedTargetAudience.getTechSavviness())},
                new String[]{"Gadget Ownership", String.join(", ", safeList(detailedTargetAudience.getGadgetOwnership()))},
                new String[]{"Media Consumption", String.join(", ", safeList(detailedTargetAudience.getMediaConsumption()))},
                new String[]{"Online Engagement", String.join(", ", safeList(detailedTargetAudience.getOnlineEngagement()))},
                new String[]{"Influencer", detailedTargetAudience.isInfluencer() ? "Yes" : "No"}
        );
    }

    private List<String[]> getOtherAttributes() {
        if (detailedTargetAudience == null) return List.of();

        return List.of(
                new String[]{"Event Participation", String.join(", ", safeList(detailedTargetAudience.getEventParticipation()))},
                new String[]{"Hobbies", String.join(", ", safeList(detailedTargetAudience.getHobbies()))},
                new String[]{"Brand Affinity", String.join(", ", safeList(detailedTargetAudience.getBrandAffinity()))},
                new String[]{"Environmental Concerns", detailedTargetAudience.isEnvironmentalConcerns() ? "Yes" : "No"},
                new String[]{"Global Perspective", detailedTargetAudience.isGlobalPerspective() ? "Yes" : "No"},
                new String[]{"Multilingual Abilities", detailedTargetAudience.isMultilingualAbilities() ? "Yes" : "No"}
        );
    }

    private String safeString(String value) {
        return value != null && !value.isEmpty() ? value : "N/A";
    }

    private List<String> safeList(List<String> list) {
        return list != null ? list : List.of();
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(DetailedController controller) {
        this.controller = controller;
    }
}
