package app;

import javax.swing.JFrame;
import java.io.IOException;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                                            .addLoginView()
                                            .addExpertChatView()
                                            .addSignupView()
                                            .addAccountSettingsView()
                                            .addDashboardView()
                                            .addPitchView()
                                            .addNewPitchView()
                                            .addPersonaChatView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .addDashboardUseCase()
                                            .addAccountSettingsUseCase()
                                            .addPitchUseCase()
                                            .addNewPitchUseCase()
                                            .addCreateNewPitchUseCase()
                                            .addExpertUseCase()
                                            .addChatExpertUseCase()
                                            .addPersonaUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);
    }
}
