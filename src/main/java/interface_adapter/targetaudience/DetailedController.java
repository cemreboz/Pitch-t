package interface_adapter.targetaudience;

import java.util.List;

import entity.DetailedTargetAudience;
import use_case.set_targetaudience.DetailedInteractor;

/**
 * Controller class for generating detailed target audiences.
 */
public class DetailedController {

    private final DetailedInteractor interactor;

    public DetailedController(DetailedInteractor interactor) {
        if (interactor == null) {
            throw new IllegalArgumentException("Interactor cannot be null.");
        }
        this.interactor = interactor;
    }

    /**
     * Executes the interactor to fetch detailed target audiences.
     *
     * @param audienceCategory The audience category to fetch details for.
     * @return A list of detailed target audiences.
     * @throws Exception If there is an error during execution.
     * @throws IllegalArgumentException if the audience category is null or empty.
     */
    public List<DetailedTargetAudience> execute(String audienceCategory) throws Exception {
        if (audienceCategory == null || audienceCategory.isEmpty()) {
            throw new IllegalArgumentException("Audience category cannot be null or empty.");
        }
        return interactor.fetchDetailedTargetAudience(audienceCategory);
    }
}
