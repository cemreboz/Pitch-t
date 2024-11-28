package interface_adapter.chat_expert;

import interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * View model for the Chat with Expert use case.
 * Holds data for display in the view layer.
 */
public class ChatExpertViewModel extends ViewModel<ChatExpertState> {

    public ChatExpertViewModel() {
        super("chat expert");
        setState(new ChatExpertState());
    }
}
