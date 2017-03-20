package dataRepo;

import utils.HashUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jandie on 20-3-2017.
 */
public class UserRepo {

    private Database database;

    public UserRepo() {
        database = new Database();
    }

    public String login(String username, String password){
        return "";
    }

    public void register(String username, String password) {
        try {
            String salt = HashUtil.generateSalt();
            password = HashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");

            String query = "INSERT INTO `securoserve`.`User` (`id`, `username`, `password`, `salt`) VALUES (?, ?, ?)";
            List<Object> parameters = new ArrayList<>();

            parameters.add(username);
            parameters.add(password);
            parameters.add(salt);

            database.executeQuery(query, parameters, QueryType.NON_QUERY);
        } catch (NoSuchAlgorithmException | SQLException e) {
            Logger.getLogger(UserRepo.class.getName()).log(Level.SEVERE,
                    "[UserRepo] Error when trying to insert user" + e.getMessage(), e);
        }
    }

    private String getUserSalt(String username) {
        try {
            String query = "SELECT `salt` FROM `user` WHERE `username` = ?";
            List<Object> parameters = new ArrayList<>();
            parameters.add(username);

            try (ResultSet rs = database.executeQuery(query, parameters, QueryType.QUERY)) {

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }
}
