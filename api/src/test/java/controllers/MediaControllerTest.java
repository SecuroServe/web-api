package controllers;

import datarepo.database.Database;
import library.User;
import org.junit.Before;
import org.junit.Test;
import util.TestUtil;

/**
 * Created by Jandie on 2017-05-08.
 */
public class MediaControllerTest {
    private Database database;
    private User user;

    @Before
    public void setUp() throws Exception {
        database = TestUtil.cleanAndBuildTestDatabase();
        user = TestUtil.createTempUser(database);
    }

    @Test
    public void getMedia() throws Exception {
    }

    @Test
    public void addMedia() throws Exception {
    }

    @Test
    public void updateMedia() throws Exception {
    }

    @Test
    public void removeMedia() throws Exception {
    }

}