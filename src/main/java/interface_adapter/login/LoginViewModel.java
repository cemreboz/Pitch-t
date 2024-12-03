package interface_adapter.login;

import interface_adapter.ViewModel;

/**
 * The View Model for the Login View.
 */
public class LoginViewModel extends ViewModel<LoginState> {

    public static final String TITLE_LABEL = "Login View";
    public static final int TITLE_FONT = 24;
    public static final int DEFUALT_HEIGHT = 20;
    public static final int DEFUALT_WIDTH = 90;

    public LoginViewModel() {
        super("log in");
        setState(new LoginState());
    }

}
