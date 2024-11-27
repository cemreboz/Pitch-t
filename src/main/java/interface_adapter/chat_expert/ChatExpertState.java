package interface_adapter.chat_expert;

/**
 * Represents the state for the ExpertChatView.
 */
public class ChatExpertState {
    private final String username;

    /**
     * Constructs a new ChatExpertState.
     *
     * @param username The username of the currently logged-in user.
     */
    public ChatExpertState(String username) {
        this.username = username;
    }

    /**
     * Gets the username associated with this state.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }
}
