package securoserve.api.logic;

import securoserve.api.ConfirmationMessage;
import securoserve.api.datarepo.CalamityRepo;
import securoserve.api.datarepo.Database;
import securoserve.api.datarepo.UserRepo;
import securoserve.library.Calamity;
import securoserve.library.Location;
import securoserve.library.User;

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
        return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "no method definition", null);
    }

    public ConfirmationMessage getCalamity(String token, int id) {
        //check token
        Calamity returnCal = null;
        try {
            returnCal = calamityRepo.getCalamity(id);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "got calamity", returnCal);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Error while loading calamity", null);
        }
    }

    public ConfirmationMessage addCalamity(String token, String name, String description, Location location, boolean isConfirmed, boolean isClosed) {
        //check token
        try {
            User user = userRepo.getUser(token);
            Calamity calamity = new Calamity(location, user, isConfirmed, isClosed, null, name, description);
            Calamity returnCal = calamityRepo.addCalamity(calamity);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "Added calamity", returnCal);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Error while adding a calamity", null);
        }
    }
}
