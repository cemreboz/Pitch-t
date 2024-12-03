package use_case.chat_persona;

import java.util.List;

import entity.ChatMessage;

/**
 * Data access interface for retrieving persona chat data.
 */
public interface ChatPersonaDataAccessInterface {

    /**
     * Makes an API call to retrieve data for the experts chat?.
     *
     * @param messages the list of chat message objects associated with that detailed target audience.
     * @return The API response content as a string.
     * @throws RuntimeException If any issue occurs during the API call.
     */
    String utilizeApi(List<ChatMessage> messages);
}
