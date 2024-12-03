package interface_adapter.view_personas;

import entity.Pitch;
import use_case.view_personas.ViewPersonasInputBoundary;
import use_case.view_personas.ViewPersonasInputData;
import use_case.view_personas.ViewPersonasInteractor;

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
     */
    public void execute(Pitch pitch) {
        final ViewPersonasInputData inputData = new ViewPersonasInputData(pitch);
        viewPersonasInteractor.execute(inputData);
    }
}
