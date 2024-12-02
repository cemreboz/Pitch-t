package use_case.view_personas;

import entity.Persona;
import entity.Pitch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        try {
            Pitch pitch = inputData.getPitch();
            String systemMessage = createSystemMessage(pitch);

            // Use the GPT API to generate personas based on the pitch details
            String response = gptAccessInterface.utilizeApi(systemMessage);

            // Parse the response to extract personas
            List<Persona> personas = parsePersonasFromJson(response);

            if (personas == null || personas.isEmpty()) {
                viewPersonasOutputBoundary.prepareFailView("No personas generated.");
            } else {
                ViewPersonasOutputData outputData = new ViewPersonasOutputData(personas);
                viewPersonasOutputBoundary.prepareSuccessView(outputData);
            }
        } catch (Exception e) {
            viewPersonasOutputBoundary.prepareFailView("An error occurred while generating personas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String createSystemMessage(Pitch pitch) {
        // Construct a descriptive system message for GPT API
        StringBuilder sb = new StringBuilder();
        sb.append("Generate user personas based on the following pitch details.\n");
        sb.append("Pitch Name: ").append(pitch.getName()).append("\n");
        sb.append("Description: ").append(pitch.getDescription()).append("\n");
        sb.append("Target Audiences: ").append(String.join(", ", pitch.getTargetAudienceList())).append("\n");

        sb.append("Return a valid JSON response that is an array of JSON objects in the following format:\n");
        sb.append("[\n");
        sb.append("    {\n");
        sb.append("        \"name\": \"<NAME>\",\n");
        sb.append("        \"age\": \"<AGE>\",\n");
        sb.append("        \"gender\": \"<GENDER>\",\n");
        sb.append("        \"education\": \"<EDUCATION>\",\n");
        sb.append("        \"salaryRange\": \"<$XXXXXX - $YYYYYY>\",\n");
        sb.append("        \"about\": \"<a paragraph describing the persona>\",\n");
        sb.append("        \"stats\": \"<STATS>\",\n");
        sb.append("        \"location\": \"<CITY, COUNTRY>\",\n");
        sb.append("        \"occupation\": \"<OCCUPATION>\",\n");
        sb.append("        \"interests\": [\"<INTEREST1>\", \"<INTEREST2>\"],\n");
        sb.append("    }\n");
        sb.append("]\n\n");
        sb.append("Do not include any markdown syntax. The response should be exactly a JSON array of objects as above.");

        return sb.toString();
    }

    private List<Persona> parsePersonasFromJson(String jsonString) {
        List<Persona> personas = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(jsonString);
            JSONArray personaArray = jsonResponse.getJSONArray("personas");

            for (int i = 0; i < personaArray.length(); i++) {
                JSONObject personaJson = personaArray.getJSONObject(i);
                Persona persona = new Persona();
                persona.setName(personaJson.getString("name"));
                persona.setAge(personaJson.getInt("age"));
                persona.setGender(personaJson.getString("gender"));
                persona.setOccupation(personaJson.getString("occupation"));
                persona.setLocation(personaJson.getString("location"));
                persona.setAbout(personaJson.getString("about"));
                // Populate other fields as needed
                personas.add(persona);
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Error parsing personas from JSON response", e);
        }
        return personas;
    }
}