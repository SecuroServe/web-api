package logic;

import api.ConfirmationMessage;
import dataRepo.CalamityRepo;
import dataRepo.Database;
import library.Calamity;
import library.Location;

/**
 * Created by yannic on 20/03/2017.
 */
public class CalamityLogic {
    private Database database;
    private CalamityRepo calamityRepo;

    public CalamityLogic() {
        this.database = new Database();
        calamityRepo = new CalamityRepo(database);
    }

    public ConfirmationMessage updateCalamity(String token, int id, String name, String description, Location location) {
        //check token
        calamityRepo.updateCalamity(id, name, description, location);
        return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "no method definition", null);
    }

    public ConfirmationMessage getCalamity(String token, int id) {
        Calamity returnCal = calamityRepo.getCalamity(id);
        return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "got calamity", returnCal);
    }

    public ConfirmationMessage addCalamity(String token, String name, String description, Location location) {
        return null;
    }
}
