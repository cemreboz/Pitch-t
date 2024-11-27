package interface_adapter.new_pitch;

import use_case.new_pitch.NewPitchInputBoundary;
import use_case.new_pitch.NewPitchInputData;
import use_case.new_pitch.NewPitchOutputBoundary;
import use_case.new_pitch.NewPitchOutputData;

/**
 * The presenter for the New Pitch Use Case.
 */
public class NewPitchPresenter implements NewPitchOutputBoundary {

    private final NewPitchState newPitchState;
    private final NewPitchViewModel newPitchViewModel;

    public NewPitchPresenter(NewPitchState newPitchState, NewPitchViewModel newPitchViewModel) {
        this.newPitchState = newPitchState;
        this.newPitchViewModel = newPitchViewModel;
    }

    /**
     * Handles the result of the New Pitch Use Case.
     * @param outputData the output data from the use case execution.
     */
    @Override
    public void present(NewPitchOutputData outputData) {
        // Update the state based on the use case outcome
        if (outputData.getErrorMessage() != null) {
            newPitchState.setErrorMessage(outputData.getErrorMessage());
            newPitchState.setSuccess(false);
        }
        else {
            newPitchState.setErrorMessage(null);
            newPitchState.setSuccess(true);
            newPitchState.setName(outputData.getName());
            newPitchState.setDescription(outputData.getDescription());
            newPitchState.setImage(outputData.getImage());
            newPitchState.setTargetAudience(outputData.getTargetAudience());
        }

        // Update ViewModel to reflect changes in state
        newPitchViewModel.updateView(newPitchState);
    }

    /**
     * Translates the user's input into the input data for the use case.
     * @param name the name of the pitch.
     * @param description the description of the pitch.
     * @param image the image URL of the pitch.
     * @param targetAudience the target audience for the pitch.
     */
    public void createNewPitch(String name, String description, String image, List<String> targetAudience) {
        // Set the loading state to true while processing
        newPitchState.setLoading(true);
        newPitchViewModel.updateView(newPitchState);

        // Prepare the input data for the use case
        NewPitchInputData inputData = new NewPitchInputData(name, description, image, targetAudience);

        // Execute the use case through the input boundary
        NewPitchInputBoundary inputBoundary = new NewPitchInputBoundary();
        inputBoundary.execute(inputData);
    }
}