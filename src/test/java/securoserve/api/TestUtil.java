package securoserve.api;

import org.junit.Assert;
import securoserve.api.controllers.LoginController;
import securoserve.api.controllers.UserController;
import securoserve.library.Calamity;
import securoserve.library.User;

/**
 * Created by Jandie on 12-Apr-17.
 */
public class TestUtil {
    public static final String USERNAME = "testuser789987";
    public static final String PASSWORD = "testpwd*()1223";
    public static final String EMAIL = "testuser789987@test123weqr456.nl";
    public static final String CITY = "Amsterdam";

    public static void deleteTempUser(User user) {
        UserController uc = new UserController();
        LoginController lc = new LoginController();

        uc.deleteUser(user.getToken(), user.getId());

        String token = lc.login(USERNAME, PASSWORD);
        Assert.assertEquals(null, token);
    }

    public static User createTempUser() {
        UserController uc = new UserController();
        User user = (User) uc.addUser(1, -1,
                USERNAME, PASSWORD, EMAIL, CITY, "").getReturnObject();

        user = uc.getUser(user.getToken());
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(CITY, user.getCity());
        Assert.assertNotEquals(null, user.getUserType());

        return user;
    }
}
