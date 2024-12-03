package interface_adapter.compare_personas;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Compare Personas View.
 */
public class ComparePersonasViewModel extends ViewModel<ComparePersonasState> {

    public ComparePersonasViewModel() {
        super("persona comparison");
        setState(new ComparePersonasState());
    }
}