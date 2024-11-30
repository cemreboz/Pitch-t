package use_case.compare_personas;

import java.util.List;

/**
 * Output Data for the Compare Personas Use Case.
 */
public class ComparePersonasOutputData {
    private final String persona1Opinion;
    private final String persona2Opinion;
    private final List<String> similarities;
    private final List<String> differences;

    /**
     * Constructs ComparePersonasOutputData with opinions, similarities, and differences.
     *
     * @param persona1Opinion The opinion generated for persona 1.
     * @param persona2Opinion The opinion generated for persona 2.
     * @param similarities    The list of similarities between the two personas' opinions.
     * @param differences     The list of differences between the two personas' opinions.
     */
    public ComparePersonasOutputData(String persona1Opinion, String persona2Opinion, List<String> similarities, List<String> differences) {
        this.persona1Opinion = persona1Opinion;
        this.persona2Opinion = persona2Opinion;
        this.similarities = similarities;
        this.differences = differences;
    }

    /**
     * Gets the opinion of persona 1.
     *
     * @return the opinion of persona 1.
     */
    public String getPersona1Opinion() {
        return persona1Opinion;
    }

    /**
     * Gets the opinion of persona 2.
     *
     * @return the opinion of persona 2.
     */
    public String getPersona2Opinion() {
        return persona2Opinion;
    }

    /**
     * Gets the similarities between the opinions.
     *
     * @return the list of similarities.
     */
    public List<String> getSimilarities() {
        return similarities;
    }

    /**
     * Gets the differences between the opinions.
     *
     * @return the list of differences.
     */
    public List<String> getDifferences() {
        return differences;
    }
}
