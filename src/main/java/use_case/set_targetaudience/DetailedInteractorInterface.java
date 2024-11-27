package use_case.set_targetaudience;

import java.util.List;

import entity.DetailedTargetAudience;

/**
 * Input boundary for the Detailed Interactor.
 * Defines the contract for fetching detailed target audiences based on a category.
 */
public interface DetailedInteractorInterface {

    /**
     * Fetches a list of detailed target audiences for the given category.
     *
     * @param audienceCategory The category for which detailed target audiences are required.
     * @return A list of {@link DetailedTargetAudience} objects containing detailed information.
     * @throws Exception If there is an error fetching or processing the data.
     */
    List<DetailedTargetAudience> fetchDetailedTargetAudience(String audienceCategory) throws Exception;
}
