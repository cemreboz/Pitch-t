package use_case.compare_personas;

import entity.Persona;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Interactor for the Compare Personas Use Case.
 */
public class ComparePersonasInteractor implements ComparePersonasInputBoundary {
    private final ComparePersonasOutputBoundary outputBoundary;

    public ComparePersonasInteractor(ComparePersonasOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ComparePersonasInputData inputData) {
        List<Persona> personas = inputData.getPersonas();
        Map<String, String> comparisonResults = new HashMap<>();

        if (personas.size() < 2) {
            outputBoundary.prepareFailView("Not enough personas to compare.");
            return;
        }
        else if (personas.size() > 2) {
            outputBoundary.prepareFailView("Too many personas to compare.");
            return;
        }

        // Comparison logic between chat histories of two personas
        Persona persona1 = personas.get(0);
        Persona persona2 = personas.get(1);

        StringBuilder persona1Chats = new StringBuilder();
        for (String chat : persona1.getChatHistory()) {
            persona1Chats.append(chat).append("\n");
        }

        StringBuilder persona2Chats = new StringBuilder();
        for (String chat : persona2.getChatHistory()) {
            persona2Chats.append(chat).append("\n");
        }

        comparisonResults.put("Persona 1 Chat History", persona1Chats.toString());
        comparisonResults.put("Persona 2 Chat History", persona2Chats.toString());

        // Similarity logic
        // Todo: think of a better way to compare chat history
        String similarity = findSimilarities(persona1.getChatHistory(), persona2.getChatHistory());
        comparisonResults.put("Similarities", similarity);

        ComparePersonasOutputData outputData = new ComparePersonasOutputData(comparisonResults);
        outputBoundary.prepareSuccessView(outputData);
    }

    private String findSimilarities(List<String> chatHistory1, List<String> chatHistory2) {
        StringBuilder similarities = new StringBuilder();
        for (String chat1 : chatHistory1) {
            for (String chat2 : chatHistory2) {
                if (chat1.equalsIgnoreCase(chat2)) {
                    similarities.append(chat1).append("\n");
                }
            }
        }

        if (similarities.length() > 0) {
            return similarities.toString();
        }
        else {
            return "No similarities found.";
        }
    }
}
