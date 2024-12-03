package use_case.chat_vision;

import use_case.chat_persona.ChatPersonaInputData;

/**
 * Input boundary for the chat persona use case.
 */
public interface ChatVisionInputBoundary {

    /**
     * Initiates a chat with the persona.
     *
     * @param inputData The input data containing user message and context.
     */
    void initiateChat(ChatVisionInputData inputData);
}