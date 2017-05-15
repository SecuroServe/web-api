package controllers;

import datarepo.database.Database;
import library.Alert;
import library.Calamity;
import library.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.TestUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jandie on 2017-05-01.
 */
public class AlertControllerTest {
    private Database database;
    private AlertController ac;
    private CalamityController cc;
    private User user;

    @Before
    public void setUp() throws Exception {
        database = TestUtil.cleanAndBuildTestDatabase();
        ac = new AlertController(database);
        cc = new CalamityController(database);
        user = TestUtil.createTempUser(database);
    }

    @Test
    public void addAlertTest() throws Exception {
        Alert alert = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                55, 56, 1).getReturnObject();

        assertEquals("testAlert", alert.getName());
        assertEquals("testDescription", alert.getDescription());
        assertEquals(55, alert.getLocation().getLatitude(), 0.01);
        assertEquals(56, alert.getLocation().getLongitude(), 0.01);
        assertEquals(1, alert.getLocation().getRadius(), 0.01);

        alert = ((List<Alert>) ac.getAllAlerts(user.getToken()).getReturnObject()).get(0);

        assertEquals("testAlert", alert.getName());
        assertEquals("testDescription", alert.getDescription());
        assertEquals(55, alert.getLocation().getLatitude(), 0.01);
        assertEquals(56, alert.getLocation().getLongitude(), 0.01);
        assertEquals(1, alert.getLocation().getRadius(), 0.01);
    }

    @Test
    public void removeAlertTest() throws Exception {
        Alert alert1 = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                55, 56, 1).getReturnObject();

        Alert alert2 = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                55, 56, 1).getReturnObject();

        ac.removeAlert(user.getToken(), alert1.getId());
        ac.removeAlert(user.getToken(), alert2.getId());

        int size = ((List<Alert>) ac.getAllAlerts(user.getToken()).getReturnObject()).size();

        assertEquals(0, size);
    }

    @Test
    public void proposeCalamityTest() throws Exception {
        Alert alert1 = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                52.369040, 9.748287, 0).getReturnObject();
        Alert alert2 = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                52.369105, 9.748333, 0).getReturnObject();
        Alert alert3 = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                52.369258, 9.748698, 0).getReturnObject();
        Alert alert4 = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                52.369253, 9.748971, 0).getReturnObject();
        Alert alert5 = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                52.368909, 9.748703, 0).getReturnObject();
        Alert alert6 = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                52.368914, 9.748370, 0).getReturnObject();

        int size = ((List<Calamity>) cc.allCalamity().getReturnObject()).size();

        assertEquals(1, size);
    }

    @Test
    public void updateAlertTest() throws Exception {
        Alert alert = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription", 10,
                52.369040, 9.748287, 0).getReturnObject();

        ac.updateAlert(user.getToken(), alert.getId(), "updateAlert", "updateDescription", 10,
                55, 56, 1);

        Alert updatedAlert = (Alert) ac.getAlert(user.getToken(), alert.getId()).getReturnObject();

        assertEquals("updateAlert", updatedAlert.getName());
        assertEquals("updateDescription", updatedAlert.getDescription());
        assertEquals(55, updatedAlert.getLocation().getLatitude(), 0.01);
        assertEquals(56, updatedAlert.getLocation().getLongitude(), 0.01);
        assertEquals(1, updatedAlert.getLocation().getRadius(), 0.01);
    }

    @After
    public void tearDown() throws Exception {
        TestUtil.deleteTempUser(user, database);
    }
}