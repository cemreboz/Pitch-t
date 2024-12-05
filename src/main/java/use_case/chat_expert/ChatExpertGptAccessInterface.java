package use_case.chat_expert;

import java.io.IOException;
import java.util.List;

import entity.ChatMessage;

/**
 * The interface for the ChatGPT expert access.
 */
public interface ChatExpertGptAccessInterface {
    /**
     * Method to getting the API response for experts.
     * @param messages from before.
     * @return the API response.
     * @throws IOException if there is an error with getting the API output.
     * @throws InterruptedException if there is an error with getting the API response.
     */
    String getInteraction(List<ChatMessage> messages) throws IOException, InterruptedException;
}
