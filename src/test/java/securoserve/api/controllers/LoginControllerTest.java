package securoserve.api.controllers;

import org.junit.Assert;
import org.junit.Test;
import securoserve.api.TestUtil;
import securoserve.library.User;

/**
 * Created by Jandie on 4/3/2017.
 */
public class LoginControllerTest {

    /**
     * Tests the login method in LoginController.
     *
     * @throws Exception
     */
    @Test
    public void login() throws Exception {
        UserController uc = new UserController();
        LoginController lc = new LoginController();
        TestUtil.createTempUser();

        String token = (String) lc.login(TestUtil.USERNAME, TestUtil.PASSWORD).getReturnObject();

        Assert.assertEquals(true, token.length() > 5);

        User user = (User) uc.getUser(token).getReturnObject();
        Assert.assertEquals(TestUtil.USERNAME, user.getUsername());
        Assert.assertEquals(TestUtil.EMAIL, user.getEmail());
        Assert.assertEquals(TestUtil.CITY, user.getCity());
        Assert.assertNotEquals(null, user.getUserType());

        TestUtil.deleteTempUser(user);
    }

}