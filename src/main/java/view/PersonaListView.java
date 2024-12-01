package view;

import entity.Persona;
import interface_adapter.compare_personas.ComparePersonasController;
import interface_adapter.compare_personas.ComparePersonasViewModel;
import use_case.compare_personas.ComparePersonasInputData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The View for listing personas and comparing them.
 */
public class PersonaListView extends JPanel {
    private final ComparePersonasController compareController;
    private final ComparePersonasViewModel compareViewModel;
    private final List<Persona> personas;
    private final JButton compareButton;
    private final JButton visionButton;
    private final JCheckBox[] personaCheckBoxes;

    public PersonaListView(List<Persona> personas, ComparePersonasController compareController, ComparePersonasViewModel compareViewModel) {
        this.personas = personas;
        this.compareController = compareController;
        this.compareViewModel = compareViewModel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Select Two Personas to Compare");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        personaCheckBoxes = new JCheckBox[personas.size()];
        for (int i = 0; i < personas.size(); i++) {
            personaCheckBoxes[i] = new JCheckBox(personas.get(i).getName());
            add(personaCheckBoxes[i]);
        }

        compareButton = new JButton("Compare Selected Personas");
        compareButton.setAlignmentX(CENTER_ALIGNMENT);
        add(compareButton);

        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCompareButton();
            }
        });

        visionButton = new JButton("Go to Vision");
        visionButton.setAlignmentX(CENTER_ALIGNMENT);
        add(visionButton);

        visionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleVisionButton();
            }
        });
    }

    private void handleCompareButton() {
        // Collect selected personas
        Persona[] selectedPersonas = new Persona[2];
        int count = 0;
        for (int i = 0; i < personaCheckBoxes.length; i++) {
            if (personaCheckBoxes[i].isSelected()) {
                selectedPersonas[count++] = personas.get(i);
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
        compareController.compare(inputData);
    }

    private void handleVisionButton() {
        // Collect the selected persona
        Persona selectedPersona = null;
        for (int i = 0; i < personaCheckBoxes.length; i++) {
            if (personaCheckBoxes[i].isSelected()) {
                selectedPersona = personas.get(i);
                break;
            }
        }

        if (selectedPersona == null) {
            JOptionPane.showMessageDialog(this, "Please select a persona to generate a vision.");
            return;
        }

        VisionViewModel visionViewModel = new VisionViewModel();
        VisionController visionController = new VisionController(new GenerateVisualInteractor(
                new VisualDataAccessObject() {
                    @Override
                    public void saveVisual(Visual visual) {
                    }
                }, new ImageAnalyzer()));

        VisionView visionView = new VisionView(selectedPersona, null, visionController, visionViewModel);

        final JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(visionView);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    public void setComparePersonasController(ComparePersonasController comparePersonasController) {
        // Implement for the view personas list
    }
}
