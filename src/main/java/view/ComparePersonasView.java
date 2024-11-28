package view;

import interface_adapter.compare_personas.ComparePersonasViewModel;
import interface_adapter.compare_personas.ComparePersonasState;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Compare Personas Use Case.
 */
public class ComparePersonasView extends JPanel implements PropertyChangeListener {
    private final ComparePersonasViewModel viewModel;
    private final JTextArea comparisonResultArea;

    public ComparePersonasView(ComparePersonasViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Compare Personas Results");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        comparisonResultArea = new JTextArea(10, 30);
        comparisonResultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(comparisonResultArea);

        add(titleLabel);
        add(scrollPane);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            ComparePersonasState state = (ComparePersonasState) evt.getNewValue();
            comparisonResultArea.setText(state.getComparisonResult());
        }
    }

    public void updateView(ComparePersonasState state) {
        viewModel.setState(state);
    }
}
