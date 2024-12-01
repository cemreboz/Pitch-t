package use_case.view_personas;

import java.util.List;

import entity.Persona;
import entity.Pitch;

/**
 * Gateway interface for interacting with the GPT service.
 */
public interface ViewPersonasGptDataAccessInterface {
    /**
     * Generates personas based on the pitch.
     *
     * @param pitch the pitch data.
     * @return a list of generated personas.
     * @throws Exception if an error occurs during generation.
     */
    List<Persona> generatePersonas(Pitch pitch) throws Exception;
}
