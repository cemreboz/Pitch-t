package interface_adapter.compare_personas;

import interface_adapter.ViewModel;

/**
 * The View Model for the Dashboard view.
 */
public class PersonaComparisonViewModel extends ViewModel<PersonaComparisonState> {

    public PersonaComparisonViewModel() {
        super("dashboard");
        setState(new PersonaComparisonState());
    }
}
