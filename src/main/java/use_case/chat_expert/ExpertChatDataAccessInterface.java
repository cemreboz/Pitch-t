package use_case.chat_expert;

import java.util.List;

import entity.ChatMessage;

/**
 * The Interface for accessing the chatgptDAO within the expert chat usecase.
 */
public interface ExpertChatDataAccessInterface {

    /**
     * Makes an API call to retrieve data for the experts chat?.
     *
     * @param messages the list of chat message objects associated with that detailed target audience.
     * @return The API response content as a string.
     * @throws RuntimeException If any issue occurs during the API call.
     */
    String utilizeApi(List<ChatMessage> messages);
}
