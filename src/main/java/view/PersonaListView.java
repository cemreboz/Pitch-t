package view;

import entity.Persona;
import entity.Pitch;
import interface_adapter.compare_personas.ComparePersonasController;
import interface_adapter.compare_personas.ComparePersonasViewModel;
import interface_adapter.view_personas.ViewPersonasState;
import interface_adapter.view_personas.ViewPersonasViewModel;
import use_case.compare_personas.ComparePersonasInputData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for listing personas and comparing them.
 */
public class PersonaListView extends JPanel implements PropertyChangeListener {
    private final String viewName = "personas list";

    private final ComparePersonasViewModel compareViewModel;
    private final ViewPersonasViewModel viewModel;

    private final JPanel personasPanel = new JPanel();
    private final JButton compareButton;
    private final JButton visionButton;
    private final JButton chatButton;
    private final List<JCheckBox> personaCheckBoxes = new ArrayList<>();

    private ComparePersonasController comparePersonasController;

    public PersonaListView(ViewPersonasViewModel viewModel,
                           ComparePersonasViewModel compareViewModel) {
        this.viewModel = viewModel;
        this.compareViewModel = compareViewModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Select Personas to Compare, Chat, or Generate Vision");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Personas Panel
        personasPanel.setLayout(new BoxLayout(personasPanel, BoxLayout.Y_AXIS));
        personasPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(personasPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        compareButton = new JButton("Compare Selected Personas");
        buttonsPanel.add(compareButton);

        visionButton = new JButton("Generate Vision");
        buttonsPanel.add(visionButton);

        chatButton = new JButton("Chat with Persona");
        buttonsPanel.add(chatButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Action Listeners
        compareButton.addActionListener(e -> handleCompareButton());
        visionButton.addActionListener(e -> handleVisionButton());
        chatButton.addActionListener(e -> handleChatButton());
    }

    private void handleCompareButton() {
        // Collect selected personas
        List<Persona> selectedPersonas = getSelectedPersonas();

        // Ensure exactly two personas are selected
        if (selectedPersonas.size() != 2) {
            JOptionPane.showMessageDialog(this, "Please select exactly two personas to compare.");
            return;
        }

        // Trigger comparison using the comparePersonasController
        Pitch pitch = viewModel.getState().getThisPitch();
        ComparePersonasInputData inputData = new ComparePersonasInputData(selectedPersonas.get(0), selectedPersonas.get(1), pitch);
//        comparePersonasController.comparePersonas(inputData);
    }

    private void handleVisionButton() {
        // Collect the selected persona
        List<Persona> selectedPersonas = getSelectedPersonas();

        if (selectedPersonas.size() != 1) {
            JOptionPane.showMessageDialog(this, "Please select exactly one persona to generate a vision.");
            return;
        }

        // TODO: Implement the vision generation feature
        JOptionPane.showMessageDialog(this, "Vision generation for " + selectedPersonas.get(0).getName() + " is not yet implemented.");
    }

    private void handleChatButton() {
        // Collect the selected persona
        List<Persona> selectedPersonas = getSelectedPersonas();

        if (selectedPersonas.size() != 1) {
            JOptionPane.showMessageDialog(this, "Please select exactly one persona to chat with.");
            return;
        }

        // TODO: Implement the chat feature
        JOptionPane.showMessageDialog(this, "Chat with " + selectedPersonas.get(0).getName() + " is not yet implemented.");
    }

    /**
     * Collects the personas that are currently selected via checkboxes.
     *
     * @return a list of selected personas.
     */
    private List<Persona> getSelectedPersonas() {
        List<Persona> selectedPersonas = new ArrayList<>();
        List<Persona> personas = viewModel.getState().getPersonas();

        for (int i = 0; i < personaCheckBoxes.size(); i++) {
            if (personaCheckBoxes.get(i).isSelected()) {
                selectedPersonas.add(personas.get(i));
            }
        }
        return selectedPersonas;
    }

    public void setComparePersonasController(ComparePersonasController comparePersonasController) {
        this.comparePersonasController = comparePersonasController;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            ViewPersonasState state = (ViewPersonasState) evt.getNewValue();

            // Update the list of personas
            personasPanel.removeAll();
            personaCheckBoxes.clear();

            List<Persona> personas = state.getPersonas();
            for (Persona persona : personas) {
                JCheckBox checkbox = new JCheckBox(persona.getName());
                personaCheckBoxes.add(checkbox);
                personasPanel.add(checkbox);
            }

            personasPanel.revalidate();
            personasPanel.repaint();
        }
    }
}

//todo implement comparison