package logic;

import api.ConfirmationMessage;
import dataRepo.CalamityRepo;
import dataRepo.Database;
import dataRepo.LocationRepo;
import dataRepo.UserRepo;
import enums.StatusType;
import library.Calamity;
import library.Location;
import library.User;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by yannic on 20/03/2017.
 */
public class CalamityLogic {
    private Database database;
    private CalamityRepo calamityRepo;
    private UserRepo userRepo;

    public CalamityLogic() {
        this.database = new Database();
        calamityRepo = new CalamityRepo(database);

    }

    public ConfirmationMessage updateCalamity(String token, int id, String name, String description, Location location) {
        //check token
        calamityRepo.updateCalamity(id, name, description, location);
        return new ConfirmationMessage(StatusType.ERROR, "no method definition", null);
    }

    public ConfirmationMessage getCalamity(String token, int id) {
        //check token
        Calamity returnCal = null;
        try {
            returnCal = calamityRepo.getCalamity(id);
            return new ConfirmationMessage(StatusType.SUCCES, "got calamity", returnCal);
        } catch (Exception e) {
            return new ConfirmationMessage(StatusType.ERROR, "Error while loading calamity", null);
        }
    }

    public ConfirmationMessage addCalamity(String token, String name, String description, Location location, boolean isConfirmed, boolean isClosed) {
        //check token
        try {
            User user = userRepo.getUser(token);
            Calamity calamity = new Calamity(location, user, isConfirmed, isClosed, null, name, description);
            Calamity returnCal = calamityRepo.addCalamity(calamity);
            return new ConfirmationMessage(StatusType.SUCCES, "Added calamity", returnCal);
        } catch (Exception e) {
            return new ConfirmationMessage(StatusType.ERROR, "Error while adding a calamity", null);
        }
    }
}
