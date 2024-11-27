package use_case.set_targetaudience;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data_access.DetailedDataAccessObjectInterface;
import entity.DetailedTargetAudience;

/**
 * Class for the Detailed Target Audience Interactor.
 */
public class DetailedInteractor implements DetailedInputBoundary {

    private final DetailedDataAccessObjectInterface dataAccess;

    public DetailedInteractor(DetailedDataAccessObjectInterface dataAccess) {
        if (dataAccess == null) {
            throw new IllegalArgumentException("Data access object cannot be null.");
        }
        this.dataAccess = dataAccess;
    }

    @Override
    public List<DetailedTargetAudience> fetchDetailedTargetAudience(String audienceCategory) throws Exception {
        final String systemMessage = """
                Provide a detailed analysis of the target audience category "%s".\s
                Structure your response in JSON format with the following fields:
                {
                  "Name": "",
                  "DemographicAttributes": {
                    "MinAge": 0,
                    "MaxAge": 0,
                    "Gender": "",
                    "EducationLevel": "",
                    "Occupation": "",
                    "IncomeLevel": "",
                    "GeographicLocation": ""
                  },
                  "PsychographicAttributes": {
                    "InterestsAndPassions": [],
                    "Values": [],
                    "PersonalityTraits": [],
                    "Lifestyle": ""
                  },
                  "BehavioralAttributes": {
                    "IsEarlyAdopter": false,
                    "TechSavviness": "",
                    "GadgetOwnership": [],
                    "MediaConsumption": [],
                    "OnlineEngagement": [],
                    "IsInfluencer": false
                  },
                  "OtherAttributes": {
                    "EventParticipation": [],
                    "Hobbies": [],
                    "BrandAffinity": [],
                    "EnvironmentalConcerns": false,
                    "GlobalPerspective": false,
                    "MultilingualAbilities": false
                  }
                }""".formatted(audienceCategory);

        final String response = dataAccess.utilizeApi(systemMessage, audienceCategory);

        return parseDetailedTargetAudience(response);
    }

    private List<DetailedTargetAudience> parseDetailedTargetAudience(String response) {
        final List<DetailedTargetAudience> audienceList = new ArrayList<>();

        try {
            final JSONObject jsonResponse = new JSONObject(response);

            if (!jsonResponse.has("detailedTargetAudiences")) {
                throw new IllegalArgumentException("JSON response does not contain 'detailedTargetAudiences'.");
            }

            final JSONArray audienceArray = jsonResponse.getJSONArray("detailedTargetAudiences");

            for (int i = 0; i < audienceArray.length(); i++) {
                final JSONObject audienceJson = audienceArray.getJSONObject(i);

                final String name = audienceJson.optString("Name", "unknown Audience");

                final DetailedTargetAudience audience = new DetailedTargetAudience(name);

                // Handle demographic attributes
                final JSONObject demographicAttributes = audienceJson.optJSONObject("DemographicAttributes");
                if (demographicAttributes != null) {
                    audience.setMinAge(demographicAttributes.optInt("MinAge", 0));
                    audience.setMaxAge(demographicAttributes.optInt("MaxAge", 0));
                    final String unknown = "unknown";
                    audience.setGender(demographicAttributes.optString("Gender", unknown));
                    audience.setEducationLevel(demographicAttributes.optString("EducationLevel", unknown));
                    audience.setOccupation(demographicAttributes.optString("Occupation", unknown));
                    audience.setIncomeLevel(demographicAttributes.optString("IncomeLevel", unknown));
                    audience.setGeographicLocation(demographicAttributes.optString("GeographicLocation", unknown));
                }

                final JSONObject psychographicAttributes = audienceJson
                        .getJSONObject("PsychographicAttributes");
                audience.setInterestsAndPassions(jsonArrayToList(psychographicAttributes
                        .getJSONArray("InterestsAndPassions")));
                audience.setValues(jsonArrayToList(psychographicAttributes.getJSONArray("Values")));
                audience.setPersonalityTraits(jsonArrayToList(psychographicAttributes
                        .getJSONArray("PersonalityTraits")));
                audience.setLifestyle(psychographicAttributes.getString("Lifestyle"));

                final JSONObject behavioralAttributes = audienceJson.getJSONObject("BehavioralAttributes");
                audience.setEarlyAdopter(behavioralAttributes.getBoolean("IsEarlyAdopter"));
                audience.setTechSavviness(behavioralAttributes.getString("TechSavviness"));
                audience.setGadgetOwnership(jsonArrayToList(behavioralAttributes
                        .getJSONArray("GadgetOwnership")));
                audience.setMediaConsumption(jsonArrayToList(behavioralAttributes
                        .getJSONArray("MediaConsumption")));
                audience.setOnlineEngagement(jsonArrayToList(behavioralAttributes
                        .getJSONArray("OnlineEngagement")));
                audience.setInfluencer(behavioralAttributes.getBoolean("IsInfluencer"));

                final JSONObject otherAttributes = audienceJson.getJSONObject("OtherAttributes");
                audience.setEventParticipation(jsonArrayToList(otherAttributes
                        .getJSONArray("EventParticipation")));
                audience.setHobbies(jsonArrayToList(otherAttributes.getJSONArray("Hobbies")));
                audience.setBrandAffinity(jsonArrayToList(otherAttributes.getJSONArray("BrandAffinity")));
                audience.setEnvironmentalConcerns(otherAttributes.getBoolean("EnvironmentalConcerns"));
                audience.setGlobalPerspective(otherAttributes.getBoolean("GlobalPerspective"));
                audience.setMultilingualAbilities(otherAttributes.getBoolean("MultilingualAbilities"));

                audienceList.add(audience);
            }
        }
        catch (JSONException exception) {
            throw new IllegalArgumentException("Error parsing DetailedTargetAudience JSON response: "
                                               + exception.getMessage(), exception);
        }

        return audienceList;
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

}
