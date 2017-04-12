package securoserve.api.logic;

import securoserve.api.interfaces.ConfirmationMessage;
import securoserve.api.datarepo.CalamityRepo;
import securoserve.api.datarepo.Database;
import securoserve.api.datarepo.UserRepo;
import securoserve.library.Calamity;
import securoserve.library.Location;
import securoserve.library.User;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
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
        userRepo = new UserRepo(database);
    }

    /**
     * Updates an exsisting Calamity into the database.
     * @param token The authentication token.
     * @param id The id of the calamity.
     * @param name The name of the calamity.
     * @param description The description of the calamity.
     * @param location The location of the calamity.
     * @param isConfirmed The confirmation state of the calamity.
     * @param isClosed Whether or not the calamity is closed.
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage updateCalamity(String token, int id, String name, String description, Location location, boolean isConfirmed, boolean isClosed) {
        //check token
        try {
            Calamity calamity = calamityRepo.getCalamity(id);
            calamity.setTitle(name);
            calamity.setMessage(description);
            calamity.setLocation(location);
            calamity.setConfirmed(isConfirmed);
            calamity.setClosed(isClosed);
            calamityRepo.updateCalamity(calamity);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "updated calamity", calamity);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Error while updating calamity", null);
        }
    }

    /**
     * Gets a calamity b id.
     * @param token The authentication token.
     * @param calamityId The id of the calamity.
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage getCalamity(String token, int calamityId) {
        //check token
        Calamity returnCal = null;
        try {
            returnCal = calamityRepo.getCalamity(calamityId);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "got calamity", returnCal);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Error while loading calamity", null);
        }
    }

    /**
     * Adds new calamity.
     * @param token The authentication token.
     * @param name The name of the calamity.
     * @param description The description of the calamity.
     * @param location The location of the calamity.
     * @param isConfirmed The confirmation state of the calamity.
     * @param isClosed Whether or not the calamity is closed.
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage addCalamity(String token, String name, String description, Location location, boolean isConfirmed, boolean isClosed) {
        //check token
        try {
            User user = userRepo.getUser(token);
            Calamity calamity = new Calamity(-1, location, user, isConfirmed, isClosed, new Date(), name, description);
            calamityRepo.addCalamity(calamity);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "Added calamity", calamity);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Error while adding a calamity", null);
        }
    }

    public ConfirmationMessage deleteCalamity(String token, int calamityId){
        try {
            userRepo.getUser(token);
            calamityRepo.deleteCalamity(calamityId);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "Deleted calamity", null);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Delete calamity failed!", null);
        }
    }
  
    /**
     * Adds a calamity assignee.
     * @param token The authentication token.
     * @param calamityId The id of the calamity.
     * @param userId The id of the user (assignee).
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage addCalamityAssignee(String token, int calamityId, int userId) {
        // check token

        try {
            calamityRepo.addCalamityAssignee(calamityId, userId);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Calamity assignee added", null);
        } catch (Exception e) {
            return new ConfirmationMessage(
                    ConfirmationMessage.StatusType.ERROR,
                    "Error while adding calamity assignee",
                    null);
        }
    }

    /**
     * Deletes a assignee.
     * @param token The authentication token.
     * @param calamityId The id of the calamity.
     * @param userId The id of the user.
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage deleteCalamityAssignee(String token, int calamityId, int userId) {
        // check token

        try {
            calamityRepo.deleteCalamityAssignee(calamityId, userId);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Calamity assignee deleted", null);
        } catch (Exception e) {
            return new ConfirmationMessage(
                    ConfirmationMessage.StatusType.ERROR,
                    "Error while deleting calamity assignee",
                    null);
        }
    }

    /**
     * Get all existing calamities from the Database
     * @return ConfirmationMessage with feedback
     */
    public ConfirmationMessage allCalamity(){
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "Get all calamities", calamityRepo.allCalamity());
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Error while getting all calamities", null);
        }
    }
}
