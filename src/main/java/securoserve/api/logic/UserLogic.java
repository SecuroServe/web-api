package securoserve.api.logic;

import securoserve.api.datarepo.Database;
import securoserve.api.datarepo.UserRepo;
import securoserve.api.interfaces.ConfirmationMessage;
import securoserve.library.User;
import securoserve.library.UserType;
import securoserve.library.exceptions.NoPermissionException;
import securoserve.library.exceptions.WrongUsernameOrPasswordException;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the user logic.
 *
 * Created by Jandie on 20-3-2017.
 */
public class UserLogic {
    private Database database;
    private UserRepo userRepo;

    public UserLogic() {
        database = new Database();
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
                    "User added!", user);

        } catch (NoSuchAlgorithmException | SQLException | ParseException | NoPermissionException e) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE,
                    "User addition failed!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Addition of user failed!", e);
        }
    }

    public ConfirmationMessage deleteUser(String token, int id) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.USER_DELETE);

            userRepo.deleteUser(id);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "User deleted!", null);
        } catch (SQLException | ParseException | NoSuchAlgorithmException | NoPermissionException e) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE,
                    "User deletion failed!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "User deletion failed!", e);
        }
    }

    public ConfirmationMessage getUser(String token) throws NoSuchAlgorithmException, ParseException {
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "User retrieved!", userRepo.getUser(token));
        } catch (SQLException e) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE,
                    "Failed to retrieve user!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Failed to retrieve user!", e);
        }
    }
}
