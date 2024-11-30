package use_case.compare_personas;

import data_access.ChatgptDataAccessInterface;
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
    private final ChatgptDataAccessInterface chatgptDataAccessObject;

    /**
     * Constructs a ComparePersonasInteractor object.
     *
     * @param chatgptDataAccessObject The ChatGPT DAO interface.
     * @param outputBoundary          The output boundary interface.
     */
    public ComparePersonasInteractor(ChatgptDataAccessInterface chatgptDataAccessObject,
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
            throw new IllegalArgumentException("Exactly two personas must be selected for comparison.");
        }

        Persona persona1 = personas.get(0);
        Persona persona2 = personas.get(1);

        // Prepare messages for API call
        List<ChatMessage> messagesForApi = new ArrayList<>();
        String systemMessage = "You are tasked with analyzing and comparing two user personas and their opinions about a product pitch. Please provide concise responses.";
        messagesForApi.add(new ChatMessage("system", systemMessage));

        // Add user messages for both personas
        messagesForApi.add(new ChatMessage("user", String.format(
                "Persona 1 (%s): %s. Give 3 concise reasons for liking or disliking the pitch. Start each with the word 'like' or 'dislike'.",
                persona1.getName(), persona1.getAbout())));

        messagesForApi.add(new ChatMessage("user", String.format(
                "Persona 2 (%s): %s. Give 3 concise reasons for liking or disliking the pitch. Start each with the word 'like' or 'dislike'.",
                persona2.getName(), persona2.getAbout())));

        // Add final prompt to compare both personas
        messagesForApi.add(new ChatMessage("user",
                "Based on the above responses, compare the two personas. What are their similarities and differences regarding their opinions on the pitch?"));

        String comparisonResponse;
        try {
            // Using getOpinion to call the API
            comparisonResponse = chatgptDataAccessObject.getOpinion(messagesForApi);
        }
        catch (Exception e) {
            comparisonResponse = "Unable to retrieve data";
            e.printStackTrace();
        }

        // Create output data and pass it to output boundary
        ComparePersonasOutputData outputData = new ComparePersonasOutputData(comparisonResponse);
        outputBoundary.presentComparison(outputData);
    }
}