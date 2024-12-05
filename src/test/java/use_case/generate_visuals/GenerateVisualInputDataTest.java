package use_case.generate_visuals;

import entity.Persona;
import entity.Pitch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateVisualInputDataTest {
    @Test
    void testGenerateVisualInputData() {
        // Step 1: Create mock Persona and Pitch
        Persona persona = new Persona();
        persona.setName("Tech Enthusiast");

        Pitch pitch = new Pitch("pitch123", "Amazing Product", "image_url", "An AI assistant", null);

        // Step 2: Create an instance of GenerateVisualInputData
        String expectedPrompt = "Create a visual tailored for Tech Enthusiast";
        GenerateVisualInputData inputData = new GenerateVisualInputData(expectedPrompt, persona, pitch);

        // Step 3: Validate constructor values with getters
        assertEquals(expectedPrompt, inputData.getPrompt(), "Prompt should match the expected value");
        assertEquals(persona.getName(), inputData.getPersonaName(), "Persona Name should match the expected value");
        assertEquals(pitch.getName(), inputData.getPitchName(), "Pitch Name should match the expected value");
    }
}
