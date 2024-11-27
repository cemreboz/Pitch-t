package use_case.dashboard_show_pitch;

import entity.Pitch;

/**
 * The input data for the Dashboard use case.
 */
public class DashboardInputData {

    private final Pitch pitch;

    public DashboardInputData(Pitch pitch) {
        this.pitch = pitch;
    }

    public Pitch getPitch() {
        return pitch;
    }
}
