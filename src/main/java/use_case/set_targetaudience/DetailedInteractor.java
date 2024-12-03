package use_case.set_targetaudience;

import java.util.ArrayList;
import java.util.List;

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

    public DetailedInteractor(DetailedtaDataAccessInterface dataAccess, DetailedOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
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
            final String response = dataAccess.utilizeApi(systemMessage, inputData.getPitchname() + " "
                    + inputData.getPitchdescription() + " " + inputData.getAudiencecategory());
            final List<DetailedTargetAudience> parseDetailedTargetAudience = parseDetailedTargetAudience(response);
            final DetailedOutputData outputData = new DetailedOutputData(parseDetailedTargetAudience);
            outputBoundary.prepareSuccessView(outputData);
        }
        catch (JSONException exception) {
            outputBoundary.prepareFailView("Error with getting the Detailed Target Audience");
        }

    }

    /**
     * Parses the output of the API call.
     * @param response what the API call returns.
     * @return the parsed response of the API call.
     * @throws IllegalArgumentException if the argument is invalid.
     */
    private List<DetailedTargetAudience> parseDetailedTargetAudience(String response) {
        final List<DetailedTargetAudience> audienceList = new ArrayList<>();

        try {
            // Trim the response
            final String trimmedResponse = response.trim();
            final JSONObject jsonResponse = new JSONObject(trimmedResponse);

            // Directly parse the attributes
            final String name = jsonResponse.optString("Name", "unknown Audience");
            final DetailedTargetAudience audience = new DetailedTargetAudience(name);

            // Handle demographic attributes
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

            // Handle psychographic attributes
            final JSONObject psychographicAttributes = jsonResponse.optJSONObject("PsychographicAttributes");
            if (psychographicAttributes != null) {
                audience.setInterestsAndPassions(jsonArrayToList(psychographicAttributes
                        .optJSONArray("InterestsAndPassions")));
                audience.setValues(jsonArrayToList(psychographicAttributes.optJSONArray("Values")));
                audience.setPersonalityTraits(jsonArrayToList(psychographicAttributes
                        .optJSONArray("PersonalityTraits")));
                audience.setLifestyle(psychographicAttributes.optString("Lifestyle", "unknown"));
            }

            // Handle behavioral attributes
            final JSONObject behavioralAttributes = jsonResponse.optJSONObject("BehavioralAttributes");
            if (behavioralAttributes != null) {
                audience.setEarlyAdopter(behavioralAttributes.optBoolean("IsEarlyAdopter", false));
                audience.setTechSavviness(behavioralAttributes.optString("TechSavviness", "unknown"));
                audience.setGadgetOwnership(jsonArrayToList(behavioralAttributes.optJSONArray("GadgetOwnership")));
                audience.setMediaConsumption(jsonArrayToList(behavioralAttributes.optJSONArray("MediaConsumption")));
                audience.setOnlineEngagement(jsonArrayToList(behavioralAttributes.optJSONArray("OnlineEngagement")));
                audience.setInfluencer(behavioralAttributes.optBoolean("IsInfluencer", false));
            }

            // Handle other attributes
            final JSONObject otherAttributes = jsonResponse.optJSONObject("OtherAttributes");
            if (otherAttributes != null) {
                audience.setEventParticipation(jsonArrayToList(otherAttributes.optJSONArray("EventParticipation")));
                audience.setHobbies(jsonArrayToList(otherAttributes.optJSONArray("Hobbies")));
                audience.setBrandAffinity(jsonArrayToList(otherAttributes.optJSONArray("BrandAffinity")));
                audience.setEnvironmentalConcerns(otherAttributes.optBoolean("EnvironmentalConcerns", false));
                audience.setGlobalPerspective(otherAttributes.optBoolean("GlobalPerspective", false));
                audience.setMultilingualAbilities(otherAttributes.optBoolean("MultilingualAbilities", false));
            }

            // Add the parsed audience to the list
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
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}