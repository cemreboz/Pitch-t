package use_case.dashboard_show_pitch;

import entity.Pitch;

/**
 * Output data for the Dashboard use case.
 */
public class DashboardOutputData {

    private final String username;
    private final String password;
    private final Pitch pitch;
    private final boolean useCaseFailed;

    public DashboardOutputData(Pitch pitch, boolean useCaseFailed, String username, String password) {
        this.pitch = pitch;
        this.useCaseFailed = useCaseFailed;
        this.username = username;
        this.password = password;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
