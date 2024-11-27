package interface_adapter.targetaudience;

import java.util.List;

import entity.DetailedTargetAudience;

/**
 * Interface for Detailed Target Audience View.
 */
public interface DetailedTargetAudienceView {
    /**
     * Method for showLoading.
     */
    void showLoading();

    /**
     * Method for hideLoading.
     */
    void hideLoading();

    /**
     * Method for Display Detailed Target Audience.
     * @param detailedTargetAudience the input for detailed Target Audience.
     */
    void displayDetailedTargetAudience(List<DetailedTargetAudience> detailedTargetAudience);

    /**
     * Method for showing the error.
     * @param errorMessage ther error message input.
     */
    void showError(String errorMessage);
}
