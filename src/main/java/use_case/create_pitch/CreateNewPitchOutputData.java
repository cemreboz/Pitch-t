package use_case.create_pitch;

import entity.Pitch;

public class CreateNewPitchOutputData {

    private final boolean useCaseFailed;
    private final Pitch newPitch;
    private final String username;
    private final String password;

    public CreateNewPitchOutputData(boolean useCaseFailed, Pitch newPitch, String username, String password) {
        this.useCaseFailed = useCaseFailed;
        this.newPitch = newPitch;
        this.username = username;
        this.password = password;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public Pitch getNewPitch() {
        return newPitch;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
