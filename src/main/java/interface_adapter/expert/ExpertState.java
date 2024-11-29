package interface_adapter.expert;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state for the ExpertChatView.
 */
public class ExpertState {
    private String username = "";
    private String password = "";
    private String expertResponse = "";
    private List<String> chatHistory = new ArrayList<>();
    private List<String[]> experts = List.of(
            new String[]{"1", "Mark Cuban", "Mark Cuban is a billionaire entrepreneur and Dallas "
                    + "Mavericks owner known for his bold style on Shark Tank. After selling "
                    + "Broadcast.com, he became an influential investor across tech, sports, and "
                    + "entertainment. Mark is direct, competitive, and growth-focused. He values clear, "
                    + "scalable ideas and isn’t afraid to call out weaknesses, backing only pitches that "
                    + "show true potential for innovation and success.", "expert_avatars/mark-cuban.png"},
            new String[]{"2", "Elena Romero", "Elena Romero is a sustainable investing advocate and "
                    + "former Silicon Valley venture capitalist. She built her wealth through early "
                    + "investments in green technology and renewable energy. Thoughtful and idealistic, "
                    + "Elena values business ideas with environmental and societal impact. In pitches, "
                    + "she urges founders to consider sustainability, aligning her support with projects "
                    + "that prioritize ethical practices and long-term positive change.",
                         "expert_avatars/elena-romero.png"},
            new String[]{"3", "Raj Patel", "Raj Patel is a fintech pioneer who founded a leading digital"
                    + " payment platform, transforming the way we transact online. Known for his calm, "
                    + "analytical approach, Raj focuses on data, scalability, and clear profitability. "
                    + "He values pitches with strong financial foundations and realistic growth models, "
                    + "offering insightful feedback and investment if the numbers show real promise. "
                    + "Raj is precise, calculated, and driven by logical assessment.",
                         "expert_avatars/raj-patel.png"},
            new String[]{"4", "Jensen Huang", "Jensen Huang is the visionary founder and CEO of NVIDIA, "
                    + "a company that revolutionized graphics processing and artificial intelligence. "
                    + "Known for his innovative mindset and strategic brilliance, Jensen thrives on "
                    + "cutting-edge technology and ambitious ideas. In pitches, he seeks projects "
                    + "with groundbreaking potential, focusing on tech-driven scalability and real-world "
                    + "impact. Analytical yet inspiring, he values bold visions backed "
                    + "by solid technical foundations.", "expert_avatars/jensen-huang.png"}
    );

    /**
     * Gets the expert's response.
     *
     * @return The expert's response.
     */
    public String getExpertResponse() {
        return expertResponse;
    }

    /**
     * Sets the expert's response.
     *
     * @param expertResponse The expert's response.
     */
    public void setExpertResponse(String expertResponse) {
        this.expertResponse = expertResponse;
    }

    /**
     * Gets the chat history.
     *
     * @return The chat history.
     */
    public List<String> getChatHistory() {
        return chatHistory;
    }

    /**
     * Sets the chat history.
     *
     * @param chatHistory The updated chat history.
     */
    public void setChatHistory(List<String> chatHistory) {
        this.chatHistory = chatHistory;
    }

    /**
     * Gets the username associated with this state.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String[]> getExperts() {
        return experts;
    }
}
