package logic;

import datarepo.UserRepo;
import datarepo.database.Database;
import exceptions.NoPermissionException;
import exceptions.WrongUsernameOrPasswordException;
import interfaces.ConfirmationMessage;
import library.User;
import library.UserType;

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
        //todo authenticate token
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "List<library.User> retrieved!", userRepo.getAllUsers());
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to get list of users from repo", e);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Failed to get list of users", e);
        }
    }
}
