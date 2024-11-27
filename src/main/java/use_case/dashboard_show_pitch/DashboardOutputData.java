package use_case.dashboard_show_pitch;

import entity.Pitch;

/**
 * Output data for the Dashboard use case.
 */
public class DashboardOutputData {

    private final Pitch pitch;
    private final boolean useCaseFailed;

    public DashboardOutputData(Pitch pitch, boolean useCaseFailed) {
        this.pitch = pitch;
        this.useCaseFailed = useCaseFailed;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
