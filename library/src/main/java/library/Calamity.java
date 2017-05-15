package library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Calamity implements Serializable{

    /**
     * The id of the library.Calamity
     */
    private int id;

    /**
     * The library.Location of the library.Calamity
     */
    private Location location;

    /**
     * The library.User who created this library.Calamity
     */
    private User user;

    /**
     * If this library.Calamity is confirmed
     */
    private boolean isConfirmed;

    /**
     * If this library.Calamity is closed
     */
    private boolean isClosed;

    /**
     * Creation date of this library.Calamity
     */
    private Date date;

    /**
     * The title of this library.Calamity
     */
    private String title;

    /**
     * The message of this library.Calamity
     */
    private String message;

    /**
     * Status of the calamity
     */
    private CalamityState state;

    /**
     * The assignees of this calamtiy;
     */
    private List<User> assignees;

    /**
     * The alerts that belong to this calamity.
     */
    private List<Alert> alerts;

    /**
     * Creates a new instance of library.Calamity with all fields.
     *
     * @param location the library.Location of the library.Calamity
     * @param user     the library.User who created this library.Calamity
     * @param title    the Title of this library.Calamity
     * @param message  the Message of this library.Calamity
     */
    public Calamity(int id, Location location, User user, boolean isConfirmed, boolean isClosed, Date date, String title, String message) {
        this.id = id;
        this.location = location;
        this.user = user;
        this.isConfirmed = isConfirmed;
        this.isClosed = isClosed;
        this.date = date;
        this.title = title;
        this.message = message;
        this.state = updateStatus();

        this.alerts = new ArrayList<>();
        this.assignees = new ArrayList<>();
    }

    public Calamity() { }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setState(CalamityState state) {
        this.state = state;
    }

    public void setAssignees(List<User> assignees) {
        this.assignees = assignees;
    }

    /**
     * Get the library.Calamity ID
     *
     * @return the ID of this library.Calamity
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the library.Location of this library.Calamity
     *
     * @return the library.Location of this library.Calamity
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Get the library.User who created this library.Calamity
     *
     * @return the library.User who created this library.Calamity
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Get the creation date of this library.Calamity
     *
     * @return the creation date of this library.Calamity
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Get the status of this library.Calamity
     *
     * @return the status of the library.Calamity
     */
    public boolean getStatus() {
        return this.isClosed;
    }

    /**
     * Check if this library.Calamity is confirmed
     *
     * @return the boolean of the library.Calamity
     */
    public boolean getConfirmation() {
        return this.isConfirmed;
    }

    /**
     * Get the title of this library.Calamity
     *
     * @return the title of this library.Calamity
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the message of this library.Calamity
     *
     * @return the title of this library.Calamity
     */
    public String getMessage() {
        return this.message;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the new library.Location of a library.Calamity
     *
     * @param location The updated location of a library.Calamity
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Change the user who is assigned to this library.Calamity
     *
     * @param user The other user who has to be assigned
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Change the title of this library.Calamity
     *
     * @param title updated title of the library.Calamity
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Change the message of this library.Calamity
     *
     * @param message updated message of the library.Calamity
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Change the value of isConfirmed
     *
     * @param confirmed Bool of confirmed
     */
    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
        state = updateStatus();
    }

    /**
     * Change the value of isClosed
     *
     * @param closed Bool of closed
     */
    public void setClosed(boolean closed) {
        isClosed = closed;
        state = updateStatus();
    }

    public CalamityState getState() {
        return state;
    }

    /**
     * Gives the calamity a status
     */
    private CalamityState updateStatus() {
        if (this.isClosed)
            return CalamityState.CLOSED;

        if (this.isConfirmed && !this.isClosed)
            return CalamityState.OPEN;

        if (!this.isConfirmed && !this.isClosed)
            return CalamityState.PENDING;

        return null;
    }

    /**
     * library.Calamity status enum
     */
    public enum CalamityState {

        PENDING,
        OPEN,
        CLOSED

    }

    /**
     * Adds an assignee to this calamity
     *
     * @param user The assignee.
     */
    public void addAssignee(User user) {
        assignees.add(user);
    }

    /**
     * Get the assignees of this calamity.
     */
    public List<User> getAssignees() {
        return this.assignees;
    }

    /**
     * Get alerts from this calamity.
     * @return Alert List
     */
    public List<Alert> getAlerts() {
        return alerts;
    }

    /**
     * Adds alerts to this calamity.
     * @param alerts Alert List
     */
    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    /**
     * Adds an alert to this calamity.
     * @param alert The alert
     */
    public void addAlert(Alert alert) {
        alerts.add(alert);
    }
}
