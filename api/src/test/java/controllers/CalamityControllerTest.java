package controllers;

import datarepo.database.Database;
import exceptions.NoPermissionException;
import exceptions.NoSuchCalamityException;
import interfaces.ConfirmationMessage;
import library.*;
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
    private User userNoPermission;

    @Before
    public void setUp() throws Exception {
        database = TestUtil.cleanAndBuildTestDatabase();
        cc = new CalamityController(database);
        uc = new UserController(database);
        user = TestUtil.createTempUser(database);
        userNoPermission = TestUtil.createTempNoPermissionUser(database);
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

    /**
     * Tests addition of a post to a calamity.
     *
     * @throws Exception Exception.
     */
    @Test
    public void addPostToCalamityTest() throws Exception {
        Location location = new Location(-1, 5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity(user.getToken(), "nine-eleven-test",
                "test of 911", location.getLatitude(), location.getLongitude(), location.getRadius(),
                false, true).getReturnObject();

        ConfirmationMessage addCalamityFeedback = cc.addPost(user.getToken(), user.getId(), c1.getId(), "Mijn mening test.");
        Post post = (Post) addCalamityFeedback.getReturnObject();

        Assert.assertEquals(ConfirmationMessage.StatusType.SUCCES, addCalamityFeedback.getStatus());
        Assert.assertEquals("Mijn mening test.", post.getText());

        Calamity c2 = (Calamity) cc.calamityById(c1.getId()).getReturnObject();

        Assert.assertEquals("Mijn mening test.", c2.getPosts().get(0).getText());
    }

    /**
     * Tests rejection of user with no permission.
     *
     * @throws Exception Exception.
     */
    @Test
    public void addPostToCalamityNoPermissionTest() throws Exception {
        ConfirmationMessage addPostFeedback = cc.addPost(userNoPermission.getToken(), user.getId(), 1, "Mijn mening test.");

        Assert.assertEquals(ConfirmationMessage.StatusType.ERROR, addPostFeedback.getStatus());
        Assert.assertTrue(addPostFeedback.getReturnObject() instanceof NoPermissionException);
    }

    /**
     * Tests rejection of add request when unknown calamityId.
     *
     * @throws Exception Exception.
     */
    @Test
    public void addPostToUnknownCalamity() throws Exception {
        ConfirmationMessage addPostFeedback = cc.addPost(user.getToken(), user.getId(), 9856, "Mijn mening test.");

        Assert.assertEquals(ConfirmationMessage.StatusType.ERROR, addPostFeedback.getStatus());
        Assert.assertTrue(addPostFeedback.getReturnObject() instanceof NoSuchCalamityException);
    }

    /**
     * Tests rejection of post with empty text.
     *
     * @throws Exception Exception.
     */
    @Test
    public void addPostWithEmptyTextTest() throws Exception {
        Location location = new Location(-1, 5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity(user.getToken(), "nine-eleven-test",
                "test of 911", location.getLatitude(), location.getLongitude(), location.getRadius(),
                false, true).getReturnObject();

        ConfirmationMessage addPostFeedback = cc.addPost(user.getToken(), user.getId(), c1.getId(), "");

        Assert.assertEquals(ConfirmationMessage.StatusType.ERROR, addPostFeedback.getStatus());
    }

    /**
     * Tests addition of plan to calamity.
     *
     * @throws Exception Exception.
     */
    @Test
    public void addPlanToCalamityTest() throws Exception {
        Location location = new Location(-1, 5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity(user.getToken(), "nine-eleven-test",
                "test of 911", location.getLatitude(), location.getLongitude(), location.getRadius(),
                false, true).getReturnObject();

        Plan plan = new Plan(0, "Do this then that.");

        ConfirmationMessage addPlanFeedback = cc.addPlan(user.getToken(), c1.getId(), plan);

        Plan returnedPlan = (Plan) addPlanFeedback.getReturnObject();

        Assert.assertEquals("Do this then that.", returnedPlan.getDescription());

        Calamity c2 = (Calamity) cc.calamityById(c1.getId()).getReturnObject();

        Assert.assertEquals("Do this then that.", c2.getPlan().getDescription());
    }

    @Test
    public void addPlanToCalamityNoPermissionTest() throws Exception {
        Plan plan = new Plan(0, "Do this then that.");

        Location location = new Location(-1, 5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity(user.getToken(), "nine-eleven-test",
                "test of 911", location.getLatitude(), location.getLongitude(), location.getRadius(),
                false, true).getReturnObject();

        ConfirmationMessage addPlanFeedback = cc.addPlan(userNoPermission.getToken(), c1.getId(), plan);

        Assert.assertEquals(ConfirmationMessage.StatusType.ERROR, addPlanFeedback.getStatus());
        Assert.assertTrue(addPlanFeedback.getReturnObject() instanceof NoPermissionException);
    }

    @Test
    public void addPlanToUnknownCalamityTest() {
        Plan plan = new Plan(0, "Do this then that.");

        ConfirmationMessage addPlanFeedback = cc.addPlan(user.getToken(), -1, plan);

        Assert.assertEquals(ConfirmationMessage.StatusType.ERROR, addPlanFeedback.getStatus());
        Assert.assertTrue(addPlanFeedback.getReturnObject() instanceof NoSuchCalamityException);
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