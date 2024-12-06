package use_case.create_pitch;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import data_access.ChatgptDataAccessObject;
import entity.DBUser;
import entity.Pitch;
import interface_adapter.pitch.PitchViewModel;
import interface_adapter.targetaudience.TargetAudienceController;
import interface_adapter.targetaudience.TargetAudiencePresenter;
import use_case.set_targetaudience.TargetAudienceDataAccessInterface;
import use_case.set_targetaudience.TargetAudienceInputBoundary;
import use_case.set_targetaudience.TargetAudienceInputData;
import use_case.set_targetaudience.TargetAudienceInteractor;
import use_case.set_targetaudience.TargetAudienceOutputBoundary;

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
            // instead of making users manually put them in remove this if and have it so the General TA is generated
            // based off of everything else
        }
        final TargetAudienceController targetAudienceController = getTargetAudienceController();
        final TargetAudienceInputData targetAudienceInputData = new TargetAudienceInputData(
                createNewPitchInputData.getName(), createNewPitchInputData.getDescription());
        final String targetaudience = targetAudienceController.generate(targetAudienceInputData);

        final List<String> targetAudienceList = parseTargetAudience(targetaudience);

        // Create a new Pitch
        final Pitch newPitch = new Pitch(
                generatePitchID(),
                createNewPitchInputData.getName(),
                createNewPitchInputData.getImage(),
                createNewPitchInputData.getDescription(),
                targetAudienceList
        );

        if (userDataAccessObject.getCurrentUser() instanceof DBUser) {
            final DBUser tempuser = (DBUser) userDataAccessObject.getCurrentUser();
            tempuser.addPitch(newPitch);
        }

        final CreateNewPitchOutputData createNewPitchOutputData = new CreateNewPitchOutputData(false,
                newPitch, userDataAccessObject.getCurrentUser().getName(),
                userDataAccessObject.getCurrentUser().getPassword());
        userPresenter.prepareSuccessView(createNewPitchOutputData);
    }

    private static List<String> parseTargetAudience(String input) {
        final List<String> result = new ArrayList<>();

        final String[] lines = input.split("\\r?\\n");

        for (String line : lines) {
            final String cleanedLine = line.replace("-", "").replace(";", "").trim();
            if (!cleanedLine.isEmpty()) {
                result.add(cleanedLine);
            }
        }

        return result;
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
