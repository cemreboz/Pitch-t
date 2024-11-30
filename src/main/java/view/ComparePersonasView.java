package view;

import interface_adapter.compare_personas.ComparePersonasState;
import interface_adapter.compare_personas.ComparePersonasViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ComparePersonasView extends JPanel implements PropertyChangeListener {
    private final ComparePersonasViewModel viewModel;
    private JLabel persona1NameLabel;
    private JTextArea persona1AboutArea;
    private JTextArea persona1OpinionArea;
    private JLabel persona2NameLabel;
    private JTextArea persona2AboutArea;
    private JTextArea persona2OpinionArea;
    private JTextArea similaritiesArea;
    private JTextArea differencesArea;

    public ComparePersonasView(ComparePersonasViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Panel for personas
        JPanel personasPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Persona 1 Panel
        JPanel persona1Panel = new JPanel();
        persona1Panel.setLayout(new BoxLayout(persona1Panel, BoxLayout.Y_AXIS));
        persona1NameLabel = new JLabel();
        persona1AboutArea = new JTextArea(3, 20);
        persona1AboutArea.setWrapStyleWord(true);
        persona1AboutArea.setLineWrap(true);
        persona1AboutArea.setEditable(false);
        persona1OpinionArea = new JTextArea(5, 20);
        persona1OpinionArea.setWrapStyleWord(true);
        persona1OpinionArea.setLineWrap(true);
        persona1OpinionArea.setEditable(false);

        persona1Panel.add(persona1NameLabel);
        persona1Panel.add(new JLabel("About:"));
        persona1Panel.add(persona1AboutArea);
        persona1Panel.add(new JLabel("Opinion:"));
        persona1Panel.add(persona1OpinionArea);

        // Persona 2 Panel
        JPanel persona2Panel = new JPanel();
        persona2Panel.setLayout(new BoxLayout(persona2Panel, BoxLayout.Y_AXIS));
        persona2NameLabel = new JLabel();
        persona2AboutArea = new JTextArea(3, 20);
        persona2AboutArea.setWrapStyleWord(true);
        persona2AboutArea.setLineWrap(true);
        persona2AboutArea.setEditable(false);
        persona2OpinionArea = new JTextArea(5, 20);
        persona2OpinionArea.setWrapStyleWord(true);
        persona2OpinionArea.setLineWrap(true);
        persona2OpinionArea.setEditable(false);

        persona2Panel.add(persona2NameLabel);
        persona2Panel.add(new JLabel("About:"));
        persona2Panel.add(persona2AboutArea);
        persona2Panel.add(new JLabel("Opinion:"));
        persona2Panel.add(persona2OpinionArea);

        personasPanel.add(persona1Panel);
        personasPanel.add(persona2Panel);

        add(personasPanel, BorderLayout.CENTER);

        // Similarities and Differences Panel
        JPanel comparisonPanel = new JPanel();
        comparisonPanel.setLayout(new BoxLayout(comparisonPanel, BoxLayout.Y_AXIS));
        similaritiesArea = new JTextArea(3, 40);
        similaritiesArea.setWrapStyleWord(true);
        similaritiesArea.setLineWrap(true);
        similaritiesArea.setEditable(false);
        differencesArea = new JTextArea(3, 40);
        differencesArea.setWrapStyleWord(true);
        differencesArea.setLineWrap(true);
        differencesArea.setEditable(false);

        comparisonPanel.add(new JLabel("Similarities:"));
        comparisonPanel.add(new JScrollPane(similaritiesArea));
        comparisonPanel.add(new JLabel("Differences:"));
        comparisonPanel.add(new JScrollPane(differencesArea));

        add(comparisonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            ComparePersonasState state = (ComparePersonasState) evt.getNewValue();

            if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
                // Display the error message in a dialog box
                JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update the UI with the personas' details
            persona1NameLabel.setText(state.getPersona1().getName());
            persona1AboutArea.setText(state.getPersona1().getAbout());
            persona1OpinionArea.setText(state.getPersona1Opinion());

            persona2NameLabel.setText(state.getPersona2().getName());
            persona2AboutArea.setText(state.getPersona2().getAbout());
            persona2OpinionArea.setText(state.getPersona2Opinion());

            // Set similarities and differences areas
            similaritiesArea.setText(String.join("\n", state.getSimilarities()));
            differencesArea.setText(String.join("\n", state.getDifferences()));
        }
    }
}