package controllers;

import api.ConfirmationMessage;
import library.User;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jandie on 4/3/2017.
 */
public class LoginControllerTest {
    private final String USERNAME = "testuser789987";
    private final String PASSWORD = "testpwd*()1223";
    private final String EMAIL = "testuser789987@test123weqr456.nl";
    private final String CITY = "Amsterdam";

    @Test
    public void login() throws Exception {
        UserController uc = new UserController();
        LoginController lc = new LoginController();
        User user;

        ConfirmationMessage cm =
                uc.addUser(-1, -1,
                        USERNAME, PASSWORD, EMAIL, CITY, "");

        Assert.assertEquals(ConfirmationMessage.StatusType.SUCCES, cm.getStatus());

        user = (User) cm.getReturnObject();
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(CITY, user.getCity());

        String token = lc.login(USERNAME, PASSWORD);

        Assert.assertEquals(true, token.length() > 5);

        user = uc.getUser(token);
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(CITY, user.getCity());

        uc.deleteUser(user.getToken(), user.getId());

        token = lc.login(USERNAME, PASSWORD);
        Assert.assertEquals(null, token);
    }

    @Test
    public void performance() throws Exception{
        for (int i = 0; i < 10; i++) {
            login();
        }
    }

}