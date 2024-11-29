package use_case.expert;


/**
 * Output data for the Expert use case.
 */
public class ExpertOutputData {

    private final String username;
    private final String password;
    private final boolean useCaseFailed;

    public ExpertOutputData(boolean useCaseFailed, String username, String password) {
        this.useCaseFailed = useCaseFailed;
        this.username = username;
        this.password = password;
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
