package logic;

import com.google.gson.JsonObject;
import datarepo.UserRepo;
import datarepo.database.Database;
import exceptions.NoPermissionException;
import exceptions.PasswordsDontMatchException;
import exceptions.WrongUsernameOrPasswordException;
import interfaces.ConfirmationMessage;
import library.User;
import library.UserType;
import utils.FCMHelper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the user logic.
 * <p>
 * Created by Jandie on 20-3-2017.
 */
public class UserLogic {
    private Database database;
    private UserRepo userRepo;

    public UserLogic() {
        database = new Database();
        userRepo = new UserRepo(database);
    }

    public UserLogic(Database database) {
        this.database = database;
        userRepo = new UserRepo(database);
    }

    /**
     * Creates and returns a new token for a user if username and password
     * are correct.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The token of the user.
     */
    public ConfirmationMessage login(String username, String password) {
        try {
            String token = userRepo.login(username, password);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Login successful!", token);
        } catch (SQLException | ParseException | NoSuchAlgorithmException | WrongUsernameOrPasswordException e) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE,
                    "Login failed!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Login failed!", e);
        }
    }

    /**
     * Creates and returns a new user. Also checks for valid username and valid passwords.
     *
     * @param username  The username of the user to register.
     * @param password1 The password of the user.
     * @param password2 The retyped password.
     * @param email     The email of the User.
     * @param city      The city of the user
     * @return A ConfirmationMessage with the new User.
     */
    public ConfirmationMessage register(String username, String password1, String password2, String email, String city) {
        try {
            if (!password1.equals(password2)) {
                throw new PasswordsDontMatchException("The 2 passwords don't match.");
            }

            User user = userRepo.register(3, -1, username, password1, email, city);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "User added!", user);

        } catch (NoSuchAlgorithmException | SQLException | ParseException | PasswordsDontMatchException e) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE,
                    "User addition failed!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Addition of user failed!", e);
        }
    }

    public ConfirmationMessage addUser(int userTypeId, int buildingId,
                                       String username, String password, String email, String city, String token) {

        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.USER_REGISTER);

            User user = userRepo.register(userTypeId, buildingId, username, password, email, city);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "library.User added!", user);

        } catch (NoSuchAlgorithmException | SQLException | ParseException | NoPermissionException e) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE,
                    "library.User addition failed!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Addition of user failed!", e);
        }
    }

    public ConfirmationMessage deleteUser(String token, int id) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.USER_DELETE);

            userRepo.deleteUser(id);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "library.User deleted!", null);
        } catch (SQLException | ParseException | NoSuchAlgorithmException | NoPermissionException e) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE,
                    "library.User deletion failed!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "library.User deletion failed!", e);
        }
    }

    public ConfirmationMessage getUser(String token) throws NoSuchAlgorithmException, ParseException {
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "library.User retrieved!", userRepo.getUser(token));
        } catch (SQLException e) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE,
                    "Failed to retrieve user!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Failed to retrieve user!", e);
        }
    }

    public ConfirmationMessage getAllUsers(String token) throws NoSuchAlgorithmException, ParseException {
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "List<library.User> retrieved!", userRepo.getAllUsers());
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to get list of users from repo", e);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Failed to get list of users", e);
        }
    }

    public ConfirmationMessage setFirebaseToken(String usertoken, String firebaseToken) {
        try{
            userRepo.getUser(usertoken).getUserType().containsPermission(UserType.Permission.SET_FIREBASE_TOKEN);

            User user = userRepo.getUser(usertoken);

            if(userRepo.getFirebaseTokenCount(user.getId()) > 0) {
                return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "FirebaseToken has been updated!", userRepo.updateFirebaseToken(user.getId(), firebaseToken));
            } else {
                return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "FirebaseToken has been set!", userRepo.setFirebaseToken(user.getId(), firebaseToken));
            }

        } catch (SQLException  | ParseException | NoSuchAlgorithmException | NoPermissionException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "Failed to set FirebaseToken to user", e);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Failed to set FirebaseToken to user!", e);
        }
    }

    public ConfirmationMessage notifyUser(String userToken, int userId) {
        try {
            // Checks permission
            userRepo.getUser(userToken).getUserType().containsPermission(UserType.Permission.USER_NOTIFY);

            String firebaseToken = userRepo.getFirebaseToken(userId);
            if(firebaseToken != "") {

                JsonObject object = new JsonObject();
                object.addProperty("TITLE", "Please send information about assigned calamity");
                object.addProperty("TEXT", "Click here to send some information...");

                FCMHelper fcm = FCMHelper.getInstance();
                fcm.sendData(FCMHelper.TYPE_TO, firebaseToken, object);

                return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                        "User has been notified!", null);
            } else {
                return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Failed to get user device-id, no notification has been send!", null);
            }
        } catch (IOException | NoSuchAlgorithmException | SQLException | NoPermissionException | ParseException e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Failed to notify user!", e);
        }
    }
}
