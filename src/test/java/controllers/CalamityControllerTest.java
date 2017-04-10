package controllers;

import api.ConfirmationMessage;
import library.Calamity;
import library.Location;
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
    @Test
    public void allCalamity() throws Exception {
        CalamityController cc = new CalamityController();

        Location location = new Location(5, 51, 1);

        Calamity c1 = (Calamity) cc.addCalamity("asdasd", "nine-eleven-test", "test of 911", location).getReturnObject();

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

}