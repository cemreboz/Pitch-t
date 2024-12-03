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
                // Construct the views
                .addLoginView()
                .addExpertChatView()
                .addSignupView()
                .addAccountSettingsView()
                .addDashboardView()
                .addPitchView()
                .addCreateNewPitchView()
                .addPersonaListView()
                .addPersonaComparisonView()
                .addVisionView()
                .addPersonaChatView()
                // Construct the use cases
                .addViewPersonasUseCase()
                .addDetailedTargetAudiencePageView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addChangePasswordUseCase()
                .addLogoutUseCase()
                .addDashboardUseCase()
                .addAccountSettingsUseCase()
                .addVisionUseCase()
                .addPitchUseCase()
                .addShowNewPitchUseCase()
                .addCreateNewPitchUseCase()
                .addDetailedTargetAudienceUseCase()
                .addExpertUseCase()
                .addChatExpertUseCase()
                .addPersonaUseCase()
                .addChatPersonaUseCase()
                .addComparePersonasUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
