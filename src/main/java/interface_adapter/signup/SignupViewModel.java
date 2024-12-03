package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public static final String TITLE_LABEL = "Sign Up";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String PASSWORD_LABEL = "Choose password";
    public static final String REPEAT_PASSWORD_LABEL = "Re-enter password";
    public static final int TITLE_FONT = 24;
    public static final int DEFUALT_HEIGHT = 20;
    public static final int DEFUALT_WIDTH = 90;

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String TO_LOGIN_BUTTON_LABEL = "Log in";

    public SignupViewModel() {
        super("sign up");
        setState(new SignupState());
    }

}
