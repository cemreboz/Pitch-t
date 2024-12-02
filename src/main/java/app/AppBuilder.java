package app;

import java.awt.CardLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.ChatExpertDataAccessObject;
import data_access.ChatgptDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.ChatMessage;
import entity.DBUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.account_settings.AccountSettingsController;
import interface_adapter.account_settings.AccountSettingsPresenter;
import interface_adapter.account_settings.AccountSettingsViewModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.chat_expert.ChatExpertController;
import interface_adapter.chat_expert.ChatExpertPresenter;
import interface_adapter.compare_personas.ComparePersonasController;
import interface_adapter.compare_personas.ComparePersonasPresenter;
import interface_adapter.compare_personas.ComparePersonasViewModel;
import interface_adapter.create_pitch.CreateNewPitchController;
import interface_adapter.create_pitch.CreateNewPitchPresenter;
import interface_adapter.expert.ExpertController;
import interface_adapter.expert.ExpertPresenter;
import interface_adapter.expert.ExpertViewModel;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardPresenter;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.new_pitch.ShowNewPitchController;
import interface_adapter.new_pitch.ShowNewPitchPresenter;
import interface_adapter.create_pitch.CreateNewPitchViewModel;
import interface_adapter.pitch.PitchViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.account_settings.AccountSettingsInputBoundary;
import use_case.account_settings.AccountSettingsInteractor;
import use_case.account_settings.AccountSettingsOutputBoundary;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.chat_expert.ChatExpertDataAccessInterface;
import use_case.chat_expert.ChatExpertGptAccessInterface;
import use_case.chat_expert.ChatExpertInputBoundary;
import use_case.chat_expert.ChatExpertInteractor;
import use_case.chat_expert.ChatExpertOutputBoundary;
import use_case.compare_personas.ComparePersonasInputBoundary;
import use_case.compare_personas.ComparePersonasInteractor;
import use_case.compare_personas.ComparePersonasOutputBoundary;
import use_case.compare_personas.ComparePersonasGptAccessInterface;
import use_case.create_pitch.CreateNewPitchInputBoundary;
import use_case.create_pitch.CreateNewPitchInteractor;
import use_case.create_pitch.CreateNewPitchOutputBoundary;
import use_case.expert.ExpertInputBoundary;
import use_case.expert.ExpertInteractor;
import use_case.expert.ExpertOutputBoundary;
import use_case.show_new_pitch.ShowNewPitchInputBoundary;
import use_case.show_new_pitch.ShowNewPitchInteractor;
import use_case.show_new_pitch.ShowNewPitchOutputBoundary;
import use_case.dashboard_show_pitch.DashboardInputBoundary;
import use_case.dashboard_show_pitch.DashboardInteractor;
import use_case.dashboard_show_pitch.DashboardOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import interface_adapter.view_personas.ViewPersonasController;
import interface_adapter.view_personas.ViewPersonasPresenter;
import interface_adapter.view_personas.ViewPersonasViewModel;
import use_case.view_personas.*;
import view.PersonaListView;
import view.AccountSettingsView;
import view.DashboardView;
import view.ExpertChatView;
import view.LoginView;
import view.CreateNewPitchView;
import view.PitchView;
import view.SignupView;
import view.ViewManager;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new DBUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;

    private LoginView loginView;
    private LoginViewModel loginViewModel;

    private AccountSettingsView accountSettingsView;
    private AccountSettingsViewModel accountSettingsViewModel;

    private PitchView pitchView;
    private PitchViewModel pitchViewModel;

    private DashboardView dashboardView;
    private DashboardViewModel dashboardViewModel;

    private ExpertChatView expertChatView;
    private ExpertViewModel expertViewModel;

    private CreateNewPitchView createNewPitchView;
    private CreateNewPitchViewModel createNewPitchViewModel;

    // Unmitigated shit below this line
    private ComparePersonasViewModel comparePersonasViewModel;
    private PersonaListView personaListView;
    private ViewPersonasViewModel viewPersonasViewModel;
    private ComparePersonasController comparePersonasController;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the Dashboard View to the application.
     * @return this builder
     */
    public AppBuilder addDashboardView() {
        dashboardViewModel = new DashboardViewModel();
        dashboardView = new DashboardView(dashboardViewModel);
        cardPanel.add(dashboardView, dashboardView.getViewName());
        return this;
    }

    /**
     * Adds the AccountSettings View to the application.
     * @return this builder
     */
    public AppBuilder addAccountSettingsView() {
        accountSettingsViewModel = new AccountSettingsViewModel();
        accountSettingsView = new AccountSettingsView(accountSettingsViewModel);
        cardPanel.add(accountSettingsView, accountSettingsView.getViewName());
        return this;
    }

    /**
     * Adds the Pitch View to the application.
     * @return this builder
     */
    public AppBuilder addPitchView() {
        pitchViewModel = new PitchViewModel();
        pitchView = new PitchView(pitchViewModel);
        cardPanel.add(pitchView, pitchView.getViewName());
        return this;
    }

    /**
     * Adds the New Pitch View to the application.
     * @return this builder
     */
    public AppBuilder addCreateNewPitchView() {
        createNewPitchViewModel = new CreateNewPitchViewModel();
        createNewPitchView = new CreateNewPitchView(createNewPitchViewModel);
        cardPanel.add(createNewPitchView, createNewPitchView.getViewName());
        return this;
    }

    /**
     * Adds the expert chat view to the application.
     * @return this builder
     */
    public AppBuilder addExpertChatView() {
        expertViewModel = new ExpertViewModel();
        expertChatView = new ExpertChatView(expertViewModel, viewManagerModel);
        cardPanel.add(expertChatView, expertChatView.getViewName());

        return this;
    }

    /**
     * Adds the View Personas View to the application.
     * @return this builder
     */
    public AppBuilder addPersonaListView() {
        viewPersonasViewModel = new ViewPersonasViewModel();
        personaListView = new PersonaListView(viewPersonasViewModel,
                comparePersonasViewModel);
        cardPanel.add(personaListView, personaListView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                dashboardViewModel, loginViewModel, signupViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(accountSettingsViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        accountSettingsView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                accountSettingsViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        accountSettingsView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the dashboard use case and as part of the hamburger menu to each view with the menu.
     * @return this builder
     */
    public AppBuilder addDashboardUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                dashboardViewModel, loginViewModel, signupViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        dashboardView.setLoginController(loginController);
        accountSettingsView.setLoginController(loginController);
        pitchView.setLoginController(loginController);
        expertChatView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the account settings use case and as part of the hamburger menu to each view with the menu.
     * @return this builder
     */
    public AppBuilder addAccountSettingsUseCase() {
        final AccountSettingsOutputBoundary accountSettingsOutputBoundary = new AccountSettingsPresenter(
                dashboardViewModel, accountSettingsViewModel, viewManagerModel);
        final AccountSettingsInputBoundary accountSettingsInteractor = new AccountSettingsInteractor(
                userDataAccessObject, accountSettingsOutputBoundary);

        final AccountSettingsController accountSettingsController = new AccountSettingsController(
                accountSettingsInteractor);
        dashboardView.setAccountSettingsController(accountSettingsController);
        accountSettingsView.setAccountSettingsController(accountSettingsController);
        pitchView.setAccountSettingsController(accountSettingsController);
        expertChatView.setAccountSettingsController(accountSettingsController);
        return this;
    }

    /**
     * Adds the pitch view use case.
     * @return this builder
     */
    public AppBuilder addPitchUseCase() {
        final DashboardOutputBoundary dashboardOutputBoundary = new DashboardPresenter(
                dashboardViewModel, pitchViewModel, viewManagerModel);
        final DashboardInputBoundary dashboardInteractor = new DashboardInteractor(
                userDataAccessObject, dashboardOutputBoundary);

//        // Block for the ViewPersonasController
//        ViewPersonasGptDataAccessInterface chatgptDataAccessObject = new ChatgptDataAccessObject();
//        ViewPersonasInputBoundary viewPersonasInteractor = new ViewPersonasInteractor(chatgptDataAccessObject);
//        ViewPersonasController viewPersonasController = new ViewPersonasController(viewPersonasInteractor);
//        pitchView.setViewPersonasController(viewPersonasController);

        final DashboardController dashboardController = new DashboardController(
                dashboardInteractor);
        dashboardView.setDashboardController(dashboardController);
        return this;
    }

    /**
     * Adds the new pitch view use case.
     * @return this builder
     */
    public AppBuilder addShowNewPitchUseCase() {
        final ShowNewPitchOutputBoundary showNewPitchOutputBoundary = new ShowNewPitchPresenter(
                createNewPitchViewModel, dashboardViewModel, viewManagerModel);
        final ShowNewPitchInputBoundary showNewPitchInteractor = new ShowNewPitchInteractor(
                userDataAccessObject, showNewPitchOutputBoundary);

        final ShowNewPitchController showNewPitchController = new ShowNewPitchController(
                showNewPitchInteractor);
        dashboardView.setNewPitchController(showNewPitchController);
        accountSettingsView.setNewPitchController(showNewPitchController);
        pitchView.setNewPitchController(showNewPitchController);
        expertChatView.setNewPitchController(showNewPitchController);
        return this;
    }

    /**
     * Adds the create new pitch view use case.
     * @return this builder
     */
    public AppBuilder addCreateNewPitchUseCase() {
        final CreateNewPitchOutputBoundary createNewPitchOutputBoundary = new CreateNewPitchPresenter(
                createNewPitchViewModel, pitchViewModel, viewManagerModel);
        final CreateNewPitchInputBoundary createNewPitchInteractor = new CreateNewPitchInteractor(
                userDataAccessObject, createNewPitchOutputBoundary);
        final CreateNewPitchController createNewPitchController = new CreateNewPitchController(
                createNewPitchInteractor);
        createNewPitchView.setCreateNewPitchController(createNewPitchController);
        return this;
    }

    /**
     * Adds the expert view use case and as part of the hamburger menu to each view with the menu.
     * @return this builder
     */
    public AppBuilder addExpertUseCase() {
        final ExpertOutputBoundary expertOutputBoundary = new ExpertPresenter(
                expertViewModel, viewManagerModel);
        final ExpertInputBoundary expertInteractor = new ExpertInteractor(
                userDataAccessObject, expertOutputBoundary);

        final ExpertController expertController = new ExpertController(
                expertInteractor);
        dashboardView.setExpertController(expertController);
        accountSettingsView.setExpertController(expertController);
        pitchView.setExpertController(expertController);
        expertChatView.setExpertController(expertController);
        return this;
    }

    /**
     * Adds the chat with individual expert use case.
     * @return this builder
     */
    public AppBuilder addChatExpertUseCase() {
        final ChatExpertOutputBoundary chatExpertOutputBoundary = new ChatExpertPresenter(
                expertViewModel, viewManagerModel);

        final ChatExpertDataAccessInterface chatExpertDataAccessObject = new ChatExpertDataAccessObject();
        final ChatExpertGptAccessInterface chatgptDataAccessObject = new ChatExpertGptAccessInterface() {
            @Override
            public String getInteraction(List<ChatMessage> messages) throws IOException, InterruptedException {
                return "";
            }
        };

        final ChatExpertInputBoundary chatExpertInteractor = new ChatExpertInteractor(
                chatExpertDataAccessObject, chatgptDataAccessObject, chatExpertOutputBoundary);

        final ChatExpertController chatExpertController = new ChatExpertController(
                chatExpertInteractor);
        expertChatView.setChatExpertController(chatExpertController);
        return this;
    }

    /**
     * Adds the compare personas use case.
     * @return this builder
     */
    public AppBuilder addComparePersonasUseCase() {
        // Instantiate Output Boundary
        final ComparePersonasOutputBoundary comparePersonasOutputBoundary = new ComparePersonasPresenter(
                comparePersonasViewModel);

        // Instantiate GPT Data Access Interface
        final ComparePersonasGptAccessInterface chatgptDataAccessObject = new ComparePersonasGptAccessInterface() {
            @Override
            public String getInteraction(List<ChatMessage> messages) throws IOException, InterruptedException {
                return "";
            }
        };

        // Create the Interactor, providing it the GPT data access object and output boundary
        final ComparePersonasInputBoundary comparePersonasInteractor = new ComparePersonasInteractor(
                chatgptDataAccessObject, comparePersonasOutputBoundary);

        // Create the controller using the Interactor
        final ComparePersonasController comparePersonasController = new ComparePersonasController(
                comparePersonasInteractor);

        // Set the controller for the Persona List View
        personaListView.setComparePersonasController(comparePersonasController);

        return this;
    }

    /**
     * Adds the View Personas Use Case to the application.
     * @return this builder
     */
    public AppBuilder addViewPersonasUseCase() {
        // Instantiate Output Boundary (Presenter)
        ViewPersonasOutputBoundary presenter = new ViewPersonasPresenter(viewPersonasViewModel);

        // Instantiate Interactor
        ViewPersonasGptDataAccessInterface chatgptDataAccessObject = new ChatgptDataAccessObject();
        ViewPersonasInputBoundary interactor = new ViewPersonasInteractor(chatgptDataAccessObject, presenter);

        // Instantiate Controller
        ViewPersonasController controller = new ViewPersonasController(interactor);

        // Set Controller in View
        personaListView.setViewPersonasController(controller);

        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Pitch!t");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        application.setExtendedState(JFrame.MAXIMIZED_BOTH);
        application.setLocationRelativeTo(null);

        // shutdown hook, registered into JVM, independent of this jFrame object
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // checks the current view before executing save, saves on log out
            if (!viewManagerModel.getState().toString()
                    .equals("sign up") && !viewManagerModel.getState().toString().equals("log in")) {
                System.out.println("Executing save before exit...");
            }
        }));

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
