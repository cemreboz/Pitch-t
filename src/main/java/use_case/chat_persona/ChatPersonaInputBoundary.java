package use_case.chat_persona;

import use_case.chat_persona.ChatPersonaInputData;

import java.io.IOException;

/**
 * Input boundary for the chat_persona use case.
 */
public interface ChatPersonaInputBoundary {
    /**
     * Executes the chat interaction logic.
     * @param inputData The input data containing user message and context.
     */
    void execute(ChatPersonaInputData inputData) throws IOException, InterruptedException;
}
