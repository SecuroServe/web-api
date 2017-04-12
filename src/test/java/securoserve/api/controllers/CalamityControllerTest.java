package securoserve.api.controllers;

import org.junit.Assert;
import org.junit.Test;
import securoserve.api.interfaces.ConfirmationMessage;
import securoserve.library.Calamity;
import securoserve.library.Location;
import securoserve.library.User;

import java.util.List;

/**
 * Created by Jandie on 10-Apr-17.
 */
public class CalamityControllerTest {
    private final String USERNAME = "testuser789987";
    private final String PASSWORD = "testpwd*()1223";
    private final String EMAIL = "testuser789987@test123weqr456.nl";
    private final String CITY = "Amsterdam";

    @Test
    public void allCalamity() throws Exception {
        CalamityController cc = new CalamityController();
        UserController uc = new UserController();
        User user = createTempUser();

        Location location = new Location(-1, 5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity(user.getToken(), "nine-eleven-test",
                "test of 911", location, false, true).getReturnObject();

        List<Calamity> calamities = cc.allCalamity();
        Assert.assertEquals(true, calamities.size() > 0);

        boolean check = false;

        for (Calamity calamity : calamities) {
            if (c1.getId() == calamity.getId()) {
                Assert.assertEquals(c1.getConfirmation(), calamity.getConfirmation());
                Assert.assertEquals(c1.getTitle(), calamity.getTitle());
                Assert.assertEquals(c1.getMessage(), calamity.getMessage());

                check = true;
            }
        }

        Assert.assertEquals(true, check);

        ConfirmationMessage.StatusType status = cc.deleteCalamity(user.getToken(), c1.getId()).getStatus();
        Assert.assertEquals(ConfirmationMessage.StatusType.SUCCES, status);

        deleteTempUser(user);
    }

    @Test
    public void addCalamityAssignee() throws Exception {
        CalamityController cc = new CalamityController();
        UserController uc = new UserController();
        User user = createTempUser();

        Location location = new Location(-1, 5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity(user.getToken(), "nine-eleven-test",
                "test of 911", location, false, true).getReturnObject();

        cc.addCalamityAssignee(user.getToken(), c1.getId(), user.getId());

        c1 = cc.calamityById(user.getToken(), c1.getId());

        Assert.assertEquals(true, isAssigned(user, c1));

        cc.deleteCalamityAssignee(user.getToken(), c1.getId(), user.getId());

        c1 = cc.calamityById(user.getToken(), c1.getId());

        Assert.assertEquals(false, isAssigned(user, c1));

        cc.deleteCalamity(user.getToken(), c1.getId());

        deleteTempUser(user);
    }

    private User createTempUser() {
        UserController uc = new UserController();
        User user = (User) uc.addUser(-1, -1,
                USERNAME, PASSWORD, EMAIL, CITY, "").getReturnObject();

        user = uc.getUser(user.getToken());
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(CITY, user.getCity());

        return user;
    }

    private void deleteTempUser(User user) {
        UserController uc = new UserController();
        LoginController lc = new LoginController();

        uc.deleteUser(user.getToken(), user.getId());

        String token = lc.login(USERNAME, PASSWORD);
        Assert.assertEquals(null, token);
    }

    private boolean isAssigned(User user, Calamity calamity) {
        for (User u : calamity.getAssignees()) {
            if (u.getId() == user.getId()) {
                return true;
            }
        }

        return false;
    }

}