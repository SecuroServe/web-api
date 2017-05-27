package controllers;

import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import library.Calamity;
import library.Location;
import library.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.TestUtil;

import java.util.List;

/**
 * Created by Jandie on 10-Apr-17.
 */
public class CalamityControllerTest {
    private Database database;
    private CalamityController cc;
    private UserController uc;
    private User user;

    @Before
    public void setUp() throws Exception {
        database = TestUtil.cleanAndBuildTestDatabase();
        cc = new CalamityController(database);
        uc = new UserController(database);
        user = TestUtil.createTempUser(database);
    }

    /**
     * Tests the allCalamity method in CalamityController.
     *
     * @throws Exception
     */
    @Test
    public void allCalamity() throws Exception {
        Location location = new Location(-1, 5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity(user.getToken(), "nine-eleven-test",
                "test of 911", location.getLatitude(), location.getLongitude(), location.getRadius(),
                false, true).getReturnObject();

        List<Calamity> calamities = (List<Calamity>) cc.allCalamity().getReturnObject();
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
    }

    /**
     * Tests the addCalamityAssignee method in CalamityController.
     *
     * @throws Exception
     */
    @Test
    public void addCalamityAssignee() throws Exception {
        Location location = new Location(-1, 5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity(user.getToken(), "nine-eleven-test",
                "test of 911", location.getLatitude(), location.getLongitude(), location.getRadius(),
                false, true).getReturnObject();

        cc.addCalamityAssignee(user.getToken(), c1.getId(), user.getId());

        c1 = (Calamity) cc.calamityById(c1.getId()).getReturnObject();

        Assert.assertEquals(true, isAssigned(user, c1));

        cc.deleteCalamityAssignee(user.getToken(), c1.getId(), user.getId());

        c1 = (Calamity) cc.calamityById(c1.getId()).getReturnObject();

        Assert.assertEquals(false, isAssigned(user, c1));

        cc.deleteCalamity(user.getToken(), c1.getId());
    }

    @After
    public void tearDown() throws Exception {
        TestUtil.deleteTempUser(user, database);
    }


    /**
     * Checks if a user is assigned to a calamity.
     *
     * @param user     The user.
     * @param calamity The calamity.
     * @return Whether or not the user is assigned to the calamity.
     */
    private boolean isAssigned(User user, Calamity calamity) {
        for (User u : calamity.getAssignees()) {
            if (u.getId() == user.getId()) {
                return true;
            }
        }

        return false;
    }


}