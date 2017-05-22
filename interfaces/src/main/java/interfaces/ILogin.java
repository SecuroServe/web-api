package interfaces;

/**
 * Created by Jandie on 13-3-2017.
 */
public interface ILogin {

    /**
     * Creates and returns a new token for a user if username and password
     * are correct.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The token of the user.
     */
    ConfirmationMessage login(String username,
                              String password);
}
