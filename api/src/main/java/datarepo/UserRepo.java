package datarepo;

import com.sun.org.apache.xpath.internal.operations.Bool;
import datarepo.database.Database;
import exceptions.WrongUsernameOrPasswordException;
import library.Calamity;
import library.User;
import library.UserType;
import utils.HashUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Manages user in the database.
 * <p>
 * Created by Jandie on 20-3-2017.
 */
public class UserRepo {

    /**
     * Defines the default dateformat for user.
     */
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * Contains the intance of the database.
     */
    private Database database;

    /**
     * Creates new instance of database.
     */
    public UserRepo(Database database) {
        this.database = database;
    }

    /**
     * Checks username and password then return a valid token.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The valid token.
     * @throws SQLException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    public String login(String username, String password) throws SQLException,
            ParseException, NoSuchAlgorithmException, WrongUsernameOrPasswordException {

        String salt = getUserSalt(username);
        password = HashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");
        String tokenExpiration;
        String token = null;

        String query =
                "SELECT `id`, " +
                        "`token`, " +
                        "`tokenExpiration` " +
                "FROM `User` " +
                "WHERE `username` = ? " +
                "AND `passwordhash` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(username);
        parameters.add(password);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs != null && rs.next()) {
                token = rs.getString(2);
                tokenExpiration = rs.getString(3);
                token = getToken(token, tokenExpiration);
            } else {
                throw new WrongUsernameOrPasswordException("Wrong username or password!");
            }
        }

        return token;
    }

    /**
     * Decides whether the token needs to be renewed or not and handles
     * accordingly.
     *
     * @param token           The token of the user.
     * @param tokenExpiration The expiration date of the user token.
     * @return The valid token.
     * @throws SQLException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    private String getToken(String token, String tokenExpiration)
            throws SQLException, ParseException, NoSuchAlgorithmException {

        if (sdf.parse(tokenExpiration).getTime() > new Date().getTime()) {
            return token;
        }

        return updateToken(token, true);
    }

    /**
     * Updates and generates a new token in the database and renews the expiration date.
     *
     * @param authToken The user id
     * @return The new valid token.
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     */
    private String updateToken(String authToken, boolean regenerate) throws NoSuchAlgorithmException, SQLException {
        String token;

        if (regenerate) {
            token = HashUtil.generateSalt();
        } else {
            token = authToken;
        }

        Calendar date = Calendar.getInstance();
        date.add(Calendar.MINUTE, 15);

        String tokenExpiration = sdf.format(date.getTime());
        String query =
                "UPDATE `User` " +
                "SET `token` = ?, " +
                    "`tokenexpiration` = ? " +
                "WHERE `token` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(token);
        parameters.add(tokenExpiration);
        parameters.add(authToken);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);

