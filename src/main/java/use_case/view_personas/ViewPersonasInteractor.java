package use_case.view_personas;

import entity.Persona;
import entity.Pitch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        sb.append("For each persona, provide the following details:\n")
                .append("- Name\n")
                .append("- Age\n")
                .append("- Gender\n")
                .append("- Occupation\n")
                .append("- Interests\n")
                .append("- Salary Range\n")
                .append("- Education\n")
                .append("- About (a paragraph describing the persona)\n")
                .append("- Market Statistics\n\n")
                .append("Present the personas in JSON format as an array.");

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