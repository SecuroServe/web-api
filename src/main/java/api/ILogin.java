package api;

import library.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Jandie on 13-3-2017.
 */
public interface ILogin {

    /**
     * Creates and returns a new token for a user if username and password
     * are correct.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The token of the user.
     */
    String login(@RequestParam(value="username") String username,
                    @RequestParam(value="password") String password);
}
