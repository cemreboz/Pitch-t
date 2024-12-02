package use_case.view_personas;

import entity.Persona;
import entity.Pitch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interactor for the View Personas use case.
 */
public class ViewPersonasInteractor implements ViewPersonasInputBoundary {

    private final ViewPersonasOutputBoundary viewPersonasOutputBoundary;
    private final ViewPersonasGptDataAccessInterface gptAccessInterface;

    public ViewPersonasInteractor(ViewPersonasGptDataAccessInterface gptAccessInterface,
                                  ViewPersonasOutputBoundary outputBoundary) {
        this.gptAccessInterface = gptAccessInterface;
        this.viewPersonasOutputBoundary = outputBoundary;
    }

    @Override
    public void execute(ViewPersonasInputData inputData) {
        Pitch pitch = inputData.getPitch();
        List<Persona> personas = populateFakePersonas(pitch);

        if (personas == null || personas.isEmpty()) {
            // Personas failed to generate -- treat as a failure, since we won't have anything to display.
            viewPersonasOutputBoundary.prepareFailView("No personas generated.");
        } else {
            ViewPersonasOutputData outputData = new ViewPersonasOutputData(personas);
            viewPersonasOutputBoundary.prepareSuccessView(outputData);
        }
    }

    /** Populate some fake personas for visualization. */
    private List<Persona> populateFakePersonas(Pitch pitch) {
        try {
            String systemMessage = createSystemMessage(pitch);

            // Use the GPT API to generate personas based on the pitch details
            String response = gptAccessInterface.utilizeApi(systemMessage);

            // Parse the response to extract personas
            return parsePersonasFromJson(response);
        } catch (Exception e) {
            viewPersonasOutputBoundary.prepareFailView("An error occurred while generating personas: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    private String createSystemMessage(Pitch pitch) {
        // Construct a descriptive system message for GPT API
        StringBuilder sb = new StringBuilder();
        sb.append("Generate user personas based on the following pitch details.\n");
        sb.append("Pitch Name: ").append(pitch.getName()).append("\n");
        sb.append("Description: ").append(pitch.getDescription()).append("\n");
        sb.append("Target Audiences: ").append(String.join(", ", pitch.getTargetAudienceList())).append("\n");

        sb.append("Return a valid JSON response that is an array of JSON objects. Each target audience is a distinct group. ");
        sb.append("For each given target audience, include a JSON object which contains the the following key, value pairs:\n");
        sb.append("    \"name\": [string] The name of the persona.\n");
        sb.append("    \"age\": [integer] The age of the persona, in years\n");
        sb.append("    \"gender\": [Male/Female] The gender of the persona.\n");
        sb.append("    \"education\": [string] Maximum level of education achieved by the audience.\n");
        sb.append("    \"salaryRange\": [string] A representative salary range.\n");
        //sb.append("    \"about\": [string] A creative but brief summary of the persona in 1 paragraphs.\n");
        sb.append("    \"stats\": [string] A D&D statistics block\n");
        sb.append("    \"location\": [string] A city and country name.\n");
        sb.append("    \"occupation\": [string] A typical occupation for the persona.\n");
        sb.append("    \"interests\": [list of strings] Some inane hobbies to fulfill their miserable lives\n");
        sb.append("\n");
        sb.append("Reply only with the raw JSON response. Do NOT include any markdown syntax.");

        return sb.toString();
    }

    private List<Persona> parsePersonasFromJson(String jsonString) {
        List<Persona> personas = new ArrayList<>();
        try {
            JSONArray personaArray = new JSONArray(jsonString);

            for (int i = 0; i < personaArray.length(); i++) {
                JSONObject personaJson = personaArray.getJSONObject(i);
                Persona persona = new Persona();
                persona.setPersonaID(i);
                persona.setName(personaJson.getString("name"));
                persona.setAge(personaJson.getInt("age"));
                persona.setGender(personaJson.getString("gender"));
                persona.setEducation(personaJson.getString("education"));
                persona.setSalaryRange(personaJson.getString("salaryRange"));
                persona.setAbout(personaJson.getString("about"));
                persona.setStats(personaJson.getString("stats"));
                persona.setLocation(personaJson.getString("location"));
                persona.setOccupation(personaJson.getString("occupation"));
                persona.setAvatar("woomp woomp");
                persona.setInterests(personaJson.getJSONArray("interests").toList().stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()));
                // Populate other fields as needed
                personas.add(persona);
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Error parsing personas from JSON response", e);
        }
        return personas;
    }
}