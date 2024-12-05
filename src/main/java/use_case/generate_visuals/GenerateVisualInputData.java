package use_case.generate_visuals;

import entity.Persona;
import entity.Pitch;

/**
 * The input data for the Generate Visual Use Case.
 */
public class GenerateVisualInputData {
    private final String prompt;
    private final Persona persona;
    private final Pitch pitch;

    public GenerateVisualInputData(String prompt, Persona persona, Pitch pitch) {
        this.prompt = prompt;
        this.persona = persona;
        this.pitch = pitch;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getPersonaName() {
        return persona.getName();
    }

    public String getPitchName() {
        return pitch.getName();
    }
}
