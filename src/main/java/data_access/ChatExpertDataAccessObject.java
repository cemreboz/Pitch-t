package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.Expert;
import use_case.chat_expert.ChatExpertDataAccessInterface;

/**
 * Data Access Object for managing Expert data.
 * Implements storage and retrieval for ChatExpertInteractor.
 */
public class ChatExpertDataAccessObject implements ChatExpertDataAccessInterface {

    private final Map<String, Expert> experts = new HashMap<>();

    /**
     * Constructs a ChatExpertDataAccessObject and loads the initial data.
     */
    public ChatExpertDataAccessObject() {
        loadExpertData();
    }

    @Override
    public Expert getExpertById(String expertId) {
        return experts.get(expertId);
    }

    /**
     * Loads the initial expert data into the storage.
     */
    private void loadExpertData() {
        experts.put("1", new Expert("1", "Mark Cuban",
                "Mark Cuban is a billionaire entrepreneur and Dallas Mavericks "
                        + "owner known for his bold style on Shark Tank. "
                        + "After selling Broadcast.com, he became an influential investor "
                        + "across tech, sports, and entertainment. "
                        + "Mark is direct, competitive, and growth-focused. He values clear, scalable "
                        + "ideas and isn’t afraid to call "
                        + "out weaknesses, backing only pitches that show true potential for innovation and success.",
                "expert_avatars/mark-cuban.png"));

        experts.put("2", new Expert("2", "Elena Romero",
                "Elena Romero is a sustainable investing advocate and former "
                        + "Silicon Valley venture capitalist. "
                        + "She built her wealth through early investments in green technology "
                        + "and renewable energy. Thoughtful "
                        + "and idealistic, Elena values business ideas with environmental "
                        + "and societal impact. In pitches, she urges "
                        + "founders to consider sustainability, aligning her support with "
                        + "projects that prioritize ethical practices "
                        + "and long-term positive change.",
                "expert_avatars/elena-romero.png"));

        experts.put("3", new Expert("3", "Raj Patel",
                "Raj Patel is a fintech pioneer who founded a leading digital "
                        + "payment platform, transforming the way we transact online. "
                        + "Known for his calm, analytical approach, Raj focuses on data, "
                        + "scalability, and clear profitability. He values pitches "
                        + "with strong financial foundations and realistic growth models, "
                        + "offering insightful feedback and investment if the numbers "
                        + "show real promise. Raj is precise, calculated, and driven by logical assessment.",
                "expert_avatars/raj-patel.png"));

        experts.put("4", new Expert("4", "Jensen Huang",
                "Jensen Huang is the visionary founder and CEO of NVIDIA, a company that revolutionized "
                        + "graphics processing and artificial intelligence. "
                        + "Known for his innovative mindset and strategic brilliance, "
                        + "Jensen thrives on cutting-edge technology and ambitious ideas. In pitches, "
                        + "he seeks projects with groundbreaking potential, focusing on tech-driven "
                        + "scalability and real-world impact. Analytical yet inspiring, "
                        + "he values bold visions backed by solid technical foundations.",
                "expert_avatars/jensen-huang.png"));
    }
}
