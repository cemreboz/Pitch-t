package use_case.dashboard_show_pitch;

import entity.Pitch;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DashboardInteractorTest {

    private TestDashboardDataAccess testDataAccess;
    private TestDashboardPresenter testPresenter;
    private DashboardInteractor interactor;

    @BeforeEach
    void setUp() {
        testDataAccess = new TestDashboardDataAccess();
        testPresenter = new TestDashboardPresenter();
        interactor = new DashboardInteractor(testDataAccess, testPresenter);
    }

    @Test
    void successDashboardView() {
        String username = "testUser";
        String password = "testPassword";
        Pitch pitch = new Pitch("1", "Great Pitch", "src/",
                "This is a fantastic pitch.", new ArrayList<>());
        DashboardInputData inputData = new DashboardInputData(pitch, username, password);

        interactor.execute(inputData);

        assertNotNull(testPresenter.outputData);
        assertEquals(username, testPresenter.outputData.getUsername());
        assertEquals(password, testPresenter.outputData.getPassword());
        assertEquals(pitch, testPresenter.outputData.getPitch());
        assertFalse(testPresenter.outputData.isUseCaseFailed());
    }

    /**
     * Test stub for DashboardDataAccessInterface.
     */
    static class TestDashboardDataAccess implements DashboardDataAccessInterface {

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
     * Test stub for DashboardOutputBoundary.
     */
    static class TestDashboardPresenter implements DashboardOutputBoundary {

        DashboardOutputData outputData;

        @Override
        public void prepareSuccessView(DashboardOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            fail("Failure path should not be triggered in this test.");
        }
    }
}
