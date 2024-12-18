package use_case.new_pitch;

import entity.DBUser;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.show_new_pitch.*;

import static org.junit.jupiter.api.Assertions.*;

class NewPitchInteractorTest {

    private TestNewPitchDataAccess testDataAccess;
    private TestNewPitchPresenter testPresenter;
    private ShowNewPitchInteractor interactor;

    @BeforeEach
    void setUp() {
        testDataAccess = new TestNewPitchDataAccess();
        testPresenter = new TestNewPitchPresenter();
        interactor = new ShowNewPitchInteractor(testDataAccess, testPresenter);
    }

    @Test
    void successNewPitchView() {
        String username = "testUser";
        String password = "testPassword";
        User currentUser = new DBUser(username, password);
        testDataAccess.setCurrentUser(currentUser);

        ShowNewPitchInputData inputData = new ShowNewPitchInputData(username, password);

        interactor.execute(inputData);

        assertNotNull(testPresenter.outputData);
        assertEquals(currentUser, testPresenter.outputData.getCurrentUser());
        assertFalse(testPresenter.outputData.isUseCaseFailed());
    }

    /**
     * Test stub for NewPitchDataAccessInterface.
     */
    static class TestNewPitchDataAccess implements ShowNewPitchDataAccessInterface {

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
     * Test stub for NewPitchOutputBoundary.
     */
    static class TestNewPitchPresenter implements ShowNewPitchOutputBoundary {

        ShowNewPitchOutputData outputData;

        @Override
        public void prepareSuccessView(ShowNewPitchOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            fail("Failure path should not be triggered in this test.");
        }
    }
}
