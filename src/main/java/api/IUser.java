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
    List<User> allusers(String token);

    /**
     * Gets a user by token.
     *
     * @param userToken The token of the user.
     * @return The user.
     */
    User getUser(String userToken);

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
    ConfirmationMessage addUser(int userTypeId,
                                int buildingId,
                                String username,
                                String password,
                                String email,
                                String city,
                                String token);

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
    ConfirmationMessage updateUser(String token, int id, String username, String password, String email, String city);

    /**
     * Deletes a user.
     *
     * @param token The authentication token.
     * @param id    The id of the user to delete.
     * @return Confirmation message with feedback about the deletion.
     */
    ConfirmationMessage deleteUser(String token,
                                   int id);
}
