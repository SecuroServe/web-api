package controllers;

import datarepo.database.Database;
import library.Alert;
import library.Media;
import library.Text;
import library.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.TestUtil;

/**
 * Created by Jandie on 2017-05-08.
 */
public class MediaControllerTest {
    private Database database;
    private MediaController mc;
    private AlertController ac;
    private User user;

    @Before
    public void setUp() throws Exception {
        database = TestUtil.cleanAndBuildTestDatabase();
        mc = new MediaController(database);
        ac = new AlertController(database);
        user = TestUtil.createTempUser(database);
    }

    @Test
    public void getMediaTest() throws Exception {
        Alert alert = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription",
                55, 56, 0).getReturnObject();

        Media media = new Text(-1, "CalamityTestReport", "This is a test!");

        media = (Media) mc.addMedia(user.getToken(), media, alert.getId()).getReturnObject();

        media = (Media) mc.getMedia(user.getToken(), media.getId()).getReturnObject();

        Assert.assertEquals(true, media instanceof Text);
        Assert.assertEquals("CalamityTestReport", media.getName());
        Assert.assertEquals("testDescription", ((Text) media).getText());
    }

    @Test
    public void addMediaTest() throws Exception {

    }

    @Test
    public void updateMediaTest() throws Exception {
    }

    @Test
    public void removeMediaTest() throws Exception {
    }

}