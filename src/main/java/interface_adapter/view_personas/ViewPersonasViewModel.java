package interface_adapter.view_personas;

import interface_adapter.ViewModel;

import java.util.ArrayList;

public class ViewPersonasViewModel extends ViewModel<ViewPersonasState> {
    public ViewPersonasViewModel() {
        super("personas list");

        // Initialize with an empty state to prevent null pointer issues
        ViewPersonasState initialState = new ViewPersonasState();
        initialState.setPersonas(new ArrayList<>()); // Setting an empty list initially
        setState(initialState);
    }
}
