package use_case.persona;

/**
 * The persona interactor, for showing persona chats.
 */
public class PersonaInteractor implements PersonaInputBoundary {
    private PersonaDataAccessInterface userDataAccessObject;
    private PersonaOutputBoundary personaPresenter;

    public PersonaInteractor(PersonaDataAccessInterface userDataAccessObject, PersonaOutputBoundary personaPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.personaPresenter = personaPresenter;
    }

    @Override
    public void execute(PersonaInputData personaInputData) {
        final PersonaOutputData personaOutputData = new PersonaOutputData(false, personaInputData.getPersona(),
                personaInputData.getPitch(), personaInputData.getUsername(), personaInputData.getPassword());
        personaPresenter.prepareSuccessView(personaOutputData);
    }
}
