package use_case.chat_expert;

import use_case.set_targetaudience.DetailedDataAccessObjectInterface;
import entity.ChatMessage;
import entity.Expert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChatExpertInteractorTest {

    @Test
    void initiateChatSuccessTest() {
        // Create input data
        String expertId = "expert_1";
        String userMessage = "Hello, I need some advice.";
        ChatExpertInputData inputData = new ChatExpertInputData(expertId, userMessage);

        // Create a mock expert repository
        ChatExpertDataAccessInterface expertRepository = new ChatExpertDataAccessInterface() {
            @Override
            public Expert getExpertById(String id) {
                if (id.equals(expertId)) {
                    Expert expert = new Expert(expertId);
                    expert.setDescription("I am an expert in marketing.");
                    return expert;
                }
                return null;
            }
        };

        // Create a mock ChatGPT data access object
        ExpertChatDataAccessInterface chatgptDataAccessObject = new ExpertChatDataAccessInterface() {
            @Override
            public String utilizeApi(List<ChatMessage> messages) {
                // Return the expected mock response
                return "Sure, I'd be happy to help!";
            }
        };

        // Create a mock output boundary
        ChatExpertOutputBoundary outputBoundary = new ChatExpertOutputBoundary() {
            @Override
            public void presentChat(ChatExpertOutputData outputData) {
                // Assertions to verify the output data
                assertEquals("Sure, I'd be happy to help!", outputData.getExpertResponse());
                assertEquals(2, outputData.getChatHistory().size());

                // Verify the user's message
                ChatMessage userMsg = outputData.getChatHistory().get(0);
                assertEquals("user", userMsg.getRole());
                assertEquals(userMessage, userMsg.getContent());

                // Verify the expert's response
                ChatMessage expertMsg = outputData.getChatHistory().get(1);
                assertEquals("assistant", expertMsg.getRole());
                assertEquals("Sure, I'd be happy to help!", expertMsg.getContent());
            }
        };

        // Create the interactor
        ChatExpertInteractor interactor = new ChatExpertInteractor(expertRepository, chatgptDataAccessObject, outputBoundary);

        // Execute the use case
        interactor.initiateChat(inputData);
    }

    @Test
    void initiateChatExpertNotFoundTest() {
        // Create input data
        String expertId = "non_existent_expert";
        String userMessage = "Hello, I need some advice.";
        ChatExpertInputData inputData = new ChatExpertInputData(expertId, userMessage);

        // Create a mock expert repository that returns null
        ChatExpertDataAccessInterface expertRepository = new ChatExpertDataAccessInterface() {
            @Override
            public Expert getExpertById(String id) {
                return null; // Expert not found
            }
        };

        // Create a mock ChatGPT data access object (methods not used in this test)
        ExpertChatDataAccessInterface chatgptDataAccessObject = new ExpertChatDataAccessInterface() {
            @Override
            public String utilizeApi(List<ChatMessage> messages) {
                return null;
            }
        };

        // Create a mock output boundary (should not be called)
        ChatExpertOutputBoundary outputBoundary = new ChatExpertOutputBoundary() {
            @Override
            public void presentChat(ChatExpertOutputData outputData) {
                fail("Output boundary should not be called when expert is not found.");
            }
        };

        // Create the interactor
        ChatExpertInteractor interactor = new ChatExpertInteractor(expertRepository, chatgptDataAccessObject, outputBoundary);

        // Expect an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            interactor.initiateChat(inputData);
        });

        assertEquals("Expert not found.", exception.getMessage());
    }

    @Test
    void initiateChatApiExceptionTest() {
        // Create input data
        String expertId = "expert_1";
        String userMessage = "Hello, I need some advice.";
        ChatExpertInputData inputData = new ChatExpertInputData(expertId, userMessage);

        // Create a mock expert repository
        ChatExpertDataAccessInterface expertRepository = new ChatExpertDataAccessInterface() {
            @Override
            public Expert getExpertById(String id) {
                if (id.equals(expertId)) {
                    Expert expert = new Expert(expertId);
                    expert.setDescription("I am an expert in marketing.");
                    return expert;
                }
                return null;
            }
        };

        // Create a mock ChatGPT data access object that throws an exception
        ExpertChatDataAccessInterface chatgptDataAccessObject = new ExpertChatDataAccessInterface() {
            @Override
            public String utilizeApi(List<ChatMessage> messages) {
                // Simulate an exception being thrown
                throw new RuntimeException("API error");
            }
        };

        // Create a mock output boundary
        ChatExpertOutputBoundary outputBoundary = new ChatExpertOutputBoundary() {
            @Override
            public void presentChat(ChatExpertOutputData outputData) {
                // Assertions to verify the output data
                assertEquals("I'm sorry, but I'm having trouble responding right now.", outputData.getExpertResponse());
                assertEquals(2, outputData.getChatHistory().size());

                // Verify the user's message
                ChatMessage userMsg = outputData.getChatHistory().get(0);
                assertEquals("user", userMsg.getRole());
                assertEquals(userMessage, userMsg.getContent());

                // Verify the expert's response
                ChatMessage expertMsg = outputData.getChatHistory().get(1);
                assertEquals("assistant", expertMsg.getRole());
                assertEquals("I'm sorry, but I'm having trouble responding right now.", expertMsg.getContent());
            }
        };

        // Create the interactor
        ChatExpertInteractor interactor = new ChatExpertInteractor(expertRepository, chatgptDataAccessObject, outputBoundary);

        // Execute the use case
        interactor.initiateChat(inputData);
    }
}
