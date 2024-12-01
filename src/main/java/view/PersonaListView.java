package view;

import entity.Persona;
import interface_adapter.compare_personas.ComparePersonasController;
import interface_adapter.compare_personas.ComparePersonasViewModel;
import interface_adapter.chat_with_persona.ChatController;
import interface_adapter.vision.VisionController;
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
//    private final ChatController chatController;
//    private final VisionController visionController;
    private final List<Persona> personas;
    private final JButton compareButton;
    private final JButton visionButton;
    private final JButton chatButton;
    private final JCheckBox[] personaCheckBoxes;

    public PersonaListView(List<Persona> personas, ComparePersonasController compareController, ComparePersonasViewModel compareViewModel, ChatController chatController, VisionController visionController) {
        this.personas = personas;
        this.compareController = compareController;
        this.compareViewModel = compareViewModel;
//        this.chatController = chatController;
//        this.visionController = visionController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Select Personas to Compare, Chat, or View Vision");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        personaCheckBoxes = new JCheckBox[personas.size()];
        for (int i = 0; i < personas.size(); i++) {
            personaCheckBoxes[i] = new JCheckBox(personas.get(i).getName());
            add(personaCheckBoxes[i]);
        }

        // Compare Button
        compareButton = new JButton("Compare Selected Personas");
        compareButton.setAlignmentX(CENTER_ALIGNMENT);
        add(compareButton);
        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCompareButton();
            }
        });

        // Vision Button
        visionButton = new JButton("Generate Vision");
        visionButton.setAlignmentX(CENTER_ALIGNMENT);
        add(visionButton);
        visionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleVisionButton();
            }
        });

        // Chat Button
        chatButton = new JButton("Chat with Selected Persona");
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

        // Trigger vision generation
//        visionController.generateVision(selectedPersona);
    }

    private void handleChatButton() {
        // Collect the selected persona
        Persona selectedPersona = null;
        for (int i = 0; i < personaCheckBoxes.length; i++) {
            if (personaCheckBoxes[i].isSelected()) {
                selectedPersona = personas.get(i);
                break;
            }
        }

        if (selectedPersona == null) {
            JOptionPane.showMessageDialog(this, "Please select a persona to chat with.");
            return;
        }

        // Trigger chat with the selected persona
//        chatController.initiateChat(selectedPersona);
    }
}