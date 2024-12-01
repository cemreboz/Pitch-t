package interface_adapter.persona;

import entity.Persona;
import use_case.persona.PersonaInputBoundary;
import use_case.persona.PersonaInputData;

/**
 * The controller for the persona (show chat) use case
 */
public class PersonaController {

    private final PersonaInputBoundary personaUseCaseInteractor;

    public PersonaController(PersonaInputBoundary personaInputBoundary) {
        this.personaUseCaseInteractor = personaInputBoundary;
    }

    /**
     * Executes the expert use case.
     * @param persona the persona the chat to
     * @param username username of user
     * @param password password of user
     */
    public void execute(Persona persona, String username, String password) {
        final PersonaInputData personaInputData = new PersonaInputData(persona, username, password);
        personaUseCaseInteractor.execute(personaInputData);
    }
}
