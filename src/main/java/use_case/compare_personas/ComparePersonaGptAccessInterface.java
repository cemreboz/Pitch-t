package data_access;

import entity.ChatMessage;
import java.io.IOException;
import java.util.List;

public interface ComparePersonaGptAccessInterface {
    String getOpinion(List<ChatMessage> messages) throws IOException, InterruptedException;
}