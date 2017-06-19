package library;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jandie on 19-Jun-17.
 */
public class SocialPost implements Serializable {
    String username;
    String tweetMessage;
    Date createdDate;

    public SocialPost(String username, String tweetMessage, Date createdDate) {
        this.username = username;
        this.tweetMessage = tweetMessage;
        this.createdDate = createdDate;
    }

    public SocialPost() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
