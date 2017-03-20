package logic;

import api.ConfirmationMessage;
import controllers.LoginController;
import dataRepo.Database;
import dataRepo.UserRepo;
import org.springframework.web.bind.annotation.RequestParam;

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
}
