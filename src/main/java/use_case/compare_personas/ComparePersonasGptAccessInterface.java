package data_access;

import entity.ChatMessage;
import java.io.IOException;
import java.util.List;

public interface ComparePersonasGptAccessInterface {
    String getInteraction(List<ChatMessage> messages) throws IOException, InterruptedException;
}