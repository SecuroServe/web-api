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
     * @param token The authentication token.
     * @return A list of exsisting users.
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    List<User> user (@RequestParam(value = "token") String token);

    /**
     * Gets a single user by id.
     * @param token The authentication token.
     * @param id The id of the user.
     * @return A single user.
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    User user (@RequestParam(value = "token") String token,
               @RequestParam(value = "id") int id);

    /**
     * Adds new user
     * @param userTypeId
     * @param calamityAssigneeId
     * @param builingId
     * @param username
     * @param password
     * @param email
     * @param city
     * @return Feedback of action with user object.
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    ConfirmationMessage user(@RequestParam(value = "userTypeId") int userTypeId,
                             @RequestParam(value = "calamityAssigneeId") int calamityAssigneeId,
                             @RequestParam(value = "builingId") int builingId,
                             @RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "city") String city);

    /**
     * Updates an exsisting user.
     * @param token The authentication token.
     * @param id The id of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email The email of the user's location.
     * @param city The city of the user.
     * @return Confirmation message with feedback about the update.
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.PUT)
    ConfirmationMessage user (@RequestParam(value = "token") String token,
                              @RequestParam(value = "id") int id,
                              @RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password,
                              @RequestParam(value = "email") String email,
                              @RequestParam(value = "city") String city);
}
