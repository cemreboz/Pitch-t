package data_access;

import entity.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBUserDataAccessObjectTest {

    private DBUserDataAccessObject dbUserDataAccessObject;

    @BeforeEach
    void setUp() {
        dbUserDataAccessObject = new DBUserDataAccessObject(new DBUserFactory()) {
            @Override
            public boolean existsByName(String username) {
                return "existingUser".equals(username);
            }

            @Override
            public void create(User user) {
                // Stub create method
            }

            @Override
            public void update(User user) {
                // Stub update method
            }

            @Override
            public User get(String username) {
                if ("existingUser".equals(username)) {
                    return new DBUser("existingUser", "password");
                }
                return null;
            }

            @Override
            public void changePassword(User user) {
                // Stub changePassword method
            }
        };
    }

    @Test
    void testSetAndGetCurrentUsername() {
        String username = "testUser";
        dbUserDataAccessObject.setCurrentUsername(username);
        assertEquals(username, dbUserDataAccessObject.getCurrentUsername());
    }

    @Test
    void testSetAndGetCurrentUser() {
        User user = new DBUser("testUser", "testPassword");
        dbUserDataAccessObject.setCurrentUser(user);
        assertEquals(user, dbUserDataAccessObject.getCurrentUser());
    }

    @Test
    void testSaveNewUser() {
        User newUser = new DBUser("newUser", "password");
        assertDoesNotThrow(() -> dbUserDataAccessObject.save(newUser));
    }

    @Test
    void testSaveExistingUser() {
        User existingUser = new DBUser("existingUser", "password");
        assertDoesNotThrow(() -> dbUserDataAccessObject.save(existingUser));
    }

    @Test
    void testGetExpertById() {
        // Arrange: Create a DBUser with a full list of predefined experts
        DBUser currentUser = new DBUser("testUser", "password");

        // Add predefined experts to the user
        Expert expert1 = Expert.createNewExpert("1");
        Expert expert2 = Expert.createNewExpert("2");
        Expert expert3 = Expert.createNewExpert("3");
        currentUser.setExperts(List.of(expert1, expert2, expert3)); // Initialize with a full list of experts

        // Set the current user in the DAO
        dbUserDataAccessObject.setCurrentUser(currentUser);

        // Act: Retrieve an existing expert by ID
        Expert result = dbUserDataAccessObject.getExpertById("1");

        // Assert: Validate the retrieved expert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Mark Cuban", result.getName());
        assertEquals("expert_avatars/mark-cuban.png", result.getAvatar());

        // Act: Attempt to retrieve a non-existent expert, expecting it to create a new expert
        Expert newExpert = dbUserDataAccessObject.getExpertById("4"); // This ID does not exist in the predefined list

        // Assert: Validate the new expert creation
        assertNotNull(newExpert);
        assertEquals("4", newExpert.getId()); // Validate the ID of the new expert
        assertEquals("Jensen Huang", newExpert.getName());      // New expert has no predefined name
        assertEquals("expert_avatars/jensen-huang.png", newExpert.getAvatar());    // New expert has no predefined avatar
        assertTrue(newExpert.getChatHistory().isEmpty()); // Ensure the chat history is empty for the new expert
    }

    @Test
    void testGet() {
        User result = dbUserDataAccessObject.get("existingUser");
        assertNotNull(result);
        assertEquals("existingUser", result.getName());
    }

    @Test
    void testChangePassword() {
        DBUser user = new DBUser("existingUser", "newPassword");
        assertDoesNotThrow(() -> dbUserDataAccessObject.changePassword(user));
    }

    @Test
    void testPitchesFromJsonAndToJson() {
        List<Pitch> pitches = List.of(
                new Pitch("1", "Pitch 1", "image1", "description1", List.of("Audience 1")),
                new Pitch("2", "Pitch 2", "image2", "description2", List.of("Audience 2"))
        );

        String pitchesJson = dbUserDataAccessObject.pitchesToJson(pitches);
        List<Pitch> result = dbUserDataAccessObject.pitchesFromJson(pitchesJson);

        assertEquals(pitches.size(), result.size());
        assertEquals(pitches.get(0).getName(), result.get(0).getName());
    }

    @Test
    void testPersonaFromJsonAndToJson() {
        Persona persona = new Persona();
        persona.setPersonaID(1);
        persona.setName("Test Persona");
        persona.setAge(30);
        persona.setGender("Non-binary");
        persona.setOccupation("Engineer");
        persona.setLocation("Toronto");
        persona.setEducation("Masters");
        persona.setSalaryRange("60k-80k");
        persona.setAbout("Friendly persona");
        persona.setStats("Healthy");
        persona.setAvatar("avatar.png");
        persona.setInterests(List.of("Tech", "Gaming"));

        String personaJson = dbUserDataAccessObject.personaToJson(persona).toString();
        Persona result = dbUserDataAccessObject.personaFromJson(new JSONObject(personaJson));

        assertEquals(persona.getName(), result.getName());
        assertEquals(persona.getAge(), result.getAge());
    }

    @Test
    void testDetailedTargetAudienceMapToJsonAndFromJson() {
        DetailedTargetAudience audience = new DetailedTargetAudience("Audience 1");
        audience.setMinAge(18);
        audience.setMaxAge(35);
        audience.setGender("Any");
        audience.setEducationLevel("Bachelor's");
        audience.setOccupation("Student");
        audience.setIncomeLevel("10k-20k");
        audience.setGeographicLocation("Canada");
        audience.setInterestsAndPassions(List.of("Tech", "Art"));
        audience.setValues(List.of("Innovation", "Sustainability"));
        audience.setPersonalityTraits(List.of("Creative", "Analytical"));
        audience.setLifestyle("Urban");
        audience.setEarlyAdopter(true);
        audience.setTechSavviness("High");
        audience.setGadgetOwnership(List.of("Smartphone", "Laptop"));
        audience.setMediaConsumption(List.of("Social Media", "Blogs"));
        audience.setOnlineEngagement(List.of("Forums", "Streaming"));
        audience.setInfluencer(false);
        audience.setEventParticipation(List.of("Conferences", "Meetups"));
        audience.setHobbies(List.of("Photography", "Traveling"));
        audience.setBrandAffinity(List.of("Brand A", "Brand B"));
        audience.setEnvironmentalConcerns(true);
        audience.setGlobalPerspective(true);
        audience.setMultilingualAbilities(true);

        var audienceMap = new HashMap<String, DetailedTargetAudience>();
        audienceMap.put("Key1", audience);

        String audienceMapJson = dbUserDataAccessObject.detailedTargetAudienceMapToJson(audienceMap).toString();
        var result = dbUserDataAccessObject.detailedTargetAudienceMapFromJson(new JSONObject(audienceMapJson));

        DetailedTargetAudience resultAudience = result.get("Key1");

        assertEquals(audience.getName(), resultAudience.getName());
        assertEquals(audience.getTechSavviness(), resultAudience.getTechSavviness());
        assertEquals(audience.getLifestyle(), resultAudience.getLifestyle());
        assertEquals(audience.getHobbies(), resultAudience.getHobbies());
        assertTrue(resultAudience.isEnvironmentalConcerns());
    }
}
