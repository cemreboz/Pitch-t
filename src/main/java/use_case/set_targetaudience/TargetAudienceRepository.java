package use_case.set_targetaudience;

/**
 * Sets the interface for target audience repository.
 */
public interface TargetAudienceRepository {

    /**
     * Generates a list of target audiences based on a project description.
     *
     * @param projectDescription The description of the project.
     * @return A list of target audience categories.
     * @throws Exception If any error occurs during data fetching.
     */
    String generateTargetAudience(String projectDescription) throws Exception;
}
