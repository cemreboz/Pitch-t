package use_case.chat_persona;

import entity.ChatMessage;
import entity.Persona;
import entity.Pitch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatPersonaInteractorTest {

    private ChatPersonaInteractor interactor;
    private TestChatPersonaDataAccess chatGptDao;
    private TestChatPersonaOutputBoundary outputBoundary;

    @BeforeEach
    void setUp() {
        // Use test stubs
        chatGptDao = new TestChatPersonaDataAccess();
        outputBoundary = new TestChatPersonaOutputBoundary();

        // Instantiate interactor with test stubs
        interactor = new ChatPersonaInteractor(chatGptDao, outputBoundary);
    }

    @Test
    void testInitiateChatWithEmptyHistory() {
        // Arrange
        Persona persona = new Persona();
        persona.setName("Alice");
        persona.setAge(30);
        persona.setOccupation("Engineer");
        persona.setLocation("Toronto");

        Pitch pitch = new Pitch("1", "Tech Innovations", "image.png",
                "A pitch about innovative technology for daily life.", new ArrayList<>());

        ChatPersonaInputData inputData = new ChatPersonaInputData("What do you think?", persona, pitch);

        // Act
        interactor.initiateChat(inputData);

        // Assert
        List<ChatMessage> chatHistory = persona.getChatHistory();
        assertEquals(3, chatHistory.size()); // Initial prompt, user message, assistant response
        assertEquals("system", chatHistory.get(0).getRole());
        assertTrue(chatHistory.get(0).getContent().contains("Alice"));
        assertEquals("user", chatHistory.get(1).getRole());
        assertEquals("What do you think?", chatHistory.get(1).getContent());
        assertEquals("assistant", chatHistory.get(2).getRole());
        assertEquals("This is a mock GPT response!", chatHistory.get(2).getContent());

        ChatPersonaOutputData outputData = outputBoundary.getLastOutputData();
        assertNotNull(outputData);
        assertEquals(3, outputData.getChatHistory().size());
    }

    @Test
    void testInitiateChatWithExistingHistory() {
        // Arrange
        Persona persona = new Persona();
        persona.setName("Bob");
        persona.setAge(25);
        persona.setOccupation("Designer");
        persona.setLocation("New York");

        List<ChatMessage> existingHistory = new ArrayList<>();
        existingHistory.add(new ChatMessage("system", "You are Bob, a 25-year-old Designer from New York."));
        existingHistory.add(new ChatMessage("user", "What is your opinion?"));
        persona.setChatHistory(existingHistory);

        Pitch pitch = new Pitch("2", "Design Pitch", "image2.png",
                "A pitch about creative design ideas.", new ArrayList<>());

        ChatPersonaInputData inputData = new ChatPersonaInputData("Do you agree?", persona, pitch);

        // Act
        interactor.initiateChat(inputData);

        // Assert
        List<ChatMessage> chatHistory = persona.getChatHistory();
        assertEquals(4, chatHistory.size()); // Existing history + user message + assistant response
        assertEquals("system", chatHistory.get(0).getRole());
        assertEquals("user", chatHistory.get(1).getRole());
        assertEquals("What is your opinion?", chatHistory.get(1).getContent());
        assertEquals("user", chatHistory.get(2).getRole());
        assertEquals("Do you agree?", chatHistory.get(2).getContent());
        assertEquals("assistant", chatHistory.get(3).getRole());
        assertEquals("This is a mock GPT response!", chatHistory.get(3).getContent());

        ChatPersonaOutputData outputData = outputBoundary.getLastOutputData();
        assertNotNull(outputData);
        assertEquals(4, outputData.getChatHistory().size());
    }

    // Test implementation for ChatPersonaDataAccessInterface
    private static class TestChatPersonaDataAccess implements ChatPersonaDataAccessInterface {
        @Override
        public String utilizeApi(List<ChatMessage> chatHistory) {
            return "This is a mock GPT response!";
        }
    }

    // Test implementation for ChatPersonaOutputBoundary
    private static class TestChatPersonaOutputBoundary implements ChatPersonaOutputBoundary {
        private ChatPersonaOutputData lastOutputData;

        @Override
        public void presentChat(ChatPersonaOutputData outputData) {
            this.lastOutputData = outputData;
        }

        public ChatPersonaOutputData getLastOutputData() {
            return lastOutputData;
        }
    }
}
