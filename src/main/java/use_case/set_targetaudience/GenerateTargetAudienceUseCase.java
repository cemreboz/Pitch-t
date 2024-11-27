package use_case.set_targetaudience;

import entity.Pitch;

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
     *
     * @param pitch the pitch itself.
     * @return the Target Audience based on the pitch.
     * @throws Exception if the API call doesn't work.
     */
    public String execute(Pitch pitch) throws Exception {
        return targetAudienceRepository.generateTargetAudience(pitch);
    }
}
