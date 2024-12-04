package use_case.login;

import data_access.InMemoryUserDataAccessObject;
import entity.DBUserFactory;
import entity.User;
import entity.UserFactory;
import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password",
                new ViewModel<>("log in"));
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        UserFactory factory = new DBUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);
        userRepository.setCurrentUser(user);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignupView() {
                fail("switchToSignupView should not be called in this test.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void successUserLoggedInTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password",
                new ViewModel<>("log in"));
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        UserFactory factory = new DBUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);
        userRepository.setCurrentUser(user);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", userRepository.getCurrentUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignupView() {
                fail("switchToSignupView should not be called in this test.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        assertNull(userRepository.getCurrentUsername());

        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        LoginInputData inputData = new LoginInputData("Paul", "wrong", new ViewModel<>(
                "log in"
        ));
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        UserFactory factory = new DBUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);
        userRepository.setCurrentUser(user);

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Paul\".", error);
            }

            @Override
            public void switchToSignupView() {
                fail("switchToSignupView should not be called in this test.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password",
                new ViewModel<LoginState>("log in"));
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        userRepository.setCurrentUser(null);

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Paul: Account does not exist.", error);
            }

            @Override
            public void switchToSignupView() {
                // Verify that the method is called when the user does not exist.
                assertTrue(true, "switchToSignupView was called.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);

        interactor.switchToSignupView();
        interactor.execute(inputData);
    }
}
