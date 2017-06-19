package controllers;

import datarepo.database.Database;
import exceptions.NoPermissionException;
import interfaces.ConfirmationMessage;
import library.SocialPost;
import library.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.TestUtil;

import java.util.List;

/**
 * Created by Jandie on 19-Jun-17.
 */
public class SocialControllerTest {
    private Database database;
    private SocialController sc;
    private User user;
    private User noPermissionUser;

    @Before
    public void setUp() throws Exception {
        database = TestUtil.cleanAndBuildTestDatabase();
        sc = new SocialController();
        user = TestUtil.createTempUser(database);
        noPermissionUser = TestUtil.createTempNoPermissionUser(database);
    }

    @Test
    public void getSocialPostsTest() throws Exception {
        String keyWords = "vrachtwagen tilburg";

        ConfirmationMessage socialPostsResponse = sc.getSocialPosts(user.getToken(), keyWords);

        Assert.assertEquals(ConfirmationMessage.StatusType.SUCCES, socialPostsResponse.getStatus());

        List<SocialPost> socialPosts = (List<SocialPost>) socialPostsResponse.getReturnObject();

        Assert.assertTrue(socialPosts.size() > 0);
        Assert.assertTrue(socialPosts.get(0).getTweetMessage().length() > 0);
        Assert.assertTrue(socialPosts.get(0).getUsername().length() > 0);
        Assert.assertTrue(socialPosts.get(0).getCreatedDate() != null);
    }

    @Test
    public void getSocialPostsPermissionTest() throws Exception {
        String keyWords = "vrachtwagen tilburg";

        ConfirmationMessage socialPostsResponse = sc.getSocialPosts(noPermissionUser.getToken(), keyWords);

        Assert.assertEquals(ConfirmationMessage.StatusType.ERROR, socialPostsResponse.getStatus());
        Assert.assertTrue(socialPostsResponse.getReturnObject() instanceof NoPermissionException);
    }
}