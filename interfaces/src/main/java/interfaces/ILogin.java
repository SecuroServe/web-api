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

    /**
     * Creates and returns a new user. Also checks for valid username and valid passwords.
     *
     * @param username  The username of the user to register.
     * @param password1 The password of the user.
     * @param password2 The retyped password.
     * @param email     The email of the User.
     * @param city      The city of the user
     * @return A ConfirmationMessage with the new User.
     */
    ConfirmationMessage register(String username, String password1, String password2, String email, String city);
}
