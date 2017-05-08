package controllers;

import datarepo.database.Database;
import library.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.TestUtil;

/**
 * Created by Jandie on 4/3/2017.
 */
public class LoginControllerTest {
    private Database database;

    @Before
    public void setUp() throws Exception {
        this.database = TestUtil.cleanAndBuildTestDatabase();
    }

    /**
     * Tests the login method in LoginController.
     *
     * @throws Exception
     */
    @Test
    public void login() throws Exception {
        UserController uc = new UserController(database);
        LoginController lc = new LoginController(database);
        TestUtil.createTempUser(database);

        String token = (String) lc.login(TestUtil.USERNAME, TestUtil.PASSWORD).getReturnObject();

        Assert.assertEquals(true, token.length() > 5);

        User user = (User) uc.getUser(token).getReturnObject();
        Assert.assertEquals(TestUtil.USERNAME, user.getUsername());
        Assert.assertEquals(TestUtil.EMAIL, user.getEmail());
        Assert.assertEquals(TestUtil.CITY, user.getCity());
        Assert.assertNotEquals(null, user.getUserType());

        TestUtil.deleteTempUser(user, database);
    }

}