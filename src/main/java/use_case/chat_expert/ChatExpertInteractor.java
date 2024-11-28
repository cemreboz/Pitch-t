package use_case.chat_expert;

import java.util.List;

import entity.Expert;

/**
 * Interactor class for the Chat with Expert use case.
 * Implements the business logic.
 */
public class ChatExpertInteractor implements ChatExpertInputBoundary {

    private final ChatExpertDataAccessInterface expertRepository;
    private final ChatExpertOutputBoundary outputBoundary;

    /**
     * Constructs a ChatExpertInteractor object.
     *
     * @param expertRepository The data access interface for experts.
     * @param outputBoundary   The output boundary interface.
     */
    public ChatExpertInteractor(
            ChatExpertDataAccessInterface expertRepository,
            ChatExpertOutputBoundary outputBoundary) {
        this.expertRepository = expertRepository;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Initiates a chat with an expert.
     *
     * @param inputData The input data containing user message
     *                  and expert ID.
     */
    @Override
    public void initiateChat(ChatExpertInputData inputData) {
        final Expert expert = expertRepository.getExpertById(
                inputData.getExpertId());

        if (expert == null) {
            // Handle expert not found scenario
            // For now, we can throw an exception or handle it gracefully
            throw new IllegalArgumentException("Expert not found.");
        }

        // Simulate expert response (to be replaced with GPT-4 API call)
        final String expertResponse = simulateExpertResponse(
                inputData.getUserMessage(), expert);

        // TODO This needs to load the chat history from the DAO currentUser object,
        //  experts stores with id 1 2 3 4 and chat histories string arrays

        // Update chat history
        expert.addChatMessage("User: " + inputData.getUserMessage());
        expert.addChatMessage("Expert: " + expertResponse);

        // Prepare output data
        final List<String> chatHistory = expert.getChatHistory();
        final ChatExpertOutputData outputData = new ChatExpertOutputData(
                expertResponse, chatHistory);

        // Present the data
        outputBoundary.presentChat(outputData);
    }

    /**
     * Simulates an expert's response to the user's message.
     *
     * @param userMessage The user's message.
     * @param expert      The expert entity.
     * @return A simulated response from the expert.
     */
    private String simulateExpertResponse(String userMessage, Expert expert) {
        // Placeholder for actual AI response
        return "Thank you for sharing your idea. Let's discuss it further!";
    }
}
