package use_case.chat_persona;

import entity.ChatMessage;

import java.io.IOException;
import java.util.List;

/**
 * Data access interface for retrieving expert data.
 */
public interface ChatPersonaGptAccessInterface {

    /**
     * Retrieves an expert by ID.
     *
     * @param messages The ID of the expert to retrieve.
     * @return The Expert object if found, null otherwise.
     */
    String utilizeApi(List<ChatMessage> messages) throws IOException, InterruptedException;
}
