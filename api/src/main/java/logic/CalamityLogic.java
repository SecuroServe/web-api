package logic;

import com.google.gson.JsonObject;
import datarepo.CalamityRepo;
import datarepo.UserRepo;
import datarepo.database.Database;
import exceptions.NoPermissionException;
import exceptions.NoSuchCalamityException;
import interfaces.ConfirmationMessage;
import library.*;
import utils.FCMHelper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the logic of library.Calamity
 * <p>
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

    public CalamityLogic(Database database) {
        this.database = database;
        calamityRepo = new CalamityRepo(database);
        userRepo = new UserRepo(database);
    }

    /**
     * Updates an exsisting library.Calamity into the database.
     *
     * @param token       The authentication token.
     * @param id          The id of the calamity.
     * @param name        The name of the calamity.
     * @param description The description of the calamity.
     * @param location    The location of the calamity.
     * @param isConfirmed The confirmation state of the calamity.
     * @param isClosed    Whether or not the calamity is closed.
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage updateCalamity(String token, int id, String name, String description, Location location,
                                              boolean isConfirmed, boolean isClosed) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_UPDATE);

            Calamity calamity = calamityRepo.getCalamity(id);
            calamity.setTitle(name);
            calamity.setMessage(description);
            calamity.setLocation(location);
            calamity.setConfirmed(isConfirmed);
            calamity.setClosed(isClosed);
            calamityRepo.updateCalamity(calamity);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "updated calamity", calamity);
        } catch (NoPermissionException | ParseException | NoSuchAlgorithmException | SQLException e) {
            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Update calamity failed!", e);

            return new ConfirmationMessage(
                    ConfirmationMessage.StatusType.ERROR, "Error while updating calamity", e);
        }
    }

    /**
     * Gets a calamity b id.
     *
     * @param calamityId The id of the calamity.
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage getCalamity(int calamityId) {
        try {
            Calamity returnCal = calamityRepo.getCalamity(calamityId);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "got calamity", returnCal);
        } catch (ParseException | NoSuchAlgorithmException | SQLException e) {
            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Error while loading calamity", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while loading calamity", e);
        }
    }

    /**
     * Adds new calamity.
     *
     * @param token       The authentication token.
     * @param name        The name of the calamity.
     * @param description The description of the calamity.
     * @param location    The location of the calamity.
     * @param isConfirmed The confirmation state of the calamity.
     * @param isClosed    Whether or not the calamity is closed.
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage addCalamity(String token, String name, String description,
                                           Location location, boolean isConfirmed, boolean isClosed) {
        try {
            User user = userRepo.getUser(token);

            user.getUserType().containsPermission(UserType.Permission.CALAMITY_ADD);

            Calamity calamity = new Calamity(-1, location, user, isConfirmed, isClosed, new Date(), name, description);
            calamityRepo.addCalamity(calamity);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Added calamity", calamity);
        } catch (NoPermissionException | SQLException | ParseException | NoSuchAlgorithmException e) {
            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Error while adding a calamity", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while adding a calamity", e);
        }
    }

    /**
     * Deletes a calamity.
     *
     * @param token      The authentication token.
     * @param calamityId The id of the calamity.
     * @return Feedback about the action.
     */
    public ConfirmationMessage deleteCalamity(String token, int calamityId) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_DELETE);
            userRepo.getUser(token);
            calamityRepo.deleteCalamity(calamityId);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Deleted calamity", null);
        } catch (NoPermissionException | SQLException | ParseException | NoSuchAlgorithmException e) {
            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Delete calamity failed!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Delete calamity failed!", e);
        }
    }

    /**
     * Adds a calamity assignee.
     *
     * @param token      The authentication token.
     * @param calamityId The id of the calamity.
     * @param userId     The id of the user (assignee).
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage addCalamityAssignee(String token, int calamityId, int userId) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_ADD_ASSIGNEE);
            calamityRepo.addCalamityAssignee(calamityId, userId);

            String firebaseToken = userRepo.getFirebaseToken(userId);
            if(firebaseToken != "") {

                JsonObject object = new JsonObject();
                object.addProperty("TITLE", "You've been added to a calamity");
                object.addProperty("TEXT", "Click here to see more information...");

                FCMHelper fcm = FCMHelper.getInstance();
                fcm.sendData(FCMHelper.TYPE_TO, firebaseToken, object);

                return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                        "library.Calamity assignee added and alerted", null);
            }

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "library.Calamity assignee added but not alerted", null);

        } catch (NoPermissionException | SQLException | ParseException | NoSuchAlgorithmException | IOException  e) {

            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Error while adding calamity assignee", e);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while adding calamity assignee", e);
        }
    }

    /**
     * Deletes a assignee.
     *
     * @param token      The authentication token.
     * @param calamityId The id of the calamity.
     * @param userId     The id of the user.
     * @return ConfirmationMessage with feedback.
     */
    public ConfirmationMessage deleteCalamityAssignee(String token, int calamityId, int userId) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_DELETE_ASSIGNEE);

            calamityRepo.deleteCalamityAssignee(calamityId, userId);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "library.Calamity assignee deleted", null);
        } catch (NoPermissionException | ParseException | NoSuchAlgorithmException | SQLException e) {
            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Error while deleting calamity assignee", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while deleting calamity assignee", e);
        }
    }

    /**
     * Get all existing calamities from the Database
     *
     * @return ConfirmationMessage with feedback
     */
    public ConfirmationMessage allCalamity() {
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Get all calamities", calamityRepo.allCalamity());
        } catch (SQLException | NoSuchAlgorithmException | ParseException e) {
            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Error while getting all calamities", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while getting all calamities", e);
        }
    }

    /**
     * Adds a post to a calamity
     *
     * @param token      The authentication token.
     * @param userId     The id of the user who added the post.
     * @param calamityId The if of the calamity to add the post to.
     * @param text       The text in the post.
     * @return Confirmation message with feedback about the addition.
     */
    public ConfirmationMessage addPost(String token, int userId, int calamityId, String text) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_POST);

            User user = userRepo.getUser(token);

            if (text.trim().equals("")) {
                return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                        "Error while adding Post to Calamity, because text is empty", null);
            }

            Post post = calamityRepo.addPostToCalamity(user, calamityId, text);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Post added to Calamity", post);
        } catch (NoPermissionException | ParseException | NoSuchAlgorithmException | SQLException |
                NoSuchCalamityException e) {
            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Error while adding Post to Calamity.", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while adding Post to Calamity.", e);
        }
    }

    /**
     * Adds a plan to a calamity
     *
     * @param token      The authentication token.
     * @param calamityId The calamity to add the plan to.
     * @param plan       The plan to add.
     * @return Confirmation message with feedback about the addition containing the new plan.
     */
    public ConfirmationMessage addPlan(String token, int calamityId, Plan plan) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_PLAN);

            plan = calamityRepo.addPlan(calamityId, plan);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Plan added to Calamity", plan);
        } catch (NoPermissionException | SQLException | ParseException | NoSuchAlgorithmException |
                NoSuchCalamityException e) {
            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Error while adding Plan to Calamity.", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while adding Plan to Calamity.", e);
        }
    }

    public ConfirmationMessage updatePlan(String token, Plan plan) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_PLAN);

            calamityRepo.updatePlan(plan);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Plan updated", plan);

        } catch (NoPermissionException | NoSuchAlgorithmException | ParseException | SQLException e) {
            Logger.getLogger(CalamityLogic.class.getName()).log(Level.SEVERE,
                    "Error while updating Plan.", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while updating plan.", e);
        }
    }
}
