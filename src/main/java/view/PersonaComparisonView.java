package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.compare_personas.ComparePersonasState;
import interface_adapter.compare_personas.ComparePersonasViewModel;

/**
 * View for comparing two personas.
 */
public class PersonaComparisonView extends JPanel implements PropertyChangeListener {

    private final ComparePersonasViewModel compareViewModel;

    private JLabel persona1NameLabel;
    private JTextArea persona1OpinionArea;

    private JLabel persona2NameLabel;
    private JTextArea persona2OpinionArea;

    private JTextArea similaritiesArea;
    private JTextArea differencesArea;

    public PersonaComparisonView(ComparePersonasViewModel viewModel) {
        this.compareViewModel = viewModel;
        this.compareViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Panel for persona information
        JPanel personasPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Persona 1 panel
        JPanel persona1Panel = new JPanel();
        persona1Panel.setLayout(new BoxLayout(persona1Panel, BoxLayout.Y_AXIS));
        persona1NameLabel = new JLabel();
        persona1OpinionArea = new JTextArea(5, 20);
        persona1OpinionArea.setWrapStyleWord(true);
        persona1OpinionArea.setLineWrap(true);
        persona1OpinionArea.setEditable(false);

        persona1Panel.add(persona1NameLabel);
        persona1Panel.add(new JLabel("Opinion:"));
        persona1Panel.add(new JScrollPane(persona1OpinionArea));

        // Persona 2 panel
        JPanel persona2Panel = new JPanel();
        persona2Panel.setLayout(new BoxLayout(persona2Panel, BoxLayout.Y_AXIS));
        persona2NameLabel = new JLabel();
        persona2OpinionArea = new JTextArea(5, 20);
        persona2OpinionArea.setWrapStyleWord(true);
        persona2OpinionArea.setLineWrap(true);
        persona2OpinionArea.setEditable(false);

        persona2Panel.add(persona2NameLabel);
        persona2Panel.add(new JLabel("Opinion:"));
        persona2Panel.add(new JScrollPane(persona2OpinionArea));

        personasPanel.add(persona1Panel);
        personasPanel.add(persona2Panel);

        add(personasPanel, BorderLayout.CENTER);

        // Panel for similarities and differences
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

            if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update persona 1
            persona1NameLabel.setText(state.getPersona1().getName());
            persona1OpinionArea.setText(state.getPersona1Opinion());

            // Update persona 2
            persona2NameLabel.setText(state.getPersona2().getName());
            persona2OpinionArea.setText(state.getPersona2Opinion());

            // Update similarities and differences
            similaritiesArea.setText(String.join("\n", state.getSimilarities()));
            differencesArea.setText(String.join("\n", state.getDifferences()));
        }
    }

    public String getViewName() {
        return "persona comparison";
    }
}
