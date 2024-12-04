package use_case.view_personas;

import entity.Persona;
import interface_adapter.view_personas.ViewPersonasViewModel;
import interface_adapter.view_personas.ViewPersonasState;
import org.junit.jupiter.api.Test;
import view.PersonaListView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonaListViewTest {
    /*
    class MockViewModel extends ViewPersonasViewModel {
        private ViewPersonasState state;

        @Override
        public ViewPersonasState getState() {
            return state;
        }

        @Override
        public void setState(ViewPersonasState newState) {
            this.state = newState;
            firePropertyChanged(); // Simulate notifying listeners
        }
    }

    @Test
    public void testDisplayPersonas() throws InterruptedException {
        // Set up personas
        List<Persona> personas = new ArrayList<>();
        Persona persona1 = new Persona();
        persona1.setName("John Doe");
        persona1.setAge(30);
        persona1.setOccupation("Engineer");
        personas.add(persona1);

        Persona persona2 = new Persona();
        persona2.setName("Jane Smith");
        persona2.setAge(28);
        persona2.setOccupation("Designer");
        personas.add(persona2);

        // Set up ViewModel and State
        MockViewModel mockViewModel = new MockViewModel();
        ViewPersonasState state = new ViewPersonasState();
        state.setPersonas(personas);
        mockViewModel.setState(state);

        // Set up View and Frame
        PersonaListView personaListView = new PersonaListView(mockViewModel, null, null);
        JFrame frame = new JFrame("Test PersonaListView");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(personaListView);
        frame.pack();
        frame.setVisible(true);

        // Visual Verification Delay
        Thread.sleep(50000); // Keep open for 5 seconds to verify

        // Assertions
        assertNotNull(personaListView.getViewName());
        assertEquals("Personas List", personaListView.getViewName());
        assertEquals(2, personaListView.getComponentCount());
    }

     */
}