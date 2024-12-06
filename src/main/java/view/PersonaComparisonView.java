package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.compare_personas.ComparePersonasState;
import interface_adapter.compare_personas.ComparePersonasViewModel;
import interface_adapter.expert.ExpertController;
import interface_adapter.login.LoginController;
import interface_adapter.new_pitch.ShowNewPitchController;

/**
 * View for comparing two personas.
 */
public class PersonaComparisonView extends JPanel implements PropertyChangeListener {

    public static final int DEF_SIZE = 10;
    public static final int HGAP = 20;
    public static final int COLS = 2;
    public static final int ROWS = 1;
    public static final int FONT_SIZE = 16;
    public static final int COLUMNS = 25;
    public static final int ROWS_TEXT = 5;
    public static final int LARGE_COLUMNS = 50;
    public static final int LARGE_ROWS = 3;
    private final ComparePersonasViewModel compareViewModel;

    private JLabel persona1NameLabel;
    private JTextArea persona1OpinionArea;

    private JLabel persona2NameLabel;
    private JTextArea persona2OpinionArea;

    private JTextArea similaritiesArea;
    private JTextArea differencesArea;
    private HamburgerMenu hamburgerMenu;

    public PersonaComparisonView(ComparePersonasViewModel viewModel) {
        this.compareViewModel = viewModel;
        this.compareViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Hamburger Menu Panel
        final JPanel topPanel = new JPanel(new BorderLayout());
        hamburgerMenu = new HamburgerMenu(compareViewModel);
        topPanel.add(hamburgerMenu, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // Panel for persona information
        final JPanel personasPanel = new JPanel(new GridLayout(ROWS, COLS, HGAP, DEF_SIZE));
        personasPanel.setBorder(BorderFactory.createEmptyBorder(
                DEF_SIZE,
                DEF_SIZE,
                DEF_SIZE,
                DEF_SIZE)
        );

        // Persona 1 panel
        final JPanel persona1Panel = new JPanel();
        persona1Panel.setLayout(new BoxLayout(persona1Panel, BoxLayout.Y_AXIS));
        persona1Panel.setBorder(BorderFactory.createTitledBorder("Persona 1"));
        persona1NameLabel = new JLabel();
        persona1NameLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        persona1OpinionArea = new JTextArea(ROWS_TEXT, COLUMNS);
        persona1OpinionArea.setWrapStyleWord(true);
        persona1OpinionArea.setLineWrap(true);
        persona1OpinionArea.setEditable(false);

        persona1Panel.add(persona1NameLabel);
        persona1Panel.add(new JLabel("Opinion:"));
        persona1Panel.add(new JScrollPane(persona1OpinionArea));

        // Persona 2 panel
        final JPanel persona2Panel = new JPanel();
        persona2Panel.setLayout(new BoxLayout(persona2Panel, BoxLayout.Y_AXIS));
        persona2Panel.setBorder(BorderFactory.createTitledBorder("Persona 2"));
        persona2NameLabel = new JLabel();
        persona2NameLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        persona2OpinionArea = new JTextArea(ROWS_TEXT, COLUMNS);
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
        final JPanel comparisonPanel = new JPanel();
        comparisonPanel.setLayout(new BoxLayout(comparisonPanel, BoxLayout.Y_AXIS));
        comparisonPanel.setBorder(BorderFactory.createEmptyBorder(DEF_SIZE, DEF_SIZE, DEF_SIZE, DEF_SIZE));
        similaritiesArea = new JTextArea(LARGE_ROWS, LARGE_COLUMNS);
        similaritiesArea.setWrapStyleWord(true);
        similaritiesArea.setLineWrap(true);
        similaritiesArea.setEditable(false);

        differencesArea = new JTextArea(LARGE_ROWS, LARGE_COLUMNS);
        differencesArea.setWrapStyleWord(true);
        differencesArea.setLineWrap(true);
        differencesArea.setEditable(false);

        comparisonPanel.add(new JLabel("Similarities:"));
        comparisonPanel.add(new JScrollPane(similaritiesArea));
        comparisonPanel.add(Box.createRigidArea(new Dimension(0, DEF_SIZE)));
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

    /**
     * Method to set hamburger menu login controller.
     * @param loginController login controller
     */
    public void setLoginController(LoginController loginController) {
        hamburgerMenu.setLoginController(loginController);
    }

    /**
     * Method to set hamburger menu account settings controller.
     * @param accountSettingsController account settings.
     */
    public void setAccountSettingsController(AccountSettingsController accountSettingsController) {
        hamburgerMenu.setAccountSettingsController(accountSettingsController);
    }

    /**
     * Method to set hamburger menu expert controller.
     * @param expertController expert controller
     */
    public void setExpertController(ExpertController expertController) {
        hamburgerMenu.setExpertController(expertController);
    }

    /**
     * Method to set hamburger menu new pitch controller.
     * @param newPitchController new pitch controller
     */
    public void setNewPitchController(ShowNewPitchController newPitchController) {
        hamburgerMenu.setNewPitchController(newPitchController);
    }

    public String getViewName() {
        return "persona comparison";
    }
}
