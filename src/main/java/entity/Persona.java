package entity;

import java.util.List;

/**
 * Represents a user persona that can be generated based on target audience analysis.
 */
public class Persona {
    private int personaID;
    private String name;
    private String ageRange;
    private String gender;
    private String occupation;
    private String location;
    private List<String> interests;
    private String quote;

    public Persona() {
        this.personaID = personaID;
        this.name = name;
        this.ageRange = ageRange;
        this.gender = gender;
        this.occupation = occupation;
        this.location = location;
        this.interests = interests;
        this.quote = quote;
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

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
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

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}

