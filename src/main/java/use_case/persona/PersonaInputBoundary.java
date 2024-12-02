package use_case.persona;

/**
 * Input boundary for persona interactor.
 */
public interface PersonaInputBoundary {

    /**
     * Executes the persona show chat use case.
     * @param personaInputData the input dat
     * */
    void execute(PersonaInputData personaInputData);

}
