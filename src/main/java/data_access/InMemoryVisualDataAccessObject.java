package data_access;

import java.util.ArrayList;
import java.util.List;

import entity.Visual;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryVisualDataAccessObject implements VisualDataAccessObject {
    private final List<Visual> visuals = new ArrayList<>();

    @Override
    public void saveVisual(Visual visual) {
        visuals.add(visual);
    }
}
