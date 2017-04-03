package api;

import library.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Jandie on 13-3-2017.
 */
public interface IUser {

    /**
     * Gets a list of exsisting users.
     *
     * @param token The authentication token.
     * @return A list of exsisting users.
     */
    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    List<User> allusers(@RequestParam(value = "token") String token);

    /**
     * Gets a user by token.
     *
     * @param userToken The token of the user.
     * @return The user.
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    User getUser(@RequestParam(value = "usertoken") String userToken);

    /**
     * Inserts a new user to the database.
     *
     * @param userTypeId         The id of the usertype.
     * @param calamityAssigneeId the calamityAssigneeId.
     * @param buildingId         The building id.
     * @param username           The username of the user.
     * @param password           The password of the user.
     * @param email              The email of the user.
     * @param city               The city of the user.
     * @param token              The token used to check permission of this action.
     * @return Feedback about the newly created user.
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    ConfirmationMessage addUser(@RequestParam(value = "userTypeId") int userTypeId,
                             @RequestParam(value = "calamityAssigneeId") int calamityAssigneeId,
                             @RequestParam(value = "buildingId") int buildingId,
                             @RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "city") String city,
                             @RequestParam(value = "token") String token);

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
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    ConfirmationMessage updateUser(@RequestParam(value = "token") String token,
                             @RequestParam(value = "id") int id,
                             @RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "city") String city);

    /**
     * Deletes a user.
     *
     * @param token The authentication token.
     * @param id    The id of the user to delete.
     * @return Confirmation message with feedback about the deletion.
     */
    @RequestMapping(value = "/deleteuser", method = RequestMethod.DELETE)
    ConfirmationMessage deleteUser(@RequestParam(value = "token") String token,
                                   @RequestParam(value = "id") int id);
}
