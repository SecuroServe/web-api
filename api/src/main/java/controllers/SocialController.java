package controllers;

import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import interfaces.ISocial;
import logic.SocialLogic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jandie on 19-Jun-17.
 */
@RestController
public class SocialController implements ISocial {
    private SocialLogic socialLogic;

    public SocialController() {
        this.socialLogic = new SocialLogic();
    }

    public SocialController(Database database) {
        this.socialLogic = new SocialLogic(database);
    }

    /**
     * Find social posts based on key words.
     *
     * @param token    The authentication token.
     * @param keyWords String with different keywords separated by spaces.
     * @return List of found tweet objects.
     */
    @Override
    @RequestMapping("/getsocialposts")
    public ConfirmationMessage getSocialPosts(@RequestParam(value = "token") String token,
                                              @RequestParam(value = "keyWords") String keyWords) {
        return this.socialLogic.getSocialPosts(token, keyWords);
    }
}
