package use_case.set_targetaudience;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.DBUser;
import entity.Pitch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.DetailedTargetAudience;

/**
 * Class for the Detailed Target Audience Interactor.
 */
public class DetailedInteractor implements DetailedInputBoundary {

    private final DetailedtaDataAccessInterface dataAccess;
    private final DetailedOutputBoundary outputBoundary;
    private final DetailedDataObjectAccessInterface dataObjectAccess;

    public DetailedInteractor(DetailedtaDataAccessInterface dataAccess, DetailedOutputBoundary outputBoundary,
                              DetailedDataObjectAccessInterface dataObjectAccess) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
        this.dataObjectAccess = dataObjectAccess;
    }

    /**
     * Method for executing the DetailedTA based on the input Data.
     *
     * @param inputData from the input data class.
     */
    @Override
    public void execute(DetailedInputData inputData) throws Exception {
        final String systemMessage = """
            Provide a detailed analysis of the pitch name and description and the target audience category "%s".\s
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
            }""".formatted(inputData.getAudiencecategory());

        try {
            String response = dataAccess.utilizeApi(systemMessage, inputData.getPitchname() + " "
                    + inputData.getPitchdescription() + " " + inputData.getAudiencecategory());
            response = response.trim();

            if (response.startsWith("```") && response.endsWith("```")) {
                response = response.substring(3, response.length() - 3).trim();
            }

            if (response.startsWith("json")) {
                response = response.substring(4).trim();
            }

            response = response.replace("\\\"", "\"");

            response = response.replaceAll("[^\\x20-\\x7E]", "");
            if (!response.startsWith("{")) {
                throw new IllegalArgumentException("Unexpected response format: Response does not start with '{'");
            }

            final List<DetailedTargetAudience> parseDetailedTargetAudience = parseDetailedTargetAudience(response);

            final DBUser currentUser = (DBUser) dataObjectAccess.getCurrentUser();
            final Pitch currentPitch = currentUser.getPitch(inputData.getPitchname());
            final List<String> generalTa = currentPitch.getTargetAudienceList();
            final Map<String, DetailedTargetAudience> newMap = new HashMap<String, DetailedTargetAudience>();
            final int size = Math.min(generalTa.size(), parseDetailedTargetAudience.size());
            for (int i = 0; i < size; i++) {
                newMap.put(generalTa.get(i), parseDetailedTargetAudience.get(i));
            }
            currentPitch.setDetailedTargetAudienceMap(newMap);
            final DetailedOutputData outputData = new DetailedOutputData(parseDetailedTargetAudience);
            outputBoundary.prepareSuccessView(outputData);
        }
        catch (JSONException exception) {
            outputBoundary.prepareFailView("Error with getting the Detailed Target Audience");
            throw new IllegalArgumentException("Error parsing DetailedTargetAudience JSON response: "
                    + exception.getMessage(), exception);
        }
    }

    private List<DetailedTargetAudience> parseDetailedTargetAudience(String response) {
        final List<DetailedTargetAudience> audienceList = new ArrayList<>();

        try {
            // Trim the response again to be sure
            final String trimmedResponse = response.trim();
            final JSONObject jsonResponse = new JSONObject(trimmedResponse);

            // You might need to check if jsonResponse has "detailedTargetAudiences"
            // depending on your original parsing logic.
            // However, based on your provided response, it doesn't have that field.

            // Create DetailedTargetAudience object directly from the response JSON
            final String name = jsonResponse.optString("Name", "unknown Audience");
            final DetailedTargetAudience audience = new DetailedTargetAudience(name);

            // Parse Demographic Attributes
            final JSONObject demographicAttributes = jsonResponse.optJSONObject("DemographicAttributes");
            if (demographicAttributes != null) {
                audience.setMinAge(demographicAttributes.optInt("MinAge", 0));
                audience.setMaxAge(demographicAttributes.optInt("MaxAge", 0));
                audience.setGender(demographicAttributes.optString("Gender", "unknown"));
                audience.setEducationLevel(demographicAttributes.optString("EducationLevel", "unknown"));
                audience.setOccupation(demographicAttributes.optString("Occupation", "unknown"));
                audience.setIncomeLevel(demographicAttributes.optString("IncomeLevel", "unknown"));
                audience.setGeographicLocation(demographicAttributes.optString("GeographicLocation", "unknown"));
            }

            // Parse Psychographic Attributes
            final JSONObject psychographicAttributes = jsonResponse.optJSONObject("PsychographicAttributes");
            if (psychographicAttributes != null) {
                audience.setInterestsAndPassions(jsonArrayToList(psychographicAttributes.optJSONArray("InterestsAndPassions")));
                audience.setValues(jsonArrayToList(psychographicAttributes.optJSONArray("Values")));
                audience.setPersonalityTraits(jsonArrayToList(psychographicAttributes.optJSONArray("PersonalityTraits")));
                audience.setLifestyle(psychographicAttributes.optString("Lifestyle", ""));
            }

            // Parse Behavioral Attributes
            final JSONObject behavioralAttributes = jsonResponse.optJSONObject("BehavioralAttributes");
            if (behavioralAttributes != null) {
                audience.setEarlyAdopter(behavioralAttributes.optBoolean("IsEarlyAdopter", false));
                audience.setTechSavviness(behavioralAttributes.optString("TechSavviness", ""));
                audience.setGadgetOwnership(jsonArrayToList(behavioralAttributes.optJSONArray("GadgetOwnership")));
                audience.setMediaConsumption(jsonArrayToList(behavioralAttributes.optJSONArray("MediaConsumption")));
                audience.setOnlineEngagement(jsonArrayToList(behavioralAttributes.optJSONArray("OnlineEngagement")));
                audience.setInfluencer(behavioralAttributes.optBoolean("IsInfluencer", false));
            }

            // Parse Other Attributes
            final JSONObject otherAttributes = jsonResponse.optJSONObject("OtherAttributes");
            if (otherAttributes != null) {
                audience.setEventParticipation(jsonArrayToList(otherAttributes.optJSONArray("EventParticipation")));
                audience.setHobbies(jsonArrayToList(otherAttributes.optJSONArray("Hobbies")));
                audience.setBrandAffinity(jsonArrayToList(otherAttributes.optJSONArray("BrandAffinity")));
                audience.setEnvironmentalConcerns(otherAttributes.optBoolean("EnvironmentalConcerns", false));
                audience.setGlobalPerspective(otherAttributes.optBoolean("GlobalPerspective", false));
                audience.setMultilingualAbilities(otherAttributes.optBoolean("MultilingualAbilities", false));
            }

            audienceList.add(audience);
        }
        catch (JSONException exception) {
            throw new IllegalArgumentException("Error parsing DetailedTargetAudience JSON response: "
                    + exception.getMessage(), exception);
        }

        return audienceList;
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        final List<String> list = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.optString(i, ""));
            }
        }
        return list;
    }

}
