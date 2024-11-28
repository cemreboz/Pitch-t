package use_case.create_pitch;

import entity.DBUser;
import entity.Pitch;
import interface_adapter.targetaudience.TargetAudienceController;
import use_case.dashboard_show_pitch.DashboardOutputData;
import use_case.new_pitch.NewPitchInputData;

import java.util.List;

/**
 * Interactor for creating a new pitch and associating it with a user.
 */
public class CreateNewPitchInteractor implements CreateNewPitchInputBoundary {
    private CreateNewPitchDataAccessInterface userDataAccessObject;
    private final CreateNewPitchOutputBoundary userPresenter;
    private final TargetAudienceController targetAudienceController;

    /**
     * Constructor for the CreateNewPitchInteractor.
     *
     * @param createNewPitchDataAccessInterface Gateway for pitch-related data access.
     * @param createNewPitchOutputBoundary the presenter to be used after this interactor
     * @param targetAudienceController generates the target audience.
     */
    public CreateNewPitchInteractor(CreateNewPitchDataAccessInterface createNewPitchDataAccessInterface,
                                    CreateNewPitchOutputBoundary createNewPitchOutputBoundary,
                                    TargetAudienceController targetAudienceController) {
        this.userDataAccessObject = createNewPitchDataAccessInterface;
        this.userPresenter = createNewPitchOutputBoundary;
        this.targetAudienceController = targetAudienceController;
    }

    @Override
    public void execute(CreateNewPitchInputData createNewPitchInputData) {
        // Validate input
        if (createNewPitchInputData.getName().isEmpty()) {
            userPresenter.prepareFailView("Pitch name cannot be empty");
        }
        if (createNewPitchInputData.getDescription().isEmpty()) {
            userPresenter.prepareFailView("Pitch description cannot be empty");
        }
        List<String> targetAudienceList = createNewPitchInputData.getTargetAudienceList();
        if (targetAudienceList == null || targetAudienceList.isEmpty()) {
            try {
                final Pitch tempPitch = new Pitch(
                        null,
                        createNewPitchInputData.getName(),
                        null,
                        createNewPitchInputData.getDescription(),
                        null
                );
                final String generatedAudience = targetAudienceController.execute(tempPitch);
                targetAudienceList = List.of(generatedAudience.split(";"));
            }
            catch (Exception exception) {
                userPresenter.prepareFailView("Failed to generate target audience: "
                                              + exception.getMessage());
            }
        }

        // Image is nice but not mandatory.
        // TODO: validate image somehow?

        // Create a new Pitch
        final Pitch newPitch = new Pitch(
            generatePitchID(),
            createNewPitchInputData.getName(),
            createNewPitchInputData.getImage(),  // Image is optional and can be null
            createNewPitchInputData.getDescription(), targetAudienceList
        );

        if (userDataAccessObject.getCurrentUser() instanceof DBUser) {
            ((DBUser) userDataAccessObject.getCurrentUser()).addPitch(newPitch);
        }

        final CreateNewPitchOutputData createNewPitchOutputData = new CreateNewPitchOutputData(false,
                newPitch, userDataAccessObject.getCurrentUser().getName(),
                userDataAccessObject.getCurrentUser().getPassword());
        userPresenter.prepareSuccessView(createNewPitchOutputData);
    }

    /**
     * Generates a unique pitch ID. (Cause I like giving id to things)
     *
     * @return The generated pitch ID.
     */
    private String generatePitchID() {
        return String.valueOf(System.currentTimeMillis());
    }
}
