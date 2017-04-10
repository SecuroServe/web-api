package securoserve.api.controllers;

import securoserve.api.ILogin;
import securoserve.api.logic.UserLogic;
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

    /**
     * Creates and returns a new token for a user if username and password
     * are correct.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The token of the user.
     */
    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", defaultValue = "") String username,
                        @RequestParam(value = "password", defaultValue = "") String password) {
        return userLogic.login(username, password);
    }
}
