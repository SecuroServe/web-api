package dataRepo;

import utils.HashUtil;

import javax.jws.Oneway;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jandie on 20-3-2017.
 */
public class UserRepo {

    private Database database;

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public UserRepo() {
        database = new Database();
    }

    public String login(String username, String password) throws SQLException,
            ParseException, NoSuchAlgorithmException {

        String salt = getUserSalt(username);
        password = HashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");
        int userId = -1;
        String tokenExpiration = null;
        String token = null;

        String query = "SELECT `id`, `token`, `tokenExpiration` FROM `user` WHERE `username` = ? AND `password` = ?";

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

    private String getToken(int userId, String token, String tokenExpiration) throws SQLException,
            ParseException, NoSuchAlgorithmException {

        if (sdf.parse(tokenExpiration).getTime() > new Date().getTime()) {
            return token;
        }

        return updateToken(userId);
    }

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

    public void register(String username, String password) throws NoSuchAlgorithmException, SQLException {
        String salt = HashUtil.generateSalt();
        password = HashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");

        String query = "INSERT INTO `securoserve`.`User` (`id`, `username`, `password`, `salt`) VALUES (?, ?, ?)";
        List<Object> parameters = new ArrayList<>();

        parameters.add(username);
        parameters.add(password);
        parameters.add(salt);

        database.executeQuery(query, parameters, QueryType.NON_QUERY);
    }
}
