package library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Jandie on 13-3-2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

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
     *
     * @param id               The id of the user.
     * @param userType         The type of user.
     * @param assignedCalamity The assigned calamity.
     * @param building         The building where the user is stationed.
     * @param username         The username of the user.
     * @param email            The email of the user.
     * @param city             The city of the user's adress
     * @param token            The authentication token of the user.
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

    public User() {
    }

    /**
     * Gets the id of the user.
     *
     * @return The id of the user.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the type of user.
     *
     * @return The type of user.
     */
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Gets the assigned calamity.
     *
     * @return The assigned calamity.
     */
    public Calamity getAssignedCalamity() {
        return assignedCalamity;
    }

    public void setAssignedCalamity(Calamity assignedCalamity) {
        this.assignedCalamity = assignedCalamity;
    }

    /**
     * Gets the building where the user is stationed.
     *
     * @return The building where the user is stationed.
     */
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the city of the user's adress.
     *
     * @return The city of the users's adress.
     */
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the authentication token of the user.
     *
     * @return The authentication token of the user.
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return this.getUsername() + " (" + this.getCity() + ")";
    }
}
