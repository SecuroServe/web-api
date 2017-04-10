package controllers;

import api.ConfirmationMessage;
import library.Calamity;
import library.Location;
import library.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.L;
import static org.junit.Assert.*;

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

        Location location = new Location(5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity("asdasd", "nine-eleven-test",
                "test of 911", location, false, true).getReturnObject();

        List<Calamity> calamities = cc.allCalamity();
        Assert.assertEquals(true, calamities.size() > 0);

        boolean check = false;

        for (Calamity calamity : calamities){
            if (c1.getId() == calamity.getId()) {
                Assert.assertEquals(c1.getConfirmation(), calamity.getConfirmation());
                Assert.assertEquals(c1.getTitle(), calamity.getTitle());
                Assert.assertEquals(c1.getMessage(), calamity.getMessage());

                check = true;
            }
        }

        Assert.assertEquals(true, check);

        ConfirmationMessage.StatusType status = cc.deleteCalamity("dasjdjkasd", c1.getId()).getStatus();
        Assert.assertEquals(ConfirmationMessage.StatusType.SUCCES, status);
    }

    @Test
    public void addCalamityAssignee() throws Exception {
        CalamityController cc = new CalamityController();
        UserController uc = new UserController();
        User user;

        Location location = new Location(5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity("asdasd", "nine-eleven-test",
                "test of 911", location, false, true).getReturnObject();

        ConfirmationMessage cm =
                uc.addUser(-1, -1,
                        USERNAME, PASSWORD, EMAIL, CITY, "");

        user = (User) cm.getReturnObject();

        cc.addCalamityAssignee("sdasd", c1.getId(), user.getId());

        c1 = cc.calamityById("sdasd", c1.getId());

        Assert.assertEquals(true, isAssigned(user, c1));

        cc.deleteCalamityAssignee("sdasd", c1.getId(), user.getId());

        c1 = cc.calamityById("sdasd", c1.getId());

        Assert.assertEquals(false, isAssigned(user, c1));
    }

    private boolean isAssigned(User user, Calamity calamity){
        for (User u : calamity.getAssignees()) {
            if (u.getId() == user.getId()) {
                return true;
            }
        }

        return false;
    }

}