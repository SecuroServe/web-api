package logic;

import datarepo.UserRepo;
import datarepo.database.Database;
import exceptions.NoPermissionException;
import interfaces.ConfirmationMessage;
import library.SocialPost;
import library.UserType;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Jandie on 19-Jun-17.
 */
public class SocialLogic {
    private Database database;
    private UserRepo userRepo;

    public SocialLogic() {
        this.database = new Database();
        userRepo = new UserRepo(database);
    }

    public SocialLogic(Database database) {
        this.database = database;
        userRepo = new UserRepo(database);
    }

    /**
     * Find social posts based on key words.
     *
     * @param token    The authentication token.
     * @param keyWords String with different keywords separated by spaces.
     * @return List of found SocialPost objects.
     */
    public ConfirmationMessage getSocialPosts(String token, String keyWords) {
        try {
            List<SocialPost> socialPosts = new ArrayList<>();

            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.SOCIAL_POST_GET);

            socialPosts.addAll(getTwitterPosts(keyWords));

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Get social posts succesfull!",
                    socialPosts);
        } catch (TwitterException | NoPermissionException | NoSuchAlgorithmException | ParseException | SQLException e) {
            java.util.logging.Logger.getLogger(SocialLogic.class.getName()).log(Level.SEVERE,
                    "Error while getting social posts.", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Get social posts failed!",
                    e);
        }
    }

    private List<SocialPost> getTwitterPosts(String keyWords) throws TwitterException {
        List<SocialPost> socialPosts = new ArrayList<>();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("ijpGiG4xBXIq7UPuQohYBZmsj")
                .setOAuthConsumerSecret("lWp9VYUEmIVl6JOdvXX8054vQTGjIvvt2fGEUA2kYzJGlOezQR")
                .setOAuthAccessToken("385565667-z76oLoqePUL6jO7gOcw8wX3QC5bGv4Cd5w6z3Ry0")
                .setOAuthAccessTokenSecret("FmeDTCwZg13zDQp01gHJdzEYjzcDs18sXIuZwYzEYtcqe");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query = new Query(keyWords);

        QueryResult result = null;

        result = twitter.search(query);

        for (Status status : result.getTweets()) {
            SocialPost socialPost = new SocialPost(status.getUser().getScreenName(), status.getText(), status.getCreatedAt());

            socialPosts.add(socialPost);
        }

        return socialPosts;
    }
}
