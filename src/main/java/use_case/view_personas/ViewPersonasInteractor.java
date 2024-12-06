package use_case.view_personas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.DBUser;
import entity.Persona;
import entity.Pitch;

/**
 * Interactor for the View Personas use case.
 */
public class ViewPersonasInteractor implements ViewPersonasInputBoundary {

    private final ViewPersonasOutputBoundary viewPersonasOutputBoundary;
    private final ViewPersonasGptDataAccessInterface gptAccessInterface;
    private final ViewPersonasDataAccessInterface personaAccessInterface;

    public ViewPersonasInteractor(ViewPersonasGptDataAccessInterface gptAccessInterface,
                                  ViewPersonasOutputBoundary outputBoundary,
                                  ViewPersonasDataAccessInterface personaAccessInterface) {
        this.gptAccessInterface = gptAccessInterface;
        this.viewPersonasOutputBoundary = outputBoundary;
        this.personaAccessInterface = personaAccessInterface;
    }

    @Override
    public void execute(ViewPersonasInputData inputData) {
        final Pitch pitch = inputData.getPitch();
        List<Persona> personas = pitch.getPersonas();

        if (personas == null || personas.isEmpty()) {
            personas = populateFakePersonas(pitch);
            final DBUser user = (DBUser) personaAccessInterface.getCurrentUser();
            final Pitch tempPitch = user.getPitch(pitch.getPitchID());
            tempPitch.setPersonas(personas);
        }

        if (personas == null || personas.isEmpty()) {
            // Personas failed to generate -- treat as a failure, since we won't have anything to display.
            viewPersonasOutputBoundary.prepareFailView("No personas generated.");
        }
        else {
            final ViewPersonasOutputData outputData = new ViewPersonasOutputData(personas, pitch,
                    personaAccessInterface.getCurrentUser().getName(),
                    personaAccessInterface.getCurrentUser().getPassword());
            viewPersonasOutputBoundary.prepareSuccessView(outputData);
        }
    }

    private List<Persona> populateFakePersonas(Pitch pitch) {
        try {
            final String systemMessage = createSystemMessage(pitch);

            // Use the GPT API to generate personas based on the pitch details
            final String response = gptAccessInterface.utilizeApi(systemMessage);

            // Parse the response to extract personas
            return parsePersonasFromJson(response);
        }
        catch (Exception e) {
            final String errorMessage = "An error occurred while generating personas: " + e.getMessage();
            viewPersonasOutputBoundary.prepareFailView(errorMessage);
            throw new IllegalStateException(errorMessage, e);
            // Rethrow the exception to ensure proper test handling
        }
    }

    private String createSystemMessage(Pitch pitch) {
        // Construct a descriptive system message for GPT API
        final StringBuilder sb = new StringBuilder();
        sb.append("Generate user personas based on the following pitch details.\n");
        sb.append("Pitch Name: ").append(pitch.getName()).append("\n");
        sb.append("Description: ").append(pitch.getDescription()).append("\n");
        sb.append("Target Audiences: ").append(String.join(", ", pitch.getTargetAudienceList())).append("\n");

        sb.append("Return a valid JSON response that is an array of JSON objects. "
                +
                "Each target audience is a distinct group. ");
        sb.append("For each given target audience, include a JSON object which contains the the following key, "
                +
                "value pairs:\n");
        sb.append("    \"name\": [string] The name of the persona.\n");
        sb.append("    \"age\": [integer] The age of the persona, in years\n");
        sb.append("    \"gender\": [Male/Female] The gender of the persona.\n");
        sb.append("    \"education\": [string] Maximum level of education achieved by the audience.\n");
        sb.append("    \"salaryRange\": [string] A representative salary range.\n");
        sb.append("    \"about\": [string] A creative but brief summary of the persona in 1 paragraphs.\n");
        sb.append("    \"stats\": [string] A D&D statistics block\n");
        sb.append("    \"location\": [string] A city and country name.\n");
        sb.append("    \"occupation\": [string] A typical occupation for the persona.\n");
        sb.append("    \"interests\": [list of strings] Some inane hobbies to fulfill their miserable lives\n");
        sb.append("\n");
        sb.append("Reply only with the raw JSON response. Do NOT include any markdown syntax.");

        return sb.toString();
    }

    private List<Persona> parsePersonasFromJson(String jsonString) {
        final List<Persona> personas = new ArrayList<>();
        try {
            final JSONArray personaArray = new JSONArray(jsonString);

            for (int i = 0; i < personaArray.length(); i++) {
                final JSONObject personaJson = personaArray.getJSONObject(i);
                final Persona persona = new Persona();
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
        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Error parsing personas from JSON response", e);
        }
        return personas;
    }
}
