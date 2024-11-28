package use_case.new_pitch;

import use_case.dashboard_show_pitch.DashboardOutputData;

/**
 * Output boundary for the New Pitch use case.
 * Used for returning data from the interactor to the controller.
 */
public interface NewPitchOutputBoundary {

    /**
     * Prepares the success view for the new pitch use case.
     * @param outputData the output data
     */
    void prepareSuccessView(NewPitchOutputData outputData);

    /**
     * Prepares the failure view for the new pitch use case.
     * @param errorMessage explanation of the failure
     */
    void prepareFailView(String errorMessage);
}