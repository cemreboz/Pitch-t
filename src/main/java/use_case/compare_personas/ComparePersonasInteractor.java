package use_case.compare_personas;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Persona;
import entity.Pitch;

/**
 * Interactor class for the Compare Personas use case.
 * Implements the business logic of comparing two personas.
 */
public class ComparePersonasInteractor implements ComparePersonasInputBoundary {

    private final ComparePersonasOutputBoundary outputBoundary;
    private final ComparePersonasGptAccessInterface chatgptDataAccessObject;
    private final ComparePersonasDataAccessInterface comparePersonasDataAccessObject;
    private final String newline = "\n";

    /**
     * Constructs a ComparePersonasInteractor object.
     *
     * @param chatgptDataAccessObject The ChatGPT DAO interface.
     * @param outputBoundary          The output boundary interface.
     * @param comparePersonasDataAccessObject current user DAO.
     */
    public ComparePersonasInteractor(ComparePersonasGptAccessInterface chatgptDataAccessObject,
                                     ComparePersonasOutputBoundary outputBoundary,
                                     ComparePersonasDataAccessInterface comparePersonasDataAccessObject) {
        this.chatgptDataAccessObject = chatgptDataAccessObject;
        this.outputBoundary = outputBoundary;
        this.comparePersonasDataAccessObject = comparePersonasDataAccessObject;
    }

    /**
     * Executes the compare personas use case.
     *
     * @param inputData The input data containing the personas and the current pitch.
     */
    @Override
    public void execute(ComparePersonasInputData inputData) {
        final List<Persona> personas = inputData.getPersonas();
        final Pitch currentPitch = inputData.getCurrentPitch();

        final Persona persona1 = personas.get(0);
        final Persona persona2 = personas.get(1);

        if (persona1 == null || persona2 == null) {
            outputBoundary.prepareFailView("Exactly two personas must be selected for comparison.");
        }

        // Create a descriptive system message to send to GPT
        final String systemMessage = createComparisonSystemMessage(currentPitch, persona1, persona2);

        try {
            // Call the GPT API
            final String comparisonResponse = chatgptDataAccessObject.utilizeApi(systemMessage);

            // Parse the response
            final JSONObject jsonResponse = new JSONObject(comparisonResponse);

            // Parse persona 1 opinion
            final JSONObject persona1OpinionJson = jsonResponse.getJSONObject("persona1_opinion");
            final String p1Opinion = parseOpinion(persona1OpinionJson);

            // Parse persona 2 opinion
            final JSONObject persona2OpinionJson = jsonResponse.getJSONObject("persona2_opinion");
            final String p2Opinion = parseOpinion(persona2OpinionJson);

            // Parse similarities and differences
            final JSONArray similaritiesJson = jsonResponse.getJSONObject("comparison").getJSONArray("similarities");
            final JSONArray differencesJson = jsonResponse.getJSONObject("comparison").getJSONArray("differences");

            final List<String> similarities = jsonArrayToList(similaritiesJson);
            final List<String> differences = jsonArrayToList(differencesJson);

            // Create output data
            final ComparePersonasOutputData outputData = new ComparePersonasOutputData(
                    persona1, persona2, p1Opinion, p2Opinion, similarities, differences,
                    comparePersonasDataAccessObject.getCurrentUser().getName(),
                    comparePersonasDataAccessObject.getCurrentUser().getPassword());

            // Present the comparison result
            outputBoundary.prepareSuccessView(outputData);

        }
        catch (JSONException exception) {
            outputBoundary.prepareFailView("Unable to parse response: " + exception.getMessage());
        }
        catch (Exception exception) {
            outputBoundary.prepareFailView("Unable to retrieve data: " + exception.getMessage());
        }
    }

    /**
     * Creates the system message for persona comparison for the GPT API.
     *
     * @param pitch    The current pitch.
     * @param persona1 The first persona.
     * @param persona2 The second persona.
     * @return The constructed system message as a String.
     */
    private String createComparisonSystemMessage(Pitch pitch, Persona persona1, Persona persona2) {

        // Construct a descriptive system message for GPT API
        final StringBuilder sb = new StringBuilder();
        sb.append("You are an assistant that helps analyze and compare user personas based on a given product "
                + "pitch.\n");
        sb.append("The product pitch details are as follows:\n");
        sb.append("Pitch Name: ").append(pitch.getName()).append(newline);
        sb.append("Description: ").append(pitch.getDescription()).append(newline);
        sb.append("Target Audiences: ").append(String.join(", ", pitch.getTargetAudienceList())).append("\n\n");

        sb.append("For each persona, provide an opinion based on the pitch. Each opinion should "
                + "consist of likes and dislikes.\n");
        sb.append("Below are the details for each persona:\n");

        // Add persona 1 details
        sb.append("Persona 1:\n");
        sb.append("Name: ").append(persona1.getName()).append(newline);
        sb.append("Age: ").append(persona1.getAge()).append(newline);
        sb.append("Gender: ").append(persona1.getGender()).append(newline);
        sb.append("Occupation: ").append(persona1.getOccupation()).append(newline);
        sb.append("About: ").append(persona1.getAbout()).append(newline);
        sb.append("\nBased on the pitch, provide Persona 1's thoughts with the following details:\n");
        sb.append("- Likes: List of key aspects of the pitch that Persona 1 likes.\n");
        sb.append("- Dislikes: List of key aspects of the pitch that Persona 1 dislikes.\n");

        // Add persona 2 details
        sb.append("\nPersona 2:\n");
        sb.append("Name: ").append(persona2.getName()).append(newline);
        sb.append("Age: ").append(persona2.getAge()).append(newline);
        sb.append("Gender: ").append(persona2.getGender()).append(newline);
        sb.append("Occupation: ").append(persona2.getOccupation()).append(newline);
        sb.append("About: ").append(persona2.getAbout()).append(newline);
        sb.append("\nBased on the pitch, provide Persona 2's thoughts with the following details:\n");
        sb.append("- Likes: List of key aspects of the pitch that Persona 2 likes.\n");
        sb.append("- Dislikes: List of key aspects of the pitch that Persona 2 dislikes.\n");

        // Request for comparison between the two personas
        sb.append("\nNext, compare the opinions of Persona 1 and Persona 2 regarding the pitch.\n");
        sb.append("Provide a comparison summary in the following format:\n");
        sb.append("- Similarities: List of aspects that both Persona 1 and Persona 2 agree upon.\n");
        sb.append("- Differences: List of aspects where Persona 1 and Persona 2 have differing opinions.\n");

        // JSON response requirement
        sb.append("\nReturn the response in a valid JSON format with the following structure:\n");
        sb.append("{\n");
        sb.append("    \"persona1_opinion\": {\n");
        sb.append("        \"likes\": [list of strings],\n");
        sb.append("        \"dislikes\": [list of strings]\n");
        sb.append("    },\n");
        sb.append("    \"persona2_opinion\": {\n");
        sb.append("        \"likes\": [list of strings],\n");
        sb.append("        \"dislikes\": [list of strings]\n");
        sb.append("    },\n");
        sb.append("    \"comparison\": {\n");
        sb.append("        \"similarities\": [list of strings],\n");
        sb.append("        \"differences\": [list of strings]\n");
        sb.append("    }\n");
        sb.append("}\n");

        sb.append("\nReply ONLY with the JSON response. Do NOT include any markdown, explanations, or additional "
                + "text.");

        return sb.toString();
    }

    /**
     * Parses an opinion from the JSON object containing 'likes' and 'dislikes'.
     *
     * @param opinionJson The JSON object with 'likes' and 'dislikes'.
     * @return A formatted string representing the persona's opinion.
     */
    private String parseOpinion(JSONObject opinionJson) {
        final StringBuilder opinion = new StringBuilder();

        final JSONArray likesArray = opinionJson.getJSONArray("likes");
        final JSONArray dislikesArray = opinionJson.getJSONArray("dislikes");

        opinion.append("Likes:\n");
        for (int i = 0; i < likesArray.length(); i++) {
            opinion.append("- ").append(likesArray.getString(i)).append(newline);
        }

        opinion.append("\nDislikes:\n");
        for (int i = 0; i < dislikesArray.length(); i++) {
            opinion.append("- ").append(dislikesArray.getString(i)).append(newline);
        }

        return opinion.toString().trim();
    }

    /**
     * Converts a JSONArray to a List of Strings.
     *
     * @param jsonArray The JSONArray to convert.
     * @return A List of Strings representing the contents of the JSONArray.
     */
    private List<String> jsonArrayToList(JSONArray jsonArray) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
