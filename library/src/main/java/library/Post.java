package library;

import java.io.Serializable;

/**
 * Created by Jandie on 2017-06-12.
 */
public class Post implements Serializable {
    /**
     * The id of the post used for identification of the post.
     */
    int id;

    /**
     * The user who posted the post.
     */
    User user;

    /**
     * The text that has been placed in the post.
     */
    String text;

    /**
     * Creates new instance of post and fills all fields.
     *
     * @param id   The id of the post.
     * @param user The user who posted the post.
     * @param text The text in the post,
     */
    public Post(int id, User user, String text) {
        this.id = id;
        this.user = user;
        this.text = text;
    }

    public Post() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
