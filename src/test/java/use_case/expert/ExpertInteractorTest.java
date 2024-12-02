package use_case.expert;

import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpertInteractorTest {

    private TestExpertDataAccess testDataAccess;
    private TestExpertPresenter testPresenter;
    private ExpertInteractor interactor;

    @BeforeEach
    void setUp() {
        testDataAccess = new TestExpertDataAccess();
        testPresenter = new TestExpertPresenter();
        interactor = new ExpertInteractor(testDataAccess, testPresenter);
    }

    @Test
    void successExpertView() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        ExpertInputData inputData = new ExpertInputData(username, password);

        // Act
        interactor.execute(inputData);

        // Assert
        assertNotNull(testPresenter.outputData);
        assertEquals(username, testPresenter.outputData.getUsername());
        assertEquals(password, testPresenter.outputData.getPassword());
        assertFalse(testPresenter.outputData.isUseCaseFailed());
    }

    /**
     * Test stub for ExpertDataAccessInterface.
     */
    static class TestExpertDataAccess implements ExpertDataAccessInterface {

        private User currentUser;

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        public void setCurrentUser(User user) {
            this.currentUser = user;
        }
    }

    /**
     * Test stub for ExpertOutputBoundary.
     */
    static class TestExpertPresenter implements ExpertOutputBoundary {

        ExpertOutputData outputData;

        @Override
        public void prepareSuccessView(ExpertOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            fail("Failure path should not be triggered in this test.");
        }
    }
}
