package securoserve.api.datarepo;

import securoserve.library.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jandie on 12-Apr-17.
 */
public class UserTypeRepo {
    private Database database;

    public UserTypeRepo(Database database) {
        this.database = database;
    }

    /**
     * Gets the UserType of user.
     *
     * @param userId The id of the user.
     * @return The UserType.
     * @throws SQLException Database error.
     */
    public UserType getUserTypeOfUser(int userId) throws SQLException {
        UserType userType = null;

        String query = "SELECT ut.Naam, p.`Node` FROM `securoserve`.`Permission` p " +
                "INNER JOIN `securoserve`.`UserTypePermission` utp ON utp.PermissionID = p.ID " +
                "INNER JOIN `securoserve`.`UserType` ut ON ut.ID = utp.UserTypeID " +
                "INNER JOIN `securoserve`.`User` u ON u.UserTypeID = ut.ID " +
                "WHERE u.ID = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(userId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            while (rs.next()) {
                String userTypeName = rs.getString(1);
                UserType.Permission permission = UserType.Permission.valueOf(rs.getString(2));

                if (userType != null) {
                    userType.addPermission(permission);
                } else {
                    userType = new UserType(userTypeName);
                    userType.addPermission(permission);
                }
            }
        }

        return userType;
    }
}
