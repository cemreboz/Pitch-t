package interface_adapter.view_personas;

import entity.Pitch;
import use_case.view_personas.ViewPersonasInputBoundary;
import use_case.view_personas.ViewPersonasInputData;

/**
 * The controller for the View Personas Use Case.
 */
public class ViewPersonasController {
    private final ViewPersonasInputBoundary viewPersonasInteractor;

    public ViewPersonasController(ViewPersonasInputBoundary viewPersonasInteractor) {
        this.viewPersonasInteractor = viewPersonasInteractor;
    }

    /**
     * The execute method.
     * @param pitch the pitch used to view personas.
     */
    public void execute(Pitch pitch) {
        final ViewPersonasInputData inputData = new ViewPersonasInputData(pitch);
        viewPersonasInteractor.execute(inputData);
    }
}
