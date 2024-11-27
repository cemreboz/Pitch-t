package use_case.set_targetaudience;

import java.util.List;

import entity.TargetAudience;

/**
 * Class that generates the target audience.
 */
public class GenerateTargetAudienceUseCase {

    private final TargetAudienceRepository targetAudienceRepository;

    public GenerateTargetAudienceUseCase(TargetAudienceRepository repository) {
        this.targetAudienceRepository = repository;
    }

    /**
     * Generates the Target Audience based on the pitch.
     * @param projectDescription the pitch description.
     * @return the Target Audience based on the pitch.
     * @throws Exception if the API call doesn't work.
     */
    public List<TargetAudience> execute(String projectDescription) throws Exception {
        return targetAudienceRepository.generateTargetAudience(projectDescription);
    }
}
