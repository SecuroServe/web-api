package logic;

import api.ConfirmationMessage;
import controllers.LoginController;
import controllers.UserController;
import dataRepo.Database;
import dataRepo.UserRepo;
import enums.StatusType;
import library.User;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jandie on 20-3-2017.
 */
public class UserLogic {
    private Database database;

    public UserLogic() {
        database = new Database();
    }

    public String login(String username, String password) {
        try {
            return new UserRepo(database).login(username, password);
        } catch (SQLException | ParseException | NoSuchAlgorithmException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE,
                    "Login failed!", e);

            return null;
        }
    }

    public ConfirmationMessage addUser(int userTypeId, int calamityAssigneeId, int buildingId,
                                       String username, String password, String email, String city, String token) {

        try {
            User user = new UserRepo(database).register(userTypeId,
                    calamityAssigneeId, buildingId, username, password, email, city);

            return new ConfirmationMessage(StatusType.SUCCES, "User added!", user);

        } catch (NoSuchAlgorithmException | SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE,
                    "User addition failed!", e);
        }

        return new ConfirmationMessage(StatusType.ERROR,
                "User addition failed!", null);
    }

    public ConfirmationMessage deleteUser(String token, int id) {
        try {
            new UserRepo(database).deleteUser(id);

            return new ConfirmationMessage(StatusType.SUCCES, "User deleted!", null);
        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE,
                    "User deletion failed!", e);
        }

        return new ConfirmationMessage(StatusType.ERROR,
                "User deletion failed!", null);
    }

    public User getUser(String token) throws NoSuchAlgorithmException, ParseException {
        try {

            return new UserRepo(database).getUser(token);
        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE,
                    "Failed to retrieve user!", e);

            return null;
        }
    }
}
