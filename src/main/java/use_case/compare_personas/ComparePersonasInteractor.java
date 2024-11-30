package use_case.compare_personas;

import data_access.ComparePersonasGptAccessInterface;
import entity.ChatMessage;
import entity.Persona;

import java.util.ArrayList;
import java.util.List;

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
     * @param inputData The input data containing the personas to be compared.
     */
    @Override
    public void execute(ComparePersonasInputData inputData) {
        List<Persona> personas = inputData.getPersonas();
        if (personas.size() != 2) {
            outputBoundary.prepareFailView("Exactly two personas must be selected for comparison.");
            return;
        }

        Persona persona1 = personas.get(0);
        Persona persona2 = personas.get(1);

        // Prepare messages for API call
        List<ChatMessage> messagesForApi = new ArrayList<>();
        String systemMessage = "You are tasked with analyzing and comparing two user personas and their opinions about a product pitch. Please provide concise responses.";
        messagesForApi.add(new ChatMessage("system", systemMessage));

        // Add user messages for both personas
        messagesForApi.add(new ChatMessage("user", String.format(
                "Persona 1 (%s): %s. Give 3 concise reasons for liking or disliking the pitch. Start each with the word 'likes' or 'dislikes'.",
                persona1.getName(), persona1.getAbout())));

        messagesForApi.add(new ChatMessage("user", String.format(
                "Persona 2 (%s): %s. Give 3 concise reasons for liking or disliking the pitch. Start each with the word 'likes' or 'dislikes'.",
                persona2.getName(), persona2.getAbout())));

        // Add final prompt to compare both personas
        messagesForApi.add(new ChatMessage("user",
                "Based on the above responses, compare the two personas. What are their similarities and differences regarding their opinions on the pitch? Start each with 'similar' or 'different'"));

        try {
            // Using getOpinion to call the API
            String comparisonResponse = chatgptDataAccessObject.getInteraction(messagesForApi);

            // Parse the response into opinions and comparison
            String[] responseParts = comparisonResponse.split("\n\n");
            if (responseParts.length < 3) {
                outputBoundary.prepareFailView("Unexpected response format from API.");
                return;
            }

            String persona1Opinion = responseParts[0];
            String persona2Opinion = responseParts[1];
            String comparison = responseParts[2];

            // Create output data and pass it to output boundary
            ComparePersonasOutputData outputData = new ComparePersonasOutputData(persona1, persona2, persona1Opinion, persona2Opinion, extractSimilarities(comparison), extractDifferences(comparison));
            outputBoundary.presentComparison(outputData);
        } catch (Exception e) {
            outputBoundary.prepareFailView("Unable to retrieve data: " + e.getMessage());
            e.printStackTrace();
        }
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