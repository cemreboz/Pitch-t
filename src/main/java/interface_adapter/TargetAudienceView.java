package interface_adapter;

import java.util.List;

/**
 * Interface for Target Audience View.
 */
public interface TargetAudienceView {
    /**
     * Method for showLoading.
     */
    void showLoading();

    /**
     * Method for hideLoading.
     */
    void hideLoading();

    /**
     * Method for displayTargetAudience.
     * @param targetAudience input for TargetAudience.
     */
    void displayTargetAudience(List<String> targetAudience);

    /**
     * Method for showError.
     * @param errorMessage if there is an ErrorMessage.
     */
    void showError(String errorMessage);
}
