package controllers;

import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import interfaces.ILogin;
import logic.UserLogic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Manages user login
 * <p>
 * Created by Jandie on 13-3-2017.
 */
@RestController
public class LoginController implements ILogin {
    private UserLogic userLogic;

    public LoginController() {
        this.userLogic = new UserLogic();
    }

    public LoginController(Database database) {
        this.userLogic = new UserLogic(database);
    }

    /**
     * Creates and returns a new token for a user if username and password
     * are correct.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The token of the user.
     */
    @RequestMapping("/login")
    public ConfirmationMessage login(@RequestParam(value = "username", defaultValue = "") String username,
                                     @RequestParam(value = "password", defaultValue = "") String password) {
        return userLogic.login(username, password);
    }

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
    @Override
    @RequestMapping("/register")
    public ConfirmationMessage register(@RequestParam(value = "username", defaultValue = "") String username,
                                        @RequestParam(value = "password1", defaultValue = "") String password1,
                                        @RequestParam(value = "password2", defaultValue = "") String password2,
                                        @RequestParam(value = "email", defaultValue = "") String email,
                                        @RequestParam(value = "city", defaultValue = "") String city) {
        return userLogic.register(username, password1, password2, email, city);
    }
}
