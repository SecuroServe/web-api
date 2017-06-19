package logic;

import interfaces.ConfirmationMessage;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Jandie on 19-Jun-17.
 */
public class SocialLogic {
    /**
     * Find social posts based on key words.
     *
     * @param token    The authentication token.
     * @param keyWords String with different keywords separated by spaces.
     * @return List of found SocialPost objects.
     */
    public ConfirmationMessage getSocialPosts(String token, String keyWords) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("ijpGiG4xBXIq7UPuQohYBZmsj")
                .setOAuthConsumerSecret("lWp9VYUEmIVl6JOdvXX8054vQTGjIvvt2fGEUA2kYzJGlOezQR")
                .setOAuthAccessToken("385565667-z76oLoqePUL6jO7gOcw8wX3QC5bGv4Cd5w6z3Ry0")
                .setOAuthAccessTokenSecret("FmeDTCwZg13zDQp01gHJdzEYjzcDs18sXIuZwYzEYtcqe");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query = new Query("tilburg ongeluk");
        try {
            QueryResult result = twitter.search(query);
            for(Status status : result.getTweets()){
                System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return null;
    }
}
