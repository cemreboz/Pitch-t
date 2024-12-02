package view;

import entity.Persona;
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
    private final String viewname = "personas list";

    private final ComparePersonasViewModel compareViewModel;
    private final ViewPersonasViewModel viewModel;

    private final JPanel personasPanel = new JPanel();
    private final JButton compareButton;
    private final JButton visionButton;
    private final JButton chatButton;
    private List<JCheckBox> personaCheckBoxes = new ArrayList<JCheckBox>();

    public PersonaListView(ViewPersonasViewModel viewModel,
                           ComparePersonasViewModel compareViewModel) {
        System.out.println("TESTING CONSTRUCTOR");

        this.viewModel = viewModel;
        this.compareViewModel = compareViewModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Select Personas to Compare, Chat, or Generate Vision");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        personasPanel.setLayout(new BoxLayout(personasPanel, BoxLayout.Y_AXIS));
        personasPanel.setBackground(Color.WHITE);
        personasPanel.setMaximumSize(new Dimension(1000, 1000));

        compareButton = new JButton("Compare Selected Personas");
        compareButton.setAlignmentX(CENTER_ALIGNMENT);
        add(compareButton);

        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCompareButton();
            }
        });

        visionButton = new JButton("Generate Vision");
        visionButton.setAlignmentX(CENTER_ALIGNMENT);
        add(visionButton);

        visionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleVisionButton();
            }
        });

        chatButton = new JButton("Chat with Persona");
        chatButton.setAlignmentX(CENTER_ALIGNMENT);
        add(chatButton);

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChatButton();
            }
        });
    }

    private void handleCompareButton() {
        // Collect selected personas
        Persona[] selectedPersonas = new Persona[2];
        int count = 0;
        for (int i = 0; i < personaCheckBoxes.size(); i++) {
            if (personaCheckBoxes.get(i).isSelected()) {
                selectedPersonas[count++] = viewModel.getState().getPersonas().get(i);
                if (count == 2) break;
            }
        }

        // Ensure exactly two personas are selected
        if (count < 2) {
            JOptionPane.showMessageDialog(this, "Please select exactly two personas to compare.");
            return;
        }

        // Trigger comparison
        ComparePersonasInputData inputData = new ComparePersonasInputData(selectedPersonas[0], selectedPersonas[1]);
        // TODO
    }

    private void handleVisionButton() {
        // Collect the selected persona
        Persona selectedPersona = null;
        for (int i = 0; i < personaCheckBoxes.size(); i++) {
            if (personaCheckBoxes.get(i).isSelected()) {
                selectedPersona = viewModel.getState().getPersonas().get(i);
                break;
            }
        }

        if (selectedPersona == null) {
            JOptionPane.showMessageDialog(this, "Please select a persona to generate a vision.");
            return;
        }

        // TODO: Implement the vision generation feature
        JOptionPane.showMessageDialog(this, "Vision generation for " + selectedPersona.getName() + " is not yet implemented.");
    }

    private void handleChatButton() {
        // Collect the selected persona
        Persona selectedPersona = null;
        for (int i = 0; i < personaCheckBoxes.size(); i++) {
            if (personaCheckBoxes.get(i).isSelected()) {
                selectedPersona = viewModel.getState().getPersonas().get(i);
                break;
            }
        }

        if (selectedPersona == null) {
            JOptionPane.showMessageDialog(this, "Please select a persona to chat with.");
            return;
        }

        // TODO: Implement the chat feature
        JOptionPane.showMessageDialog(this, "Chat with " + selectedPersona.getName() + " is not yet implemented.");
    }

    public void setComparePersonasController(ComparePersonasController comparePersonasController) {
        // Set the compare personas controller
    }

    public String getViewName() {
        return viewname;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            var state = (ViewPersonasState) evt.getNewValue();

            // Update the list of personas
            personasPanel.removeAll();




            // TODO: Print the personas list length and see if it's getting the right data from the state


            for (int i = 0; i < state.getPersonas().size(); i++) {
                var checkbox = new JCheckBox(state.getPersonas().get(i).getName());
                personaCheckBoxes.add(checkbox);
                personasPanel.add(checkbox);
            }

            revalidate();
            repaint();
        }
    }
}