package use_case.chat_expert;

import java.util.ArrayList;
import java.util.List;

import use_case.chat_expert.ChatExpertGptAccessInterface;
import entity.ChatMessage;
import entity.Expert;

/**
 * Interactor class for the Chat with Expert use case.
 * Implements the business logic.
 */
public class ChatExpertInteractor implements ChatExpertInputBoundary {

    private final ChatExpertDataAccessInterface expertRepository;
    private final ChatExpertGptAccessInterface chatgptDataAccessObject;
    private final ChatExpertOutputBoundary outputBoundary;

    /**
     * Constructs a ChatExpertInteractor object.
     *
     * @param expertRepository The data access interface for experts.
     * @param chatgptDataAccessObject  the DAO for the api.
     * @param outputBoundary   The output boundary interface.
     */
    public ChatExpertInteractor(
            ChatExpertDataAccessInterface expertRepository, ChatExpertGptAccessInterface chatgptDataAccessObject,
            ChatExpertOutputBoundary outputBoundary) {
        this.expertRepository = expertRepository;
        this.chatgptDataAccessObject = chatgptDataAccessObject;
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
        final Expert expert = expertRepository.getExpertById(inputData.getExpertId());

        if (expert == null) {
            throw new IllegalArgumentException("Expert not found.");
        }

        // Add user's message to chat history
        final ChatMessage userMessage = new ChatMessage("user", inputData.getUserMessage());
        expert.addChatMessage(userMessage);

        // Prepare messages for API call
        final List<ChatMessage> messagesForApi = new ArrayList<>();

        // Add system message (expert's description)
        messagesForApi.add(new ChatMessage("system", expert.getDescription()));

        // Add previous conversation messages
        List<ChatMessage> chatHistory = expert.getChatHistory();

        // Limit the number of messages (e.g., last 10 messages)
        final int maxMessages = 10;
        final int startIndex = Math.max(chatHistory.size() - maxMessages, 0);
        for (int i = startIndex; i < chatHistory.size(); i++) {
            messagesForApi.add(chatHistory.get(i));
        }

        // Call the GPT API to get expert's response
        String expertResponse;
        try {
            expertResponse = chatgptDataAccessObject.getInteraction(messagesForApi);
        }
        catch (Exception event) {
            expertResponse = "I'm sorry, but I'm having trouble responding right now.";
            event.printStackTrace();
        }

        // Add expert's response to chat history
        final ChatMessage assistantMessage = new ChatMessage("assistant", expertResponse);
        expert.addChatMessage(assistantMessage);

        // Prepare output data
        chatHistory = expert.getChatHistory();
        final ChatExpertOutputData outputData = new ChatExpertOutputData(expertResponse, chatHistory);

        // Present the data
        outputBoundary.presentChat(outputData);
    }

}
