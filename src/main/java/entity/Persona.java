package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user persona that can be generated based on target audience analysis.
 */
public class Persona {
    private int personaID;
    private String name;
    private int age;
    private String gender;
    private String education;
    private String salaryRange;
    private String about;
    private String stats;
    private String location;
    private String occupation;
    private String avatar;
    private List<String> interests;
    private List<ChatMessage> chatHistory;

    public Persona() {
        this.chatHistory = new ArrayList<>();
    }

    public int getPersonaID() {
        return personaID;
    }

    public void setPersonaID(int personaID) {
        this.personaID = personaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryrange) {
        this.salaryRange = salaryrange;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }

    /**
     * Adds message to chat history.
     * @param message to add
     */
    public void addChatMessage(ChatMessage message) {
        this.chatHistory.add(message);
    }

    /**
     * Clears chat history.
     */
    public void clearChatHistory() {
        this.chatHistory.clear();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Persona persona = (Persona) o;

        return personaID == persona.personaID &&
                age == persona.age &&
                Objects.equals(name, persona.name) &&
                Objects.equals(gender, persona.gender) &&
                Objects.equals(occupation, persona.occupation) &&
                Objects.equals(location, persona.location) &&
                Objects.equals(education, persona.education) &&
                Objects.equals(salaryRange, persona.salaryRange) &&
                Objects.equals(about, persona.about) &&
                Objects.equals(stats, persona.stats) &&
                Objects.equals(avatar, persona.avatar) &&
                Objects.equals(interests, persona.interests) &&
                Objects.equals(chatHistory, persona.chatHistory);
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(
                personaID, name, age, gender, occupation, location, education,
                salaryRange, about, stats, avatar, interests, chatHistory
        );
    }
}

