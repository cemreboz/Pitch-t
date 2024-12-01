package interface_adapter.view_personas;

import entity.Persona;
import interface_adapter.ViewModel;

import java.util.List;

/**
 * The ViewModel for the View Personas page.
 */
public class ViewPersonasViewModel extends ViewModel<ViewPersonasState> {
    public ViewPersonasViewModel() {
        super("personas list");
        setState(null);
    }
}
