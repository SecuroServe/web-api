package controllers;

import interfaces.ConfirmationMessage;
import interfaces.ITweet;
import logic.TweetLogic;

/**
 * Created by Jandie on 19-Jun-17.
 */
public class TweetController implements ITweet {
    private TweetLogic tweetLogic;

    public TweetController() {
        this.tweetLogic = new TweetLogic();
    }

    /**
     * Find tweets based on key words.
     *
     * @param token    The authentication token.
     * @param keyWords String with different keywords separated by spaces.
     * @return List of found tweet objects.
     */
    @Override
    public ConfirmationMessage getTweets(String token, String keyWords) {
        return this.tweetLogic.getTweets(token, keyWords);
    }
}
