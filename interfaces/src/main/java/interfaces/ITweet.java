package interfaces;

/**
 * Created by Jandie on 19-Jun-17.
 */
public interface ITweet {
    /**
     * Find tweets based on key words.
     *
     * @param token    The authentication token.
     * @param keyWords String with different keywords separated by spaces.
     * @return List of found tweet objects.
     */
    ConfirmationMessage getTweets(String token, String keyWords);
}
