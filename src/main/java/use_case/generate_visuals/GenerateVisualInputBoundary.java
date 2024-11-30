package use_case.generate_visuals;

/**
 * The Change Password Use Case.
 */

public interface GenerateVisualInputBoundary {
    /**
     * Execute the Generate Visuals Use Case.
     *
     * @param visualInputData the input data for this use case
     */
    void execute(GenerateVisualInputData visualInputData);
}
