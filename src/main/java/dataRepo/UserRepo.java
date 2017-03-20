package dataRepo;

import library.User;
import utils.HashUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manager user in the database.
 *
 * Created by Jandie on 20-3-2017.
 */
public class UserRepo {

    /**
     * Contains the intance of the database.
     */
    private Database database;

    /**
     * Defines the default dateformat for user.
     */
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * Creates new instance of database.
     */
    public UserRepo() {
        database = new Database();
    }

    /**
     * Checks username and password then return a valid token.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The valid token.
     * @throws SQLException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    public String login(String username, String password) throws SQLException,
            ParseException, NoSuchAlgorithmException {

        String salt = getUserSalt(username);
        password = HashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");
        int userId = -1;
        String tokenExpiration = null;
        String token = null;

        String query = "SELECT `id`, `token`, `tokenExpiration` FROM `user` WHERE `username` = ? AND `passwordhash` = ?";

        List<Object> parameters =  new ArrayList<>();
        parameters.add(username);
        parameters.add(password);

        try (ResultSet rs = database.executeQuery(query, parameters, QueryType.QUERY)) {
            if (rs != null && rs.next()) {
                userId = rs.getInt(0);
                token = rs.getString(1);
                tokenExpiration = rs.getString(2);
                token = getToken(userId, token, tokenExpiration);
            }
        }

        return token;
    }

    /**
     * Decides whether the token needs to be renewed or not and handles
     * accordingly.
     * @param userId The id of the user.
     * @param token The token of the user.
     * @param tokenExpiration The expiration date of the user token.
     * @return The valid token.
     * @throws SQLException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    private String getToken(int userId, String token, String tokenExpiration)
            throws SQLException, ParseException, NoSuchAlgorithmException {

        if (sdf.parse(tokenExpiration).getTime() > new Date().getTime()) {
            return token;
        }

        return updateToken(userId);
    }

    /**
     * Updates and generates a new token in the database and renews the expiration date.
     * @param userId The user id
     * @return The new valid token.
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     */
    private String updateToken(int userId) throws NoSuchAlgorithmException, SQLException {
        String token = HashUtil.generateSalt();
        String tokenExpiration = sdf.format(LocalDateTime.now().plusMinutes(15));
        String query = "UPDATE `user` SET `token` = ?, `tokenexpiration` = ? WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(token);
        parameters.add(tokenExpiration);
        parameters.add(userId);

        database.executeQuery(query, parameters, QueryType.NON_QUERY);

        return token;
    }

    /**
     * Fetches the user salt from the database.
     * @param username The username of the user.
     * @return The salt of the user.
     * @throws SQLException
     */
    private String getUserSalt(String username) throws SQLException {
        String query = "SELECT `salt` FROM `user` WHERE `username` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(username);

        String salt = null;

        try (ResultSet rs = database.executeQuery(query, parameters, QueryType.QUERY)) {
            if (rs != null && rs.next()) {
                salt = rs.getString(0);
            }
        }

        return salt;
    }

    /**
     * Inserts a new user to the database.
     * @param userTypeId The id of the usertype.
     * @param calamityAssigneeId The calamityAssigneeId.
     * @param buildingId The id of the building.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email The email of the user.
     * @param city The city of the user.
     * @return The newly created user.
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     */
    public User register(int userTypeId, int calamityAssigneeId, int buildingId, String username,
                         String password, String email, String city)
            throws NoSuchAlgorithmException, SQLException {

        String salt = HashUtil.generateSalt();
        password = HashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");

        String query = "INSERT INTO `securoserve`.`User` " +
                "(`UserTypeID`, `CalamityAssigneeID`, `BuildingID`, `Username`, " +
                "`PasswordHash`, `Salt`, `Email`, `City`, `Token`, `TokenExpiration`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object> parameters = new ArrayList<>();

        parameters.add(userTypeId);
        parameters.add(calamityAssigneeId);
        parameters.add(buildingId);
        parameters.add(username);
        parameters.add(password);
        parameters.add(salt);
        parameters.add(email);
        parameters.add(city);
        parameters.add(HashUtil.generateSalt());
        parameters.add(sdf.format(LocalDateTime.now().plusMinutes(15)));

        database.executeQuery(query, parameters, QueryType.NON_QUERY);

        return null;
    }
}
