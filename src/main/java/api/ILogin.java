package api;

import library.User;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Jandie on 13-3-2017.
 */
public interface ILogin {
    String getToken(@RequestParam(value="username") String username,
                    @RequestParam(value="password") String password);
}