        return token;
    }

    /**
     * Fetches the user salt from the database.
     *
     * @param username The username of the user.
     * @return The salt of the user.
     * @throws SQLException
     */
    private String getUserSalt(String username) throws SQLException {
        String query =
                "SELECT `Salt` " +
                "FROM `User` " +
                "WHERE `Username` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(username);

        String salt = null;

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs != null && rs.next()) {
                salt = rs.getString(1);
            }
        }

        return salt;
    }

    /**
     * Inserts a new user to the database.
     *
     * @param userTypeId The id of the usertype.
     * @param buildingId The id of the building.
     * @param username   The username of the user.
     * @param password   The password of the user.
     * @param email      The email of the user.
     * @param city       The city of the user.
     * @return The newly created user.
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     */
    public User register(int userTypeId, int buildingId, String username,
                         String password, String email, String city)
            throws NoSuchAlgorithmException, SQLException, ParseException {
        User user = null;

        Calendar date = Calendar.getInstance();
        date.add(Calendar.MINUTE, 15);

        String salt = HashUtil.generateSalt();
        password = HashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");
        String token = HashUtil.generateSalt();
        String tokenExpiration = sdf.format(date.getTime());
        String query =
                "INSERT INTO `User` " +
                "(`UserTypeID`, " +
                        "`BuildingID`, " +
                        "`Username`, " +
                        "`PasswordHash`, " +
                        "`Salt`, " +
                        "`Email`, " +
                        "`City`, " +
                        "`Token`, " +
                        "`TokenExpiration`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(userTypeId);
        parameters.add(buildingId);
        parameters.add(username);
        parameters.add(password);
        parameters.add(salt);
        parameters.add(email);
        parameters.add(city);
        parameters.add(token);
        parameters.add(tokenExpiration);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.INSERT)) {
            if (rs.next()) {
                int id = rs.getInt(1);

                user = new User(id, null, null, null,
                        username, email, city, token);
            }
        }

        user = getUserById(user.getId());
        user.setToken(token);

        return user;
    }

    /**
     * Deletes a user from the database by id.
     *
     * @param userId The id of the user to delete.
     */
    public void deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM `User` WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(userId);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    public User getUser(String token) throws SQLException, NoSuchAlgorithmException, ParseException {
        User user = null;
        String query =
                "SELECT u.`ID`, " +
                        "u.`BuildingID`, " +
                        "u.`Username`, " +
                        "u.`Email`,  " +
                        "u.`City`, " +
                        "u.`TokenExpiration`, " +
                        "ca.`CalamityID` " +
                "FROM `User` u " +
                "LEFT JOIN `CalamityAssignee` ca ON u.`ID` = ca.`AssigneeID` " +
                "WHERE u.`Token` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(token);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                int id = rs.getInt(1);
                int buildingId = rs.getInt(2);
                String username = rs.getString(3);
                String email = rs.getString(4);
                String city = rs.getString(5);
                if (new Date().after(sdf.parse(rs.getString(6)))) {
                    return null;
                }
                int calamityId = rs.getInt(7);

                UserType userType = new UserTypeRepo(database).getUserTypeOfUser(id);

                user = new User(id, userType, null, null, username, email, city, token);
                if(calamityId > 0) {
                    user.setAssignedCalamity(new CalamityRepo(database).getAssignedCalamity(calamityId));
                }
            }
        }

        if (user != null) {
            updateToken(token, false);
        }

        return user;
    }

    public User getUserById(int id) throws SQLException, NoSuchAlgorithmException, ParseException {
        User user = null;
        String query =
                "SELECT u.`UserTypeID`, " +
                        "u.`BuildingID`, " +
                        "u.`Username`, " +
                        "u.`Email`, " +
                        "u.`City`, " +
                        "u.`Token`, " +
                        "ca.`CalamityID` " +
                "FROM `User` u " +
                "LEFT JOIN `CalamityAssignee` ca ON u.`ID` = ca.`AssigneeID` " +
                "WHERE u.`ID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                int buildingId = rs.getInt(2);
                String username = rs.getString(3);
                String email = rs.getString(4);
                String city = rs.getString(5);
                String token = rs.getString(6);
                int calamityId = rs.getInt(7);

                UserType userType = new UserTypeRepo(database).getUserTypeOfUser(id);

                user = new User(id, userType, null, null, username, email, city, null);
                if (calamityId > 0) {
                    user.setAssignedCalamity(new CalamityRepo(database).getAssignedCalamity(calamityId));
                }
            }
        }

        return user;
    }


    /**
     * this method creates a list of users from entries from the database.
     *
     * @return a List of Users.
     * @throws SQLException
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String query =
                "SELECT u.`ID`, " +
                        "u.`UserTypeID`, " +
                        "u.`BuildingID`, " +
                        "u.`Username`, " +
                        "u.`Email`, " +
                        "u.`City`, " +
                        "u.`Token`, " +
                        "ca.`CalamityID` " +
                "FROM `User` u " +
                "LEFT JOIN `CalamityAssignee` ca ON u.`ID` = ca.`AssigneeID`";

        List<Object> parameters = new ArrayList<>();

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            while (rs.next()) {
                int userID = rs.getInt(1);
                String username = rs.getString(4);
                String email = rs.getString(5);
                String city = rs.getString(6);
                String userToken = rs.getString(7);
                int calamityId = rs.getInt(8);

                UserType userType = new UserTypeRepo(database).getUserTypeOfUser(userID);

                User user = new User(userID, userType, null, null, username, email, city, userToken);
                if(calamityId > 0) {
                    user.setAssignedCalamity(new CalamityRepo(database).getAssignedCalamity(calamityId));
                }

                userList.add(user);
            }
        } catch (ParseException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public Boolean setFirebaseToken(int userID, String firebaseToken) throws SQLException {
        String query =
                "INSERT INTO `FirebaseToken`" +
                        "(`UserID`," +
                        "`FirebaseToken`)" +
                "VALUES (?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(userID);
        parameters.add(firebaseToken);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.INSERT)) {
            if (rs.next()) {
                int id = rs.getInt(1);
                if(id > 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public String getFirebaseToken(int userID) throws SQLException {
        String firebaseToken = "";

        String query =
                "SELECT `ID`, " +
                        "`FirebaseToken`" +
                "FROM `FirebaseToken`" +
                "WHERE `UserID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(userID);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                firebaseToken = rs.getString(2);
            }
        }

        return firebaseToken;
    }

    public int getFirebaseTokenCount(int id) throws SQLException{
        String query =
                "SELECT COUNT(`UserID`) " +
                "FROM `FirebaseToken` " +
                "WHERE `UserID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if(rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    public String updateFirebaseToken(int id, String firebaseToken) throws SQLException {
        String token = firebaseToken;

        String query =
                "UPDATE `FirebaseToken` " +
                "SET `FirebaseToken` = ?" +
                "WHERE `UserID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(firebaseToken);
        parameters.add(id);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);

        return token;
    }
}
