package controllers;

import api.ConfirmationMessage;
import api.IUser;
import dataRepo.UserRepo;
import library.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jandie on 20-3-2017.
 */
@RestController
public class UserController implements IUser{
    /**
     * Gets a list of exsisting users.
     *
     * @param token The authentication token.
     * @return A list of exsisting users.
     */
    @Override
    public List<User> user(@RequestParam(value = "token") String token) {
        return null;
    }

    /**
     * Gets a single user by id.
     *
     * @param token The authentication token.
     * @param id    The id of the user.
     * @return A single user.
     */
    @Override
    public User user(@RequestParam(value = "token") String token,
                     @RequestParam(value = "id") int id) {
        return null;
    }


    @Override
    public ConfirmationMessage user(@RequestParam(value = "userTypeId") int userTypeId,
                                    @RequestParam(value = "calamityAssigneeId") int calamityAssigneeId,
                                    @RequestParam(value = "builingId") int builingId,
                                    @RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "city") String city){

        try {
            User user = new UserRepo().register(userTypeId,
                    calamityAssigneeId, builingId, username, password, email, city);

            return new ConfirmationMessage("Succes", "User added!", user);

        } catch (NoSuchAlgorithmException | SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE,
                    "User addition failed!", e);
        }

        return new ConfirmationMessage("Error",
                "User addition failed!", null);
    }

    /**
     * Updates an exsisting user.
     *
     * @param token    The authentication token.
     * @param id       The id of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email    The email of the user's location.
     * @param city     The city of the user.
     * @return Confirmation message with feedback about the update.
     */
    @Override
    public ConfirmationMessage user(@RequestParam(value = "token") String token,
                                    @RequestParam(value = "id") int id,
                                    @RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "city") String city) {
        return null;
    }
}
