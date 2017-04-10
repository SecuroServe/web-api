package securoserve.library;

/**
 * Created by Jandie on 13-3-2017.
 */
public class User {

    /**
     * The id of the user.
     */
    private int id;

    /**
     * The type of user.
     */
    private UserType userType;

    /**
     * The assigned calamity.
     */
    private Calamity assignedCalamity;

    /**
     * The building where the user is stationed.
     */
    private Building building;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The city of the user's adress.
     */
    private String city;

    /**
     * The authentication token of the user.
     */
    private String token;

    /**
     * Creates a new instance of user with all fields.
     * @param id The id of the user.
     * @param userType The type of user.
     * @param assignedCalamity The assigned calamity.
     * @param building The building where the user is stationed.
     * @param username The username of the user.
     * @param email The email of the user.
     * @param city The city of the user's adress
     * @param token The authentication token of the user.
     */
    public User(int id, UserType userType, Calamity assignedCalamity,
                Building building, String username, String email,
                String city, String token) {
        this.id = id;
        this.userType = userType;
        this.assignedCalamity = assignedCalamity;
        this.building = building;
        this.username = username;
        this.email = email;
        this.city = city;
        this.token = token;
    }

    /**
     * Gets the id of the user.
     *
     * @return The id of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the type of user.
     *
     * @return The type of user.
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Gets the assigned calamity.
     *
     * @return The assigned calamity.
     */
    public Calamity getAssignedCalamity() {
        return assignedCalamity;
    }

    /**
     * Gets the building where the user is stationed.
     *
     * @return The building where the user is stationed.
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the city of the user's adress.
     *
     * @return The city of the users's adress.
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the authentication token of the user.
     *
     * @return The authentication token of the user.
     */
    public String getToken() {
        return token;
    }
}
