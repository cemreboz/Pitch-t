package use_case.account_settings;

import entity.DBUser;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountSettingsInteractorTest {

    private TestAccountSettingsDataAccess testDataAccess;
    private TestAccountSettingsPresenter testPresenter;
    private AccountSettingsInteractor interactor;

    @BeforeEach
    void setUp() {
        testDataAccess = new TestAccountSettingsDataAccess();
        testPresenter = new TestAccountSettingsPresenter();
        interactor = new AccountSettingsInteractor(testDataAccess, testPresenter);
    }

    @Test
    void successFetchAccountSettingsForDBUser() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        DBUser existingUser = new DBUser(username, password);
        testDataAccess.addUser(existingUser);

        AccountSettingsInputData inputData = new AccountSettingsInputData(username, password);

        // Act
        interactor.execute(inputData);

        // Assert
        assertNotNull(testPresenter.outputData);
        assertEquals(username, testPresenter.outputData.getUsername());
        assertEquals(password, testPresenter.outputData.getPassword());
        assertFalse(testPresenter.outputData.isUseCaseFailed());
    }

    @Test
    void successFetchAccountSettingsForGenericUser() {
        // Arrange
        String username = "genericUser";
        String password = "genericPassword";

        User existingUser = new DBUser(username, password);
        testDataAccess.addUser(existingUser);

        AccountSettingsInputData inputData = new AccountSettingsInputData(username, password);

        // Act
        interactor.execute(inputData);

        // Assert
        assertNotNull(testPresenter.outputData);
        assertEquals(username, testPresenter.outputData.getUsername());
        assertEquals(password, testPresenter.outputData.getPassword());
        assertFalse(testPresenter.outputData.isUseCaseFailed());
    }

    /**
     * Test stub for AccountSettingsDataAccessInterface.
     */
    static class TestAccountSettingsDataAccess implements AccountSettingsDataAccessInterface {
        private User storedUser;

        @Override
        public User get(String username) {
            if (storedUser != null && storedUser.getName().equals(username)) {
                return storedUser;
            }
            return null;
        }

        public void addUser(User user) {
            this.storedUser = user;
        }
    }

    /**
     * Test stub for AccountSettingsOutputBoundary.
     */
    static class TestAccountSettingsPresenter implements AccountSettingsOutputBoundary {
        AccountSettingsOutputData outputData;
        boolean failed = false;
        String failMessage;

        @Override
        public void prepareSuccessView(AccountSettingsOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            this.failed = true;
            this.failMessage = error;
        }
    }
}
