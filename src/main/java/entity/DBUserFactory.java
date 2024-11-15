package entity;

import java.util.List;

/**
 * Factory for creating DBUser objects.
 */
public class DBUserFactory implements UserFactory {

    @Override
    public User create(String name, String password) {
        return new DBUser(name, password);
    }

    /**
     * Create a DB user with pitches and experts history.
     * @param name username of user
     * @param password password of user
     * @param pitches list of pitch objects associated to user
     * @param experts list of experts associated to user
     * @return
     */
    public DBUser create(String name, String password, List<Pitch> pitches, List<Expert> experts) {
        final DBUser dbUser = new DBUser(name, password);
        dbUser.setPitches(pitches);
        dbUser.setExperts(experts);
        return dbUser;
    }
}