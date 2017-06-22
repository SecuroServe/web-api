package library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jandie on 13-3-2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Alert implements Serializable {
    private int id;
    private User user;
    private Date date;
    private String name;
    private String description;
    private Location location;
    private int calamityId;
    private int urgency;
    private Media media;

    public Alert(int id, Location location, User user, Date date, String name, String description, int urgency, int calamityId) {
        this(id, location, user, date, name, description, urgency);
        this.calamityId = calamityId;
    }

    public Alert(int id, Location location, User user, Date date, String name, String description, int urgency) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.name = name;
        this.description = description;
        this.location = location;
        this.urgency = urgency;

        media = null;
    }

    public Alert() {
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getCalamityId() {
        return calamityId;
    }

    public void setCalamityId(int calamityId) {
        this.calamityId = calamityId;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}