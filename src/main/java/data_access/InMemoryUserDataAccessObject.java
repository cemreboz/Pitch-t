package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.DBUser;
import entity.Expert;
import entity.User;
import use_case.account_settings.AccountSettingsDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.chat_expert.ChatExpertDataAccessInterface;
import use_case.create_pitch.CreateNewPitchDataAccessInterface;
import use_case.dashboard_show_pitch.DashboardDataAccessInterface;
import use_case.expert.ExpertDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.new_pitch.NewPitchDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        DashboardDataAccessInterface,
        AccountSettingsDataAccessInterface,
        NewPitchDataAccessInterface,
        CreateNewPitchDataAccessInterface,
        ChatExpertDataAccessInterface,
        ExpertDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;
    private User currentUser;

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public Expert getExpertById(String expertId) {
        final DBUser castedCurrentUser = (DBUser) this.currentUser;
        return castedCurrentUser.getExpertById(expertId);
    }

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void changePassword(User user) {
        // Replace the old entry with the new password
        users.put(user.getName(), user);
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }
}
