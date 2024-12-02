package interface_adapter.persona;

import interface_adapter.ViewModel;

/**
 * View Model for Persona chat.
 */
public class PersonaViewModel extends ViewModel<PersonaState> {

    public PersonaViewModel() {
        super("chat persona");
        setState(new PersonaState());
    }
}
