package controllers;

import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import interfaces.ISocial;
import logic.SocialLogic;

/**
 * Created by Jandie on 19-Jun-17.
 */
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
    public ConfirmationMessage getSocialPosts(String token, String keyWords) {
        return this.socialLogic.getSocialPosts(token, keyWords);
    }
}
