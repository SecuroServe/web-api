package controllers;

import dataRepo.UserRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jandie on 13-3-2017.
 */
@RestController
public class LoginController{

    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", defaultValue="") String username,
                        @RequestParam(value = "password", defaultValue="") String password) {
        try {
            return new UserRepo().login(username, password);
        } catch (SQLException | ParseException | NoSuchAlgorithmException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Login failed!", e);

            return null;
        }
    }
}
