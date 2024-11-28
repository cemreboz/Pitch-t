package use_case.compare_personas;

import java.util.Map;

/**
 * The Output Data for the Compare Personas Use Case.
 */
public class ComparePersonasOutputData {
    private final Map<String, String> comparisonResults;

    public ComparePersonasOutputData(Map<String, String> comparisonResults) {
        this.comparisonResults = comparisonResults;
    }

    public Map<String, String> getComparisonResults() {
        return comparisonResults;
    }
}
