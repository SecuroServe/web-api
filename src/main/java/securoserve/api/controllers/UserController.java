package securoserve.api.controllers;

import securoserve.api.ConfirmationMessage;
import securoserve.api.IUser;
import securoserve.library.User;
import securoserve.api.logic.UserLogic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Jandie on 20-3-2017.
 */
@RestController
public class UserController implements IUser {

    private UserLogic userLogic;

    public UserController() {

        this.userLogic = new UserLogic();
    }

    /**
     * Gets a list of exsisting users.
     *
     * @param token The authentication token.
     * @return A list of exsisting users.
     */
    @Override
    @RequestMapping("/allusers")
    public List<User> allusers(@RequestParam(value = "token") String token) {
        return null;
    }

    /**
     * Gets a user by token.
     *
     * @param userToken The token of the user.
     * @return The user.
     */
    @Override
    @RequestMapping("/getuser")
    public User getUser(@RequestParam(value = "usertoken") String userToken) {

        try {
            return userLogic.getUser(userToken);
        } catch (NoSuchAlgorithmException | ParseException e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Inserts a new user to the database.
     *
     * @param userTypeId         The id of the usertype.
     * @param buildingId         The building id.
     * @param username           The username of the user.
     * @param password           The password of the user.
     * @param email              The email of the user.
     * @param city               The city of the user.
     * @param token              The token used to check permission of this action.
     * @return Feedback about the newly created user.
     */
    @Override
    @RequestMapping("/adduser")
    public ConfirmationMessage addUser(@RequestParam(value = "userTypeId") int userTypeId,
                                    @RequestParam(value = "buildingId") int buildingId,
                                    @RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "city") String city,
                                    @RequestParam(value = "token") String token) {

        return userLogic.addUser(userTypeId, buildingId, username, password, email, city, token);
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
    @RequestMapping("/updateuser")
    public ConfirmationMessage updateUser(@RequestParam(value = "token") String token,
                                    @RequestParam(value = "id") int id,
                                    @RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "city") String city) {
        return null;
    }

    /**
     * Deletes a user.
     *
     * @param token The authentication token.
     * @param id    The id of the user to delete.
     * @return Confirmation message with feedback about the deletion.
     */
    @Override
    @RequestMapping("/deleteuser")
    public ConfirmationMessage deleteUser(@RequestParam(value = "token") String token, @RequestParam(value = "id") int id) {
        return userLogic.deleteUser(token, id);
    }
}
