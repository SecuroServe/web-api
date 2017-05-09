package controllers;

import datarepo.database.Database;
import library.*;
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
    public void getMediaTextTest() throws Exception {
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
    public void getMediaFileTest() throws Exception {
        Alert alert = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription",
                55, 56, 0).getReturnObject();

        Media media = new MediaFile(-1, "TestPhoto",
                "test.jpeg", MediaFile.FileType.PHOTO);

        media = (Media) mc.addMedia(user.getToken(), media, alert.getId()).getReturnObject();
        media = (Media) mc.getMedia(user.getToken(), media.getId()).getReturnObject();

        Assert.assertEquals(true, media instanceof Text);
        Assert.assertEquals("TestPhoto", media.getName());
        Assert.assertEquals("test.jpeg", ((MediaFile) media).getFileName());
        Assert.assertEquals(MediaFile.FileType.PHOTO, ((MediaFile) media).getFileType());
    }

    @Test
    public void updateMediaTest() throws Exception {

    }

    @Test
    public void removeMediaTest() throws Exception {
        Alert alert = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription",
                55, 56, 0).getReturnObject();

        Media media = new Text(-1, "CalamityTestReport", "This is a test!");

        media = (Media) mc.addMedia(user.getToken(), media, alert.getId()).getReturnObject();
        media = (Media) mc.getMedia(user.getToken(), media.getId()).getReturnObject();
        mc.removeMedia(user.getToken(), media.getId());
        media = (Media) mc.getMedia(user.getToken(), media.getId()).getReturnObject();

        Assert.assertEquals(null, media);
    }

}