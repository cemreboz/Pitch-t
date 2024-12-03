package use_case.generate_visuals;

/**
 * The input data for the Generate Visual Use Case.
 */
public class GenerateVisualInputData {
    private final String prompt;
    private final String personaName;
    private final String pitchName;

    public GenerateVisualInputData(String prompt, String personaName, String pitchName) {
        this.prompt = prompt;
        this.personaName = personaName;
        this.pitchName = pitchName;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getPersonaName() {
        return personaName;
    }

    public String getPitchName() {
        return pitchName;
    }
}
