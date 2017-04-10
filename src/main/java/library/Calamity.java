package library;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Calamity {

    /**
     * The id of the Calamity
     */
    private int id;

    /**
     * The Location of the Calamity
     */
    private Location location;

    /**
     * The User who created this Calamity
     */
    private User user;

    /**
     * If this Calamity is confirmed
     */
    private boolean isConfirmed;

    /**
     * If this Calamity is closed
     */
    private boolean isClosed;

    /**
     * Creation date of this Calamity
     */
    private Date date;

    /**
     * The title of this Calamity
     */
    private String title;

    /**
     * The message of this Calamity
     */
    private String message;

    /**
     * The assignees of this calamtiy;
     */
    private List<User> assignees;

    /**
     * Creates a new instance of Calamity with all fields.
     * @param location the Location of the Calamity
     * @param user the User who created this Calamity
     * @param title the Title of this Calamity
     * @param message the Message of this Calamity
     */
    public Calamity(Location location, User user, boolean isConfirmed, boolean isClosed, Date date, String title, String message){
        this.location = location;
        this.user = user;
        this.isConfirmed = isConfirmed;
        this.isClosed = isClosed;
        this.date = date;
        this.title = title;
        this.message = message;
    }

    /**
     * Get the Calamity ID
     * @return the ID of this Calamity
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the Location of this Calamity
     * @return the Location of this Calamity
     */
    public Location getLocation(){
        return this.location;
    }

    /**
     * Get the User who created this Calamity
     * @return the User who created this Calamity
     */
    public User getUser(){
        return this.user;
    }

    /**
     * Get the creation date of this Calamity
     * @return the creation date of this Calamity
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * Get the status of this Calamity
     * @return the status of the Calamity
     */
    public boolean getStatus(){
        return this.isClosed;
    }

    /**
     * Check if this Calamity is confirmed
     * @return the boolean of the Calamity
     */
    public boolean getConfirmation(){
        return this.isConfirmed;
    }

    /**
     * Get the title of this Calamity
     * @return the title of this Calamity
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Get the message of this Calamity
     * @return the title of this Calamity
     */
    public String getMessage(){
        return this.message;
    }

    public void setId(int id) { this.id = id; }

    /**
     * Set the new Location of a Calamity
     * @param location The updated location of a Calamity
     */
    public void setLocation(Location location){
        this.location = location;
    }

    /**
     * Change the user who is assigned to this Calamity
     * @param user The other user who has to be assigned
     */
    public void setUser(User user){
        this.user = user;
    }

    /**
     * Change the title of this Calamity
     * @param title updated title of the Calamity
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Change the message of this Calamity
     * @param message updated message of the Calamity
     */
    public void setMessage(String message){
        this.message = message;
    }

    /**
     * Adds an assignee to this calamity
     * @param user The assignee.
     */
    public void addAssignee(User user) {
        assignees.add(user);
    }

    /**
     * Get the assignees of this calamity.
     * @param assignees The assignees of this calamity.
     */
    public void setAssignees(List<User> assignees) {
        this.assignees = assignees;
    }
}
