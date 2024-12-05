package interface_adapter.view_personas;

import interface_adapter.ViewModel;

/**
 * View Model for the View Personas page.
 */
public class ViewPersonasViewModel extends ViewModel<ViewPersonasState> {

    public ViewPersonasViewModel() {
        super("personas list");
        setState(new ViewPersonasState());
    }
}
