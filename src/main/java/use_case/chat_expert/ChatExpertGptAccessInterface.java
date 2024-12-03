package use_case.chat_expert;

import entity.ChatMessage;
import java.io.IOException;
import java.util.List;

public interface ChatExpertGptAccessInterface {
    String getInteraction(List<ChatMessage> messages) throws IOException, InterruptedException;
}
