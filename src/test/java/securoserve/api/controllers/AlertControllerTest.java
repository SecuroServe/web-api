package securoserve.api.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import securoserve.api.TestUtil;
import securoserve.api.datarepo.database.Database;
import securoserve.library.Alert;
import securoserve.library.User;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jandie on 2017-05-01.
 */
public class AlertControllerTest {
    private Database database;
    private AlertController ac;
    private User user;

    @Before
    public void setUp() throws Exception {
        database = TestUtil.cleanAndBuildTestDatabase();
        ac = new AlertController(database);
        user = TestUtil.createTempUser(database);
    }

    @Test
    public void addAlert() throws Exception {
        Alert alert = (Alert) ac.addAlert(user.getToken(), "testAlert", "testDescription",
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

    @After
    public void tearDown() throws Exception {
        TestUtil.deleteTempUser(user, database);
    }
}