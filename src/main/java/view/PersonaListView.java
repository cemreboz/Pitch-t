package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import entity.Persona;
import entity.Pitch;
import interface_adapter.compare_personas.ComparePersonasController;
import interface_adapter.compare_personas.ComparePersonasViewModel;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.persona.PersonaController;
import interface_adapter.view_personas.ViewPersonasState;
import interface_adapter.view_personas.ViewPersonasViewModel;
import use_case.compare_personas.ComparePersonasInputData;

/**
 * The View for listing personas and comparing them.
 */
public class PersonaListView extends JPanel implements PropertyChangeListener {
    public static final int FONT_SIZE18 = 18;
    private final String viewName = "personas list";

    private final ComparePersonasViewModel compareViewModel;
    private final ViewPersonasViewModel viewModel;

    private final JPanel personasPanel = new JPanel();
    private final JButton compareButton;
    private final JButton visionButton;
    private final JButton chatButton;
    private final JButton backButton;
    private final List<JCheckBox> personaCheckBoxes = new ArrayList<>();
    private PersonaController personaController;
    private DashboardController dashboardController;

    private ComparePersonasController comparePersonasController;

    public PersonaListView(ViewPersonasViewModel viewModel,
                           ComparePersonasViewModel compareViewModel) {
        this.viewModel = viewModel;
        this.compareViewModel = compareViewModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Title Label
        final JLabel titleLabel = new JLabel("Select Personas to Compare, Chat, or Generate Vision");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE18));
        add(titleLabel, BorderLayout.NORTH);

        // Personas Panel
        personasPanel.setLayout(new BoxLayout(personasPanel, BoxLayout.Y_AXIS));
        personasPanel.setBackground(Color.WHITE);
        final JScrollPane scrollPane = new JScrollPane(personasPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        backButton = new JButton("Back");
        buttonsPanel.add(backButton);

        compareButton = new JButton("Compare Selected Personas");
        buttonsPanel.add(compareButton);

        visionButton = new JButton("Generate Vision");
        buttonsPanel.add(visionButton);

        chatButton = new JButton("Chat with Persona");
        buttonsPanel.add(chatButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Action Listeners
        compareButton.addActionListener(evt -> handleCompareButton());
        visionButton.addActionListener(evt -> handleVisionButton());
        chatButton.addActionListener(evt -> handleChatButton());
        backButton.addActionListener(evt -> handleBackButton());
    }

    private void handleBackButton() {
        final Pitch pitch = viewModel.getState().getThisPitch();
        final String username = viewModel.getState().getUsername();
        final String password = viewModel.getState().getPassword();
        dashboardController.execute(pitch, username, password);
    }

    private void handleCompareButton() {
        // Collect selected personas
        final List<Persona> selectedPersonas = getSelectedPersonas();

        // Ensure exactly two personas are selected
        if (selectedPersonas.size() != 2) {
            JOptionPane.showMessageDialog(this,
                    "Please select exactly two personas to compare.");
            return;
        }

        // Trigger comparison using the comparePersonasController
        final Pitch pitch = viewModel.getState().getThisPitch();
        final ComparePersonasInputData inputData = new ComparePersonasInputData(selectedPersonas.get(0),
                selectedPersonas.get(1), pitch);
        comparePersonasController.comparePersonas(inputData);
    }

    public void setPersonaController(PersonaController personaController) {
        this.personaController = personaController;
    }

    private void handleVisionButton() {
        // Collect the selected persona
        final List<Persona> selectedPersonas = getSelectedPersonas();

        if (selectedPersonas.size() != 1) {
            JOptionPane.showMessageDialog(
                    this, "Please select exactly one persona to generate a vision.");
            return;
        }

        // TODO: Implement the vision generation feature
        JOptionPane.showMessageDialog(
                this, "Vision generation for "
                        + selectedPersonas.get(0).getName() + " is not yet implemented.");
    }

    private void handleChatButton() {
        // Collect the selected persona
        final List<Persona> selectedPersonas = getSelectedPersonas();

        if (selectedPersonas.size() != 1) {
            JOptionPane.showMessageDialog(
                    this, "Please select exactly one persona to chat with.");
            return;
        }

        final Pitch pitch = viewModel.getState().getThisPitch();
        personaController.execute(selectedPersonas.get(0), pitch,
                viewModel.getState().getUsername(), viewModel.getState().getPassword());
    }

    /**
     * Collects the personas that are currently selected via checkboxes.
     *
     * @return a list of selected personas.
     */
    private List<Persona> getSelectedPersonas() {
        final List<Persona> selectedPersonas = new ArrayList<>();
        final List<Persona> personas = viewModel.getState().getPersonas();

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

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            final ViewPersonasState state = (ViewPersonasState) evt.getNewValue();

            // Update the list of personas
            personasPanel.removeAll();
            personaCheckBoxes.clear();

            final List<Persona> personas = state.getPersonas();
            for (Persona persona : personas) {
                final JCheckBox checkbox = new JCheckBox(persona.getName());
                personaCheckBoxes.add(checkbox);
                personasPanel.add(checkbox);
            }

            personasPanel.revalidate();
            personasPanel.repaint();
        }
    }
}
