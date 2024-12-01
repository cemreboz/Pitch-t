package use_case.create_pitch;

import data_access.ChatgptDataAccessObject;
import interface_adapter.pitch.PitchViewModel;
import interface_adapter.targetaudience.TargetAudienceController;
import interface_adapter.targetaudience.TargetAudiencePresenter;
import use_case.set_targetaudience.*;

/**
 * Input data for the Create Pitch use case.
 * Contains the necessary information to create a pitch.
 */
public class CreateNewPitchInputData {

    private final String name;
    private final String description;
    private final String image;
    private final String targetAudience;

    public CreateNewPitchInputData(String name, String description, String image,
                                   String targetAudience) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.targetAudience = targetAudience;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getTargetAudienceList() throws Exception {
        final TargetAudienceDataAccessInterface dataAccess = new ChatgptDataAccessObject();
        final PitchViewModel viewModel = new PitchViewModel();
        final TargetAudienceOutputBoundary outputBoundary = new TargetAudiencePresenter(viewModel);
        final TargetAudienceInputBoundary inputBoundary = new TargetAudienceInteractor(dataAccess, outputBoundary);
        final TargetAudienceController targetAudienceController = new TargetAudienceController(inputBoundary);
        final TargetAudienceInputData inputData = new TargetAudienceInputData(name, description);
        return targetAudienceController.generate(inputData);
    }
}
