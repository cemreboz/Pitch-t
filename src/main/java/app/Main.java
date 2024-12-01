package app;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                                            .addLoginView()
                                            .addExpertChatView()
                                            .addSignupView()
                                            .addAccountSettingsView()
                                            .addDashboardView()
                                            .addPitchView()
                                            .addNewPitchView()
                                            .addDetailedTargetAudiencePageView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .addDashboardUseCase()
                                            .addAccountSettingsUseCase()
                                            .addPitchUseCase()
                                            .addNewPitchUseCase()
                                            .addCreateNewPitchUseCase()
                                            .addDetailedTargetAudienceUseCase()
                                            .addExpertUseCase()
                                            .addChatExpertUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);
    }
}
