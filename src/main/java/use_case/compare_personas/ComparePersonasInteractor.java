package use_case.compare_personas;

import java.util.ArrayList;
import java.util.List;

import entity.ChatMessage;
import entity.Persona;
import entity.Pitch;

/**
 * Interactor class for the Compare Personas use case.
 * Implements the business logic of comparing two personas.
 */
public class ComparePersonasInteractor implements ComparePersonasInputBoundary {

    private final ComparePersonasOutputBoundary outputBoundary;
    private final ComparePersonasGptAccessInterface chatgptDataAccessObject;

    /**
     * Constructs a ComparePersonasInteractor object.
     *
     * @param chatgptDataAccessObject The ChatGPT DAO interface.
     * @param outputBoundary          The output boundary interface.
     */
    public ComparePersonasInteractor(ComparePersonasGptAccessInterface chatgptDataAccessObject,
                                     ComparePersonasOutputBoundary outputBoundary) {
        this.chatgptDataAccessObject = chatgptDataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Executes the compare personas use case.
     *
     * @param inputData The input data containing the personas and the current pitch.
     */
    @Override
    public void execute(ComparePersonasInputData inputData) {
        List<Persona> personas = inputData.getPersonas();
        Pitch currentPitch = inputData.getCurrentPitch();

        if (personas.size() != 2) {
            outputBoundary.prepareFailView("Exactly two personas must be selected for comparison.");
            return;
        }

        Persona persona1 = personas.get(0);
        Persona persona2 = personas.get(1);

        // Prepare messages for API call
        List<ChatMessage> messagesForApi = new ArrayList<>();
        String systemMessage = "You are an assistant that analyzes and compares two user personas' opinions about a product pitch. Provide structured and concise responses.";
        messagesForApi.add(new ChatMessage("system", systemMessage));

        // Include pitch description
        messagesForApi.add(new ChatMessage("user", "Here is the product pitch description:"));
        messagesForApi.add(new ChatMessage("user", currentPitch.getDescription()));

        // Add persona 1 information and ask for opinion
        messagesForApi.add(new ChatMessage("user", String.format(
                "Persona 1 (%s): %s", persona1.getName(), persona1.getAbout())));
        messagesForApi.add(new ChatMessage("user", String.format(
                "Based on the pitch, what are %s's thoughts? Provide 3 concise points starting with 'Likes:' or 'Dislikes:'.",
                persona1.getName())));

        // Add persona 2 information and ask for opinion
        messagesForApi.add(new ChatMessage("user", String.format(
                "Persona 2 (%s): %s", persona2.getName(), persona2.getAbout())));
        messagesForApi.add(new ChatMessage("user", String.format(
                "Based on the pitch, what are %s's thoughts? Provide 3 concise points starting with 'Likes:' or 'Dislikes:'.",
                persona2.getName())));

        // Ask to compare the opinions
        messagesForApi.add(new ChatMessage("user",
                "Compare the opinions of both personas. List similarities and differences starting with 'Similar:' or 'Different:'."));

        try {
            // Call the GPT API
            String comparisonResponse = chatgptDataAccessObject.utilizeApi(messagesForApi);

            // Parse the response
            String[] responseParts = comparisonResponse.split("\n\n");

            if (responseParts.length < 3) {
                outputBoundary.prepareFailView("Unexpected response format from API.");
                return;
            }

            String p1Opinion = parseOpinion(responseParts[0]);
            String p2Opinion = parseOpinion(responseParts[1]);
            String comparison = responseParts[2];

            // Extract similarities and differences
            List<String> similarities = extractSimilarities(comparison);
            List<String> differences = extractDifferences(comparison);

            // Create output data
            ComparePersonasOutputData outputData = new ComparePersonasOutputData(
                    persona1, persona2, p1Opinion, p2Opinion, similarities, differences);

            // Present the comparison result
            outputBoundary.prepareSuccessView(outputData);

        } catch (Exception e) {
            outputBoundary.prepareFailView("Unable to retrieve data: " + e.getMessage());
        }
    }

    /**
     * Parses the opinion text to extract concise points.
     *
     * @param opinionText The raw opinion text from the GPT response.
     * @return A formatted opinion string with concise points.
     */
    private String parseOpinion(String opinionText) {
        // Assuming the opinion text contains likes and dislikes, format them as needed
        return opinionText.trim();
    }

    /**
     * Extracts similarities from the comparison text.
     *
     * @param comparison The comparison text from ChatGPT.
     * @return A list of similarities.
     */
    private List<String> extractSimilarities(String comparison) {
        List<String> similarities = new ArrayList<>();
        for (String line : comparison.split("\n")) {
            if (line.toLowerCase().startsWith("similar")) {
                similarities.add(line);
            }
        }
        return similarities;
    }

    /**
     * Extracts differences from the comparison text.
     *
     * @param comparison The comparison text from ChatGPT.
     * @return A list of differences.
     */
    private List<String> extractDifferences(String comparison) {
        List<String> differences = new ArrayList<>();
        for (String line : comparison.split("\n")) {
            if (line.toLowerCase().startsWith("different")) {
                differences.add(line);
            }
        }
        return differences;
    }
}