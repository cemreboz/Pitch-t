package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.DBUser;
import entity.DetailedTargetAudience;
import entity.Expert;
import entity.Persona;
import entity.Pitch;
import entity.User;
import entity.UserFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.account_settings.AccountSettingsDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.dashboard_show_pitch.DashboardDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * The DAO for user data.
 */
public class DBUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        DashboardDataAccessInterface,
        AccountSettingsDataAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private static final String INFO_FIELD = "info";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String GENDER_FIELD = "gender";
    private static final String OCCUPATION_FIELD = "occupation";
    private static final String CHATHISTORY_FIELD = "chatHistory";
    private static final String PITCHES_KEY = "pitches";
    private static final String EXPERTS_KEY = "experts";
    private static final String PREFIX = "pitchit:";

    private final UserFactory userFactory;
    private String currentUsername;
    private User currentUser;

    public DBUserDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
        // No need to do anything to reinitialize a user list! The data is the cloud that may be miles away.
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public User get(String username) {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s",
                        addPrefix(username)))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                final JSONObject userJSONObject = responseBody.getJSONObject("user");
                final String name = userJSONObject.getString(USERNAME);
                final String password = userJSONObject.getString(PASSWORD);

                final User user = userFactory.create(removePrefix(name), password);

                if (user instanceof DBUser) {
                    final DBUser dbUser = (DBUser) user;

                    final JSONObject infoObject = userJSONObject.getJSONObject(INFO_FIELD);
                    if (infoObject.has(PITCHES_KEY)) {
                        dbUser.setPitches(pitchesFromJson(infoObject.getString(PITCHES_KEY)));
                    }
                    if (infoObject.has(EXPERTS_KEY)) {
                        dbUser.setExperts(expertsFromJson(infoObject.getString(EXPERTS_KEY)));
                    }
                }
                return user;
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean existsByName(String username) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s",
                        addPrefix(username)))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            return responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE;
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        if (existsByName(user.getName())) {
            update(user);
        }
        else {
            create(user);
        }
    }

    /**
     * Creates a user then updates user info.
     * @param user user to create
     * @throws RuntimeException when the api encounters trouble returning data
     */
    public void create(User user) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);

        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, addPrefix(user.getName()));
        requestBody.put(PASSWORD, user.getPassword());

        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("POST", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                // After creation, update the info field
                update(user);
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Updates the user on the database.
     * @param user user to update
     * @throws RuntimeException when the api encounters trouble returning data
     */
    public void update(User user) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);

        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, addPrefix(user.getName()));
        requestBody.put(PASSWORD, user.getPassword());

        if (user instanceof DBUser) {
            final DBUser dbUser = (DBUser) user;
            final JSONObject infoObject = new JSONObject();
            infoObject.put(PITCHES_KEY, pitchesToJson(dbUser.getPitches()));
            infoObject.put(EXPERTS_KEY, expertsToJson(dbUser.getExperts()));
            requestBody.put(INFO_FIELD, infoObject);
        }

        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) != SUCCESS_CODE) {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changePassword(User user) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // POST METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, addPrefix(user.getName()));
        requestBody.put(PASSWORD, user.getPassword());
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                // success!
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String addPrefix(String username) {
        return PREFIX + username;
    }

    private String removePrefix(String username) {
        final String name;
        if (username.startsWith(PREFIX)) {
            name = username.substring(PREFIX.length());
        }
        else {
            name = username;
        }
        return name;
    }

    private String expertsToJson(List<Expert> experts) {
        final JSONArray expertsArray = new JSONArray();
        for (Expert expert : experts) {
            final JSONObject expertJson = new JSONObject();
            expertJson.put(ID_FIELD, expert.getId());
            expertJson.put(CHATHISTORY_FIELD, new JSONArray(expert.getChatHistory()));
            expertsArray.put(expertJson);
        }
        return expertsArray.toString();
    }

    private List<Expert> expertsFromJson(String expertsJson) {
        final List<Expert> experts = new ArrayList<>();
        final JSONArray expertsArray = new JSONArray(expertsJson);

        for (int i = 0; i < expertsArray.length(); i++) {
            final JSONObject expertJson = expertsArray.getJSONObject(i);

            final List<String> chatHistory = new ArrayList<>();
            final JSONArray chatHistoryArray = expertJson.getJSONArray(CHATHISTORY_FIELD);
            for (int j = 0; j < chatHistoryArray.length(); j++) {
                chatHistory.add(chatHistoryArray.getString(j));
            }

            final Expert expert = new Expert(expertJson.getString(ID_FIELD));
            expert.setChatHistory(chatHistory);
            experts.add(expert);
        }
        return experts;
    }

    private String pitchesToJson(List<Pitch> pitches) {
        final JSONArray pitchesArray = new JSONArray();
        for (Pitch pitch : pitches) {
            final JSONObject pitchJson = new JSONObject();
            pitchJson.put(ID_FIELD, pitch.getPitchID());
            pitchJson.put(NAME_FIELD, pitch.getName());
            pitchJson.put("image", pitch.getImage());
            pitchJson.put("description", pitch.getDescription());
            pitchJson.put("targetAudienceList", new JSONArray(pitch.getTargetAudienceList()));

            final JSONArray personasArray = new JSONArray();
            for (Persona persona : pitch.getPersonas()) {
                personasArray.put(personaToJson(persona));
            }
            pitchJson.put("personas", personasArray);
            pitchJson.put("detailedTargetAudienceMap", detailedTargetAudienceMapToJson(pitch
                    .getDetailedTargetAudienceMap()));

            pitchesArray.put(pitchJson);
        }
        return pitchesArray.toString();
    }

    private List<Pitch> pitchesFromJson(String pitchesJson) {
        final List<Pitch> pitches = new ArrayList<>();
        final JSONArray pitchesArray = new JSONArray(pitchesJson);

        for (int i = 0; i < pitchesArray.length(); i++) {
            final JSONObject pitchJson = pitchesArray.getJSONObject(i);

            final List<String> targetAudienceList = new ArrayList<>();
            final JSONArray targetAudienceArray = pitchJson.getJSONArray("targetAudienceList");
            for (int j = 0; j < targetAudienceArray.length(); j++) {
                targetAudienceList.add(targetAudienceArray.getString(j));
            }

            final Pitch pitch = new Pitch(
                    pitchJson.getString(ID_FIELD),
                    pitchJson.getString(NAME_FIELD),
                    pitchJson.getString("image"),
                    pitchJson.getString("description"),
                    targetAudienceList
            );

            final JSONArray personasArray = pitchJson.getJSONArray("personas");
            for (int j = 0; j < personasArray.length(); j++) {
                pitch.getPersonas().add(personaFromJson(personasArray.getJSONObject(j)));
            }

            pitch.setDetailedTargetAudienceMap(detailedTargetAudienceMapFromJson(pitchJson
                    .getJSONObject("detailedTargetAudienceMap")));
            pitches.add(pitch);
        }
        return pitches;
    }

    private JSONObject detailedTargetAudienceMapToJson(Map<String, DetailedTargetAudience> detailedTargetAudienceMap) {
        final JSONObject detailedTargetAudienceMapJson = new JSONObject();
        for (Map.Entry<String, DetailedTargetAudience> entry : detailedTargetAudienceMap.entrySet()) {
            final DetailedTargetAudience detailedTargetAudience = entry.getValue();
            final JSONObject detailedTargetAudienceJson = new JSONObject();
            detailedTargetAudienceJson.put(NAME_FIELD, detailedTargetAudience.getName());
            detailedTargetAudienceJson.put("minAge", detailedTargetAudience.getMinAge());
            detailedTargetAudienceJson.put("maxAge", detailedTargetAudience.getMaxAge());
            detailedTargetAudienceJson.put(GENDER_FIELD, detailedTargetAudience.getGender());
            detailedTargetAudienceJson.put("educationLevel", detailedTargetAudience.getEducationLevel());
            detailedTargetAudienceJson.put(OCCUPATION_FIELD, detailedTargetAudience.getOccupation());
            detailedTargetAudienceJson.put("incomeLevel", detailedTargetAudience.getIncomeLevel());
            detailedTargetAudienceJson.put("geographicLocation", detailedTargetAudience.getGeographicLocation());
            detailedTargetAudienceJson.put("interestsAndPassions", new JSONArray(detailedTargetAudience
                    .getInterestsAndPassions()));
            detailedTargetAudienceJson.put("values", new JSONArray(detailedTargetAudience.getValues()));
            detailedTargetAudienceJson.put("personalityTraits", new JSONArray(detailedTargetAudience
                    .getPersonalityTraits()));
            detailedTargetAudienceJson.put("lifestyle", detailedTargetAudience.getLifestyle());
            detailedTargetAudienceJson.put("isEarlyAdopter", detailedTargetAudience.isEarlyAdopter());
            detailedTargetAudienceJson.put("techSavviness", detailedTargetAudience.getTechSavviness());
            detailedTargetAudienceJson.put("gadgetOwnership", new JSONArray(detailedTargetAudience
                    .getGadgetOwnership()));
            detailedTargetAudienceJson.put("mediaConsumption", new JSONArray(detailedTargetAudience
                    .getMediaConsumption()));
            detailedTargetAudienceJson.put("onlineEngagement", new JSONArray(detailedTargetAudience
                    .getOnlineEngagement()));
            detailedTargetAudienceJson.put("isInfluencer", detailedTargetAudience.isInfluencer());
            detailedTargetAudienceJson.put("eventParticipation", new JSONArray(detailedTargetAudience
                    .getEventParticipation()));
            detailedTargetAudienceJson.put("hobbies", new JSONArray(detailedTargetAudience.getHobbies()));
            detailedTargetAudienceJson.put("brandAffinity", new JSONArray(detailedTargetAudience.getBrandAffinity()));
            detailedTargetAudienceJson.put("environmentalConcerns", detailedTargetAudience.isEnvironmentalConcerns());
            detailedTargetAudienceJson.put("globalPerspective", detailedTargetAudience.isGlobalPerspective());
            detailedTargetAudienceJson.put("multilingualAbilities", detailedTargetAudience.isMultilingualAbilities());

            detailedTargetAudienceMapJson.put(entry.getKey(), detailedTargetAudienceJson);
        }
        return detailedTargetAudienceMapJson;
    }

    private Map<String, DetailedTargetAudience> detailedTargetAudienceMapFromJson(
            JSONObject detailedTargetAudienceMapJson) {
        final Map<String, DetailedTargetAudience> detailedTargetAudienceMap = new HashMap<>();
        for (String key : detailedTargetAudienceMapJson.keySet()) {
            final JSONObject detailedTargetAudienceJson = detailedTargetAudienceMapJson.getJSONObject(key);
            final DetailedTargetAudience detailedTargetAudience = new DetailedTargetAudience(detailedTargetAudienceJson
                    .getString(NAME_FIELD));
            detailedTargetAudience.setMinAge(detailedTargetAudienceJson.getInt("minAge"));
            detailedTargetAudience.setMaxAge(detailedTargetAudienceJson.getInt("maxAge"));
            detailedTargetAudience.setGender(detailedTargetAudienceJson.getString(GENDER_FIELD));
            detailedTargetAudience.setEducationLevel(detailedTargetAudienceJson.getString("educationLevel"));
            detailedTargetAudience.setOccupation(detailedTargetAudienceJson.getString(OCCUPATION_FIELD));
            detailedTargetAudience.setIncomeLevel(detailedTargetAudienceJson.getString("incomeLevel"));
            detailedTargetAudience.setGeographicLocation(detailedTargetAudienceJson.getString("geographicLocation"));
            detailedTargetAudience.setInterestsAndPassions(convertJsonArrayToStringList(detailedTargetAudienceJson
                            .getJSONArray("interestsAndPassions")));
            detailedTargetAudience.setValues(convertJsonArrayToStringList(detailedTargetAudienceJson
                    .getJSONArray("values")));
            detailedTargetAudience.setPersonalityTraits(convertJsonArrayToStringList(detailedTargetAudienceJson
                    .getJSONArray("personalityTraits")));
            detailedTargetAudience.setLifestyle(detailedTargetAudienceJson.getString("lifestyle"));
            detailedTargetAudience.setEarlyAdopter(detailedTargetAudienceJson.getBoolean("isEarlyAdopter"));
            detailedTargetAudience.setTechSavviness(detailedTargetAudienceJson.getString("techSavviness"));
            detailedTargetAudience.setGadgetOwnership(convertJsonArrayToStringList(detailedTargetAudienceJson
                    .getJSONArray("gadgetOwnership")));
            detailedTargetAudience.setMediaConsumption(convertJsonArrayToStringList(detailedTargetAudienceJson
                    .getJSONArray("mediaConsumption")));
            detailedTargetAudience.setOnlineEngagement(convertJsonArrayToStringList(detailedTargetAudienceJson
                    .getJSONArray("onlineEngagement")));
            detailedTargetAudience.setInfluencer(detailedTargetAudienceJson.getBoolean("isInfluencer"));
            detailedTargetAudience.setEventParticipation(convertJsonArrayToStringList(detailedTargetAudienceJson
                    .getJSONArray("eventParticipation")));
            detailedTargetAudience.setHobbies(convertJsonArrayToStringList(detailedTargetAudienceJson
                    .getJSONArray("hobbies")));
            detailedTargetAudience.setBrandAffinity(convertJsonArrayToStringList(detailedTargetAudienceJson
                    .getJSONArray("brandAffinity")));
            detailedTargetAudience.setEnvironmentalConcerns(detailedTargetAudienceJson
                    .getBoolean("environmentalConcerns"));
            detailedTargetAudience.setGlobalPerspective(detailedTargetAudienceJson.getBoolean("globalPerspective"));
            detailedTargetAudience.setMultilingualAbilities(detailedTargetAudienceJson
                    .getBoolean("multilingualAbilities"));

            detailedTargetAudienceMap.put(key, detailedTargetAudience);
        }
        return detailedTargetAudienceMap;
    }

    private List<String> convertJsonArrayToStringList(JSONArray jsonArray) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    private JSONObject personaToJson(Persona persona) {
        final JSONObject personaJson = new JSONObject();
        personaJson.put("personaID", persona.getPersonaID());
        personaJson.put(NAME_FIELD, persona.getName());
        personaJson.put("age", persona.getAge());
        personaJson.put(GENDER_FIELD, persona.getGender());
        personaJson.put(OCCUPATION_FIELD, persona.getOccupation());
        personaJson.put("location", persona.getLocation());
        personaJson.put("education", persona.getEducation());
        personaJson.put("salaryRange", persona.getSalaryRange());
        personaJson.put("about", persona.getAbout());
        personaJson.put("stats", persona.getStats());
        personaJson.put("avatar", persona.getAvatar());
        personaJson.put("interests", new JSONArray(persona.getInterests()));
        personaJson.put(CHATHISTORY_FIELD, new JSONArray(persona.getChatHistory()));
        return personaJson;
    }

    private Persona personaFromJson(JSONObject personaJson) {
        final Persona persona = new Persona();
        persona.setPersonaID(personaJson.getInt("personaID"));
        persona.setName(personaJson.getString(NAME_FIELD));
        persona.setAge(personaJson.getInt("age"));
        persona.setGender(personaJson.getString(GENDER_FIELD));
        persona.setOccupation(personaJson.getString(OCCUPATION_FIELD));
        persona.setLocation(personaJson.getString("location"));
        persona.setEducation(personaJson.optString("education", ""));
        persona.setSalaryRange(personaJson.optString("salaryRange", ""));
        persona.setAbout(personaJson.optString("about", ""));
        persona.setStats(personaJson.optString("stats", ""));
        persona.setAvatar(personaJson.optString("avatar", ""));

        final List<String> interests = new ArrayList<>();
        final JSONArray interestsArray = personaJson.optJSONArray("interests");
        if (interestsArray != null) {
            for (int i = 0; i < interestsArray.length(); i++) {
                interests.add(interestsArray.getString(i));
            }
        }
        persona.setInterests(interests);

        final List<String> chatHistory = new ArrayList<>();
        final JSONArray chatHistoryArray = personaJson.optJSONArray(CHATHISTORY_FIELD);
        if (chatHistoryArray != null) {
            for (int i = 0; i < chatHistoryArray.length(); i++) {
                chatHistory.add(chatHistoryArray.getString(i));
            }
        }
        persona.setChatHistory(chatHistory);

        return persona;
    }

}
