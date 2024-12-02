package use_case.change_password;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordInteractorTest {

    private TestChangePasswordUserDataAccess testDataAccess;
    private TestChangePasswordPresenter testPresenter;
    private UserFactory userFactory;
    private ChangePasswordInteractor interactor;

    @BeforeEach
    void setUp() {
        testDataAccess = new TestChangePasswordUserDataAccess();
        testPresenter = new TestChangePasswordPresenter();
        userFactory = new DBUserFactory();
        interactor = new ChangePasswordInteractor(testDataAccess, testPresenter, userFactory);
    }

    @Test
    void successChangePasswordForDBUser() {
        String username = "testUser";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        DBUser existingUser = new DBUser(username, oldPassword);
        testDataAccess.addUser(existingUser);

        ChangePasswordInputData inputData = new ChangePasswordInputData(newPassword, username);

        interactor.execute(inputData);

        assertNotNull(testPresenter.outputData);
        assertEquals(username, testPresenter.outputData.getUsername());
        assertFalse(testPresenter.outputData.isUseCaseFailed());

        User updatedUser = testDataAccess.get(username);
        assertNotNull(updatedUser);
        assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    void successChangePasswordForGenericUser() {
        String username = "genericUser";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        User existingUser = new CommonUser(username, oldPassword);
        testDataAccess.addUser(existingUser);

        ChangePasswordInputData inputData = new ChangePasswordInputData(newPassword, username);

        // Act
        interactor.execute(inputData);

        // Assert
        assertNotNull(testPresenter.outputData);
        assertEquals(username, testPresenter.outputData.getUsername());
        assertFalse(testPresenter.outputData.isUseCaseFailed());

        User updatedUser = testDataAccess.get(username);
        assertNotNull(updatedUser);
        assertEquals(newPassword, updatedUser.getPassword());
    }



    /**
     * Test stub for ChangePasswordUserDataAccessInterface.
     */
    static class TestChangePasswordUserDataAccess implements ChangePasswordUserDataAccessInterface {
        private User storedUser;

        @Override
        public User get(String username) {
            if (storedUser != null && storedUser.getName().equals(username)) {
                return storedUser;
            }
            return null;
        }

        @Override
        public void changePassword(User user) {
            if (storedUser != null && storedUser.getName().equals(user.getName())) {
                this.storedUser = user;
            }
        }

        public void addUser(User user) {
            this.storedUser = user;
        }
    }

    /**
     * Test stub for ChangePasswordOutputBoundary.
     */
    static class TestChangePasswordPresenter implements ChangePasswordOutputBoundary {
        ChangePasswordOutputData outputData;
        boolean failed = false;
        String failMessage;

        @Override
        public void prepareSuccessView(ChangePasswordOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            this.failed = true;
            this.failMessage = error;
        }
    }
}
