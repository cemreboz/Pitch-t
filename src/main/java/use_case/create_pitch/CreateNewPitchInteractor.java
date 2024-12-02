package use_case.create_pitch;

import data_access.ChatgptDataAccessObject;
import entity.DBUser;
import entity.Pitch;
import interface_adapter.pitch.PitchViewModel;
import interface_adapter.targetaudience.TargetAudienceController;
import interface_adapter.targetaudience.TargetAudiencePresenter;
import org.jetbrains.annotations.NotNull;
import use_case.dashboard_show_pitch.DashboardOutputData;
import use_case.new_pitch.NewPitchInputData;
import use_case.set_targetaudience.*;

/**
 * Interactor for creating a new pitch and associating it with a user.
 */
public class CreateNewPitchInteractor implements CreateNewPitchInputBoundary {
    private CreateNewPitchDataAccessInterface userDataAccessObject;
    private final CreateNewPitchOutputBoundary userPresenter;

    /**
     * Constructor for the CreateNewPitchInteractor.
     *
     * @param createNewPitchDataAccessInterface Gateway for pitch-related data access.
     * @param createNewPitchOutputBoundary the presenter to be used after this interactor
     */
    public CreateNewPitchInteractor(CreateNewPitchDataAccessInterface createNewPitchDataAccessInterface,
                                    CreateNewPitchOutputBoundary createNewPitchOutputBoundary) {
        this.userDataAccessObject = createNewPitchDataAccessInterface;
        this.userPresenter = createNewPitchOutputBoundary;
    }

    @Override
    public void execute(CreateNewPitchInputData createNewPitchInputData) throws Exception {
        // Validate input
        if (createNewPitchInputData.getName().isEmpty()) {
            userPresenter.prepareFailView("Pitch name cannot be empty");
        }
        if (createNewPitchInputData.getDescription().isEmpty()) {
            userPresenter.prepareFailView("Pitch description cannot be empty");
        }
        if (createNewPitchInputData.getTargetAudienceList().isEmpty()) {
            userPresenter.prepareFailView("Pitch target audience list cannot be empty");
            final TargetAudienceController targetAudienceController = getTargetAudienceController();
            final TargetAudienceInputData targetAudienceInputData = new TargetAudienceInputData(
                    createNewPitchInputData.getName(), createNewPitchInputData.getDescription());
            targetAudienceController.generate(targetAudienceInputData);
            // instead of making users manually put them in remove this if and have it so the General TA is generated
            // based off of everything else
        }

        // Image is nice but not mandatory.
        // TODO: validate image somehow?

        // Create a new Pitch
        final Pitch newPitch = new Pitch(
                generatePitchID(),
                createNewPitchInputData.getName(),
                createNewPitchInputData.getImage(),
                createNewPitchInputData.getDescription(),
                createNewPitchInputData.getTargetAudienceList()
        );

        if (userDataAccessObject.getCurrentUser() instanceof DBUser) {
            ((DBUser) userDataAccessObject.getCurrentUser()).addPitch(newPitch);
        }

        final CreateNewPitchOutputData createNewPitchOutputData = new CreateNewPitchOutputData(false,
                newPitch, userDataAccessObject.getCurrentUser().getName(),
                userDataAccessObject.getCurrentUser().getPassword());
        userPresenter.prepareSuccessView(createNewPitchOutputData);
    }

    @NotNull
    private static TargetAudienceController getTargetAudienceController() {
        final TargetAudienceDataAccessInterface dataAccessInterface = new ChatgptDataAccessObject();
        final PitchViewModel pitchViewModel = new PitchViewModel();
        final TargetAudienceOutputBoundary targetAudienceOutputBoundary = new TargetAudiencePresenter(pitchViewModel);
        final TargetAudienceInputBoundary targetAudienceInputBoundary = new TargetAudienceInteractor(
                dataAccessInterface, targetAudienceOutputBoundary);
        final TargetAudienceController targetAudienceController = new TargetAudienceController(
                targetAudienceInputBoundary);
        return targetAudienceController;
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