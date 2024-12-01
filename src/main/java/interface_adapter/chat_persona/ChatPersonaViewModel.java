package interface_adapter.chat_persona;

import interface_adapter.ViewModel;

/**
 * ViewModel for the chat_persona use case.
 */
public class ChatPersonaViewModel extends ViewModel<ChatPersonaState> {
    public ChatPersonaViewModel() {
        super("chat persona");
        setState(new ChatPersonaState());
    }
}
