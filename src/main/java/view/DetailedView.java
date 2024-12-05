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
import java.util.Objects;

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
    private final String comma = ", ";
    private final String yes = "Yes";
    private final String no = "No";
    private final int fourteen = 14;

    private final JPanel contentPanel;

    private final int twentyfour = 24;
    private final int five = 5;
    private final int ten = 10;
    private final int sixteen = 16;
    private final double pointthree = 0.3;
    private final double pointseven = 0.7;

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

    private void updateDetailed(DetailedTargetAudience detailed) {
        this.detailedTargetAudience = detailed;
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
        final JLabel noDataLabel = new JLabel("No detailed target audience data available.",
                SwingConstants.CENTER);
        noDataLabel.setFont(new Font(font, Font.PLAIN, sixteen));
        contentPanel.add(noDataLabel);
    }

    private void addAttributesPanel(String title, List<String[]> attributes) {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(title));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(five, ten, five, ten);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = pointthree;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (String[] attr : attributes) {
            if (attr == null || attr.length < 2) {
                continue;
            }

            final JLabel label = new JLabel(attr[0] + ": ");
            label.setFont(new Font(font, Font.BOLD, fourteen));
            panel.add(label, gbc);

            gbc.gridx++;
            gbc.weightx = pointseven;

            final JLabel value = new JLabel(attr[1]);
            value.setFont(new Font(font, Font.PLAIN, fourteen));
            panel.add(value, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.weightx = pointthree;
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
                    new String[]{"Age Range", detailedTargetAudience.getMinAge() + " - "
                            + detailedTargetAudience.getMaxAge()},
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
        final List<String[]> attributes;
        if (detailedTargetAudience == null) {
            attributes = new ArrayList<>();
        }
        else {
            attributes = List.of(
                    new String[]{"Interests and Passions", String.join(comma,
                            safeList(detailedTargetAudience.getInterestsAndPassions()))},
                    new String[]{"Values", String.join(comma, safeList(detailedTargetAudience.getValues()))},
                    new String[]{"Personality Traits", String.join(comma, safeList(
                            detailedTargetAudience.getPersonalityTraits()))},
                    new String[]{"Lifestyle", safeString(detailedTargetAudience.getLifestyle())}
            );
        }
        return attributes;
    }

    private List<String[]> getBehavioralAttributes() {
        final List<String[]> attributes;
        if (detailedTargetAudience == null) {
            attributes = new ArrayList<>();
        }
        else {
            attributes = new ArrayList<>();

            final String earlyAdopterValue;
            if (detailedTargetAudience.isEarlyAdopter()) {
                earlyAdopterValue = yes;
            }
            else {
                earlyAdopterValue = no;
            }
            attributes.add(new String[]{"Early Adopter", earlyAdopterValue});

            attributes.add(new String[]{"Tech Savviness", safeString(detailedTargetAudience.getTechSavviness())});
            attributes.add(new String[]{"Gadget Ownership", String.join(comma, safeList(
                    detailedTargetAudience.getGadgetOwnership()))});
            attributes.add(new String[]{"Media Consumption", String.join(comma, safeList(
                    detailedTargetAudience.getMediaConsumption()))});
            attributes.add(new String[]{"Online Engagement", String.join(comma, safeList(
                    detailedTargetAudience.getOnlineEngagement()))});

            final String influencerValue;
            if (detailedTargetAudience.isInfluencer()) {
                influencerValue = yes;
            }
            else {
                influencerValue = no;
            }
            attributes.add(new String[]{"Influencer", influencerValue});
        }
        return attributes;
    }

    private List<String[]> getOtherAttributes() {
        final List<String[]> attributes;
        if (detailedTargetAudience == null) {
            attributes = new ArrayList<>();
        }
        else {
            attributes = new ArrayList<>();

            attributes.add(new String[]{"Event Participation", String.join(comma, safeList(
                    detailedTargetAudience.getEventParticipation()))});
            attributes.add(new String[]{"Hobbies", String.join(comma, safeList(detailedTargetAudience.getHobbies()))});
            attributes.add(new String[]{"Brand Affinity", String.join(comma, safeList(
                    detailedTargetAudience.getBrandAffinity()))});

            final String environmentalConcernsValue;
            if (detailedTargetAudience.isEnvironmentalConcerns()) {
                environmentalConcernsValue = yes;
            }
            else {
                environmentalConcernsValue = no;
            }
            attributes.add(new String[]{"Environmental Concerns", environmentalConcernsValue});

            final String globalPerspectiveValue;
            if (detailedTargetAudience.isGlobalPerspective()) {
                globalPerspectiveValue = yes;
            }
            else {
                globalPerspectiveValue = no;
            }
            attributes.add(new String[]{"Global Perspective", globalPerspectiveValue});

            final String multilingualAbilitiesValue;
            if (detailedTargetAudience.isMultilingualAbilities()) {
                multilingualAbilitiesValue = yes;
            }
            else {
                multilingualAbilitiesValue = no;
            }
            attributes.add(new String[]{"Multilingual Abilities", multilingualAbilitiesValue});
        }
        return attributes;
    }

    private String safeString(String value) {
        final String valuable;
        if (value != null && !value.isEmpty()) {
            valuable = value;
        }
        else {
            valuable = "N/A";
        }
        return valuable;
    }

    private List<String> safeList(List<String> list) {
        return Objects.requireNonNullElseGet(list, List::of);
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(DetailedController controller) {
        this.controller = controller;
    }
}
