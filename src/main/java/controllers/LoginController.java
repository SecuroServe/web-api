package controllers;

import api.ILogin;
import dataRepo.UserRepo;
import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jandie on 13-3-2017.
 */
@RestController
public class LoginController{

    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", defaultValue="") String username,
                        @RequestParam(value = "password", defaultValue="") String password) {
        return new UserRepo().login(username, password);
    }
}
