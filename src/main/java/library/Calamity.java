package library;

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
     * The title of this Calamity
     */
    private String title;

    /**
     * The message of this Calamity
     */
    private String message;

    /**
     * Creates a new instance of Calamity with all fields.
     * @param id the id of the Calamity
     * @param location the Location of the Calamity
     * @param user the User who created this Calamity
     * @param title the Title of this Calamity
     * @param message the Message of this Calamity
     */
    public Calamity(int id, Location location, User user, String title, String message){
        this.id = id;
        this.location = location;
        this.user = user;
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
}
