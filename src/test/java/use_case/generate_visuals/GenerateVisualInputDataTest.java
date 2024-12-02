package use_case.generate_visuals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateVisualInputDataTest {
    @Test
    void testGenerateVisualInputData() {
        // Step 1: Create an instance of GenerateVisualInputData
        String expectedPrompt = "Create a visual tailored for Tech Enthusiast";
        String expectedPersonaName = "Tech Enthusiast";
        String expectedPitchName = "Amazing Product";

        GenerateVisualInputData inputData = new GenerateVisualInputData(expectedPrompt, expectedPersonaName, expectedPitchName);

        // Step 2: Validate constructor values with getters
        assertEquals(expectedPrompt, inputData.getPrompt(), "Prompt should match the expected value");
        assertEquals(expectedPersonaName, inputData.getPersonaName(), "Persona Name should match the expected value");
        assertEquals(expectedPitchName, inputData.getPitchName(), "Pitch Name should match the expected value");
    }
}
