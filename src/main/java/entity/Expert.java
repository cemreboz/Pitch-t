package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an expert with an ID and a chat history.
 */
public class Expert {
    private String id;
    private String name;
    private String description;
    private String avatar;
    private List<String> chatHistory;

    /**
     * Constructs an expert with only an ID.
     *
     * @param id The unique identifier for the expert.
     */
    public Expert(String id) {
        this.id = id;
        this.chatHistory = new ArrayList<>();
    }

    /**
     * Constructs an expert with full details.
     *
     * @param id          The unique identifier for the expert.
     * @param name        The name of the expert.
     * @param description A brief description of the expert.
     * @param avatar      The path or filename for the expert's avatar.
     */
    public Expert(String id, String name, String description, String avatar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.chatHistory = new ArrayList<>();
    }

    /**
     * Static factory method to create a preset expert.
     * @param expertId expert to be made
     * @return the expert if found
     * @throws IllegalArgumentException if invalid expertid
     */
    public static Expert createNewExpert(String expertId) {
        if ("1".equals(expertId)) {
            return new Expert("1", "Mark Cuban",
                    "Mark Cuban is a billionaire entrepreneur and Dallas Mavericks owner known for his "
                            + "bold style on Shark Tank. "
                            + "After selling Broadcast.com, he became an influential investor across tech, sports, "
                            + "and entertainment. "
                            + "Mark is direct, competitive, and growth-focused. He values clear, scalable ideas "
                            + "and isn’t afraid to call "
                            + "out weaknesses, backing only pitches that show true potential for innovation and "
                            + "success.",
                    "expert_avatars/mark-cuban.png");
        }
        else if ("2".equals(expertId)) {
            return new Expert("2", "Elena Romero",
                    "Elena Romero is a sustainable investing advocate and former Silicon Valley "
                            + "venture capitalist. "
                            + "She built her wealth through early investments in green technology and renewable "
                            + "energy. Thoughtful "
                            + "and idealistic, Elena values business ideas with environmental and societal impact. "
                            + "In pitches, she urges "
                            + "founders to consider sustainability, aligning her support with projects that "
                            + "prioritize ethical practices "
                            + "and long-term positive change.",
                    "expert_avatars/elena-romero.png");
        }
        else if ("3".equals(expertId)) {
            return new Expert("3", "Raj Patel",
                    "Raj Patel is a fintech pioneer who founded a leading digital payment platform, "
                            + "transforming the way we transact online. "
                            + "Known for his calm, analytical approach, Raj focuses on data, scalability, "
                            + "and clear profitability. He values pitches "
                            + "with strong financial foundations and realistic growth models, "
                            + "offering insightful feedback and investment if the numbers "
                            + "show real promise. Raj is precise, calculated, and driven by logical assessment.",
                    "expert_avatars/raj-patel.png");
        }
        else if ("4".equals(expertId)) {
            return new Expert("4", "Jensen Huang",
                    "Jensen Huang is the visionary founder and CEO of NVIDIA, a company that "
                            + "revolutionized graphics processing and artificial intelligence. "
                            + "Known for his innovative mindset and strategic brilliance, Jensen thrives "
                            + "on cutting-edge technology and ambitious ideas. In pitches, "
                            + "he seeks projects with groundbreaking potential, focusing on tech-driven "
                            + "scalability and real-world impact. Analytical yet inspiring, "
                            + "he values bold visions backed by solid technical foundations.",
                    "expert_avatars/jensen-huang.png");
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String expertName) {
        this.name = expertName;
    }

    public void setAvatar(String expertAvatar) {
        this.avatar = expertAvatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<String> chatHistory) {
        this.chatHistory = chatHistory;
    }

    /**
     * Adds a message to the chat history.
     *
     * @param message The message to be added.
     */
    public void addChatMessage(String message) {
        this.chatHistory.add(message);
    }

    /**
     * Clears all messages from the chat history.
     */
    public void clearChatHistory() {
        this.chatHistory.clear();
    }
}
