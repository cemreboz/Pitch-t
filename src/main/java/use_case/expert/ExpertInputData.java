package use_case.expert;

/**
 * The input data for the Expert use case.
 */
public class ExpertInputData {
    private final String username;
    private final String password;

    public ExpertInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
