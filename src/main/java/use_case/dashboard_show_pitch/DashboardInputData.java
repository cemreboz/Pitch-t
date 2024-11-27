package use_case.dashboard_show_pitch;

import entity.Pitch;

/**
 * The input data for the Dashboard use case.
 */
public class DashboardInputData {

    private final String username;
    private final String password;
    private final Pitch pitch;

    public DashboardInputData(Pitch pitch, String username, String password) {
        this.pitch = pitch;
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
}
