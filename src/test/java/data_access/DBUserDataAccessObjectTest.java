package data_access;

import entity.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBUserDataAccessObjectTest {

    @Test
    public void testPitchesSerializationDeserialization() {
        // Instantiate the DAO
        DBUserDataAccessObject dao = new DBUserDataAccessObject(new DBUserFactory());

        // Create sample pitches
        List<Pitch> originalPitches = createSamplePitches();

        // Serialize pitches to JSON
        String pitchesJson = dao.pitchesToJson(originalPitches);

        // Deserialize JSON back to pitches
        List<Pitch> deserializedPitches = dao.pitchesFromJson(pitchesJson);

        // Compare original and deserialized pitches
        assertEquals(originalPitches, deserializedPitches, "Pitches did not deserialize correctly");

        System.out.println("Original Pitch: " + originalPitches.get(0));
        System.out.println("Deserialized Pitch: " + deserializedPitches.get(0));
    }


    @Test
    public void testExpertsSerializationDeserialization() {
        // Instantiate the DAO
        DBUserDataAccessObject dao = new DBUserDataAccessObject(new DBUserFactory());

        // Create sample experts
        List<Expert> originalExperts = createSampleExperts();

        // Serialize experts to JSON
        String expertsJson = dao.expertsToJson(originalExperts);

        // Deserialize JSON back to experts
        List<Expert> deserializedExperts = dao.expertsFromJson(expertsJson);

        // Compare original and deserialized experts
        assertEquals(originalExperts, deserializedExperts, "Experts did not deserialize correctly");
    }


    private List<Pitch> createSamplePitches() {
        // Create sample personas
        Persona persona1 = new Persona();
        persona1.setPersonaID(1);
        persona1.setName("Persona 1");
        persona1.setAge(30);
        persona1.setGender("Male");
        persona1.setOccupation("Tech Specialist");
        persona1.setLocation("San Francisco");
        persona1.setEducation("Bachelor's Degree");
        persona1.setSalaryRange("$70,000 - $90,000");
        persona1.setAbout("Always learning.");
        persona1.setStats("High engagement");
        persona1.setAvatar("avatar1.png");
        persona1.setInterests(Arrays.asList("tech", "marketing"));
        persona1.setChatHistory(Arrays.asList(
                new ChatMessage("user", "Chat message 1", LocalDateTime.now()),
                new ChatMessage("assistant", "Chat message 2", LocalDateTime.now())
        ));

        // Create sample detailed target audience
        DetailedTargetAudience audience1 = new DetailedTargetAudience("Tech Enthusiasts");
        audience1.setMinAge(18);
        audience1.setMaxAge(35);
        audience1.setGender("Any");
        audience1.setEducationLevel("College");
        audience1.setOccupation("Software Engineer");
        audience1.setIncomeLevel("$50k-$100k");
        audience1.setGeographicLocation("Urban");
        audience1.setInterestsAndPassions(Arrays.asList("AI", "Blockchain"));
        audience1.setValues(Arrays.asList("Innovation", "Efficiency"));
        audience1.setPersonalityTraits(Arrays.asList("Curious", "Analytical"));
        audience1.setLifestyle("Tech-savvy");
        audience1.setEarlyAdopter(true);
        audience1.setTechSavviness("High");
        audience1.setGadgetOwnership(Arrays.asList("Smartphone", "Laptop"));
        audience1.setMediaConsumption(Arrays.asList("Tech Blogs", "YouTube"));
        audience1.setOnlineEngagement(Arrays.asList("Forums", "Webinars"));
        audience1.setInfluencer(false);
        audience1.setEventParticipation(Arrays.asList("Tech Conferences"));
        audience1.setHobbies(Arrays.asList("Coding", "Gaming"));
        audience1.setBrandAffinity(Arrays.asList("Google", "Microsoft"));
        audience1.setEnvironmentalConcerns(true);
        audience1.setGlobalPerspective(true);
        audience1.setMultilingualAbilities(false);

        // Create sample pitch
        Pitch pitch1 = new Pitch("unique_pitch_id_1", "Pitch Name 1", "http://example.com/image1.png",
                "Pitch description 1", Arrays.asList("Tech Enthusiasts", "Marketers"));
        pitch1.getPersonas().add(persona1);
        Map<String, DetailedTargetAudience> audienceMap = new HashMap<>();
        audienceMap.put("Audience1", audience1);
        pitch1.setDetailedTargetAudienceMap(audienceMap);

        // Add more pitches if needed

        return Arrays.asList(pitch1);
    }
    private List<Expert> createSampleExperts() {
        Expert expert1 = new Expert("expert_1");
        expert1.setChatHistory(Arrays.asList(
                new ChatMessage("user", "Chat message 1", LocalDateTime.now()),
                new ChatMessage("assistant", "Chat message 2", LocalDateTime.now())
        ));

        // Add more experts if needed

        return Arrays.asList(expert1);
    }

}
