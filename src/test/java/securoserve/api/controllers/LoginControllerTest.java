package securoserve.api.controllers;

import org.junit.Assert;
import org.junit.Test;
import securoserve.api.TestUtil;
import securoserve.api.interfaces.ConfirmationMessage;
import securoserve.library.User;

/**
 * Created by Jandie on 4/3/2017.
 */
public class LoginControllerTest {

    @Test
    public void login() throws Exception {
        UserController uc = new UserController();
        LoginController lc = new LoginController();
        TestUtil.createTempUser();

        String token = lc.login(TestUtil.USERNAME, TestUtil.PASSWORD);

        Assert.assertEquals(true, token.length() > 5);

        User user = uc.getUser(token);
        Assert.assertEquals(TestUtil.USERNAME, user.getUsername());
        Assert.assertEquals(TestUtil.EMAIL, user.getEmail());
        Assert.assertEquals(TestUtil.CITY, user.getCity());
        Assert.assertNotEquals(null, user.getUserType());

        TestUtil.deleteTempUser(user);
    }

    @Test
    public void performance() throws Exception {
        for (int i = 0; i < 10; i++) {
            login();
        }
    }

}