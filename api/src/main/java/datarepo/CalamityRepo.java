package datarepo;

import datarepo.database.Database;
import library.Calamity;
import library.Location;
import library.User;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class defines the database operations for calamities.
 * Created by yannic on 20/03/2017.
 */
public class CalamityRepo {

    private Database database;

    public CalamityRepo(Database database) {
        this.database = database;
    }

    /**
     * Add a new library.Calamity to the database
     *
     * @param calamity the library.Calamity to add into the database
     * @return the updated library.Calamity with the correct ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public void addCalamity(Calamity calamity) throws SQLException {
        new LocationRepo(database).addLocation(calamity.getLocation());

        String query = "INSERT INTO `library.Calamity` " +
                "(`LocationID`, `CreatedByUserID`, `isConfirmed`, `isClosed`, `Time`, `Title`, `Message`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(calamity.getLocation().getId());
        parameters.add(calamity.getUser().getId());
        parameters.add(calamity.getConfirmation() ? 1 : 0);
        parameters.add(calamity.getStatus() ? 1 : 0);
        parameters.add(calamity.getDate());
        parameters.add(calamity.getTitle());
        parameters.add(calamity.getMessage());

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.INSERT)) {
            if (rs.next()) {
                calamity.setId(rs.getInt(1));
            }
        }
    }

    /**
     * Update an existing library.Calamity into the database
     *
     * @param calamity the updated library.Calamity with the correct ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public void updateCalamity(Calamity calamity) throws SQLException {

        Location location = new LocationRepo(database).updateLocation(calamity.getLocation());

        String query = "UPDATE `library.Calamity` SET `LocationID` = ?, `CreatedByUserID` = ?, `isConfirmed` = ?, `isClosed` = ?, `Time` = ?, `Title` = ?, `Message` = ? WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(location.getId());
        parameters.add(calamity.getUser().getId());
        parameters.add(calamity.getConfirmation() ? 1 : 0);
        parameters.add(calamity.getStatus() ? 1 : 0);
        parameters.add(calamity.getDate());
        parameters.add(calamity.getTitle());
        parameters.add(calamity.getMessage());
        parameters.add(calamity.getId());

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Get a library.Calamity by an ID
     *
     * @param id the ID of a calamity
     * @return the library.Calamity with the given ID
     * @throws SQLException             exception when an SQL Error occurs
     * @throws ParseException           exception when an Parse Error occurs
     * @throws NoSuchAlgorithmException exception when Algorithm is not found
     */
    public Calamity getCalamity(int id) throws SQLException, ParseException, NoSuchAlgorithmException {
        Calamity calamity = null;
        UserRepo userRepo = new UserRepo(database);
        LocationRepo locationRepo = new LocationRepo(database);

        String query = "SELECT `LocationID`, `CreatedByUserID`, `isConfirmed`, " +
                "`isClosed`, `Time`, `Title`, `Message` " +
                "FROM `library.Calamity` " +
                "WHERE `ID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                int locationId = rs.getInt(1);
                int createdByUserId = rs.getInt(2);
                boolean isConfirmed = rs.getInt(3) == 1;
                boolean isClosed = rs.getInt(4) == 1;
                Date time = rs.getDate(5);
                String title = rs.getString(6);
                String message = rs.getString(7);

                calamity = new Calamity(id, locationRepo.getLocation(locationId), userRepo.getUserById(createdByUserId),
                        isConfirmed, isClosed, time, title, message);

                for (User user : getCalamityAssignees(calamity.getId())) {
                    calamity.addAssignee(user);
                }
            }
        }

        return calamity;


    }

    /**
     * Delete an existing library.Calamity from the Database
     *
     * @param id the id of a library.Calamity
     * @throws SQLException             exception when an SQL Error occurs
     * @throws ParseException           exception when an Parse Error occurs
     * @throws NoSuchAlgorithmException exception when Algorithm is not found
     */
    public void deleteCalamity(int id) throws ParseException, NoSuchAlgorithmException, SQLException {
        String query = "DELETE FROM library.Location WHERE ID = (SELECT LocationId FROM library.Calamity WHERE ID = ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);

        query = "DELETE FROM library.Calamity WHERE ID = ?";

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    public List<Calamity> allCalamity() throws SQLException, ParseException, NoSuchAlgorithmException {
        List<Calamity> calamities = new ArrayList<>();

        UserRepo userRepo = new UserRepo(database);
        LocationRepo locationRepo = new LocationRepo(database);

        String query = "SELECT `ID`, `LocationID`, `CreatedByUserID`, `isConfirmed`, " +
                "`isClosed`, `Time`, `Title`, `Message` " +
                "FROM `library.Calamity`";

        List<Object> parameters = new ArrayList<>();

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            while (rs.next()) {
                int id = rs.getInt(1);
                int locationId = rs.getInt(2);
                int createdByUserId = rs.getInt(3);
                boolean isConfirmed = rs.getInt(4) == 1;
                boolean isClosed = rs.getInt(5) == 1;
                Date time = rs.getDate(6);
                String title = rs.getString(7);
                String message = rs.getString(8);

                calamities.add(new Calamity(id, locationRepo.getLocation(locationId), userRepo.getUserById(createdByUserId),
                        isConfirmed, isClosed, time, title, message));
            }
        }

        return calamities;
    }

    /**
     * Adds a calamity assignee to the database.
     *
     * @param calamityId The id of the calamity.
     * @param userId     The id of the user (assignee).
     * @throws SQLException Database error.
     */
    public void addCalamityAssignee(int calamityId, int userId) throws SQLException {
        String query = "INSERT INTO `CalamityAssignee` (`CalamityID`, `AssigneeID`) VALUES (?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(calamityId);
        parameters.add(userId);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Gets list of assignees for a calamity.
     *
     * @param calamityId The id of the calamity.
     * @return List of assignees.
     * @throws SQLException Database error.
     */
    public List<User> getCalamityAssignees(int calamityId) throws SQLException {
        List<User> assignees = new ArrayList<>();

        String query = "SELECT u.`ID`, u.`UserTypeID`, u.`BuildingID`, u.`Username`, u.`Email`, u.`City` " +
                "FROM `CalamityAssignee` ca " +
                "INNER JOIN `library.User` u ON ca.AssigneeID = u.ID " +
                "WHERE `CalamityID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(calamityId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            while (rs.next()) {
                int id = rs.getInt(1);
                int userTypeId = rs.getInt(2);
                int buildingId = rs.getInt(3);
                String username = rs.getString(4);
                String email = rs.getString(5);
                String city = rs.getString(6);

                assignees.add(new User(id, null, null, null, username, email, city, null));
            }
        }

        return assignees;
    }

    /**
     * Deletes a assignee from the database.
     *
     * @param calamityId The id of the calamity.
     * @param userId     The id of the user.
     * @throws SQLException Database error.
     */
    public void deleteCalamityAssignee(int calamityId, int userId) throws SQLException {
        String query = "DELETE FROM `CalamityAssignee` WHERE `CalamityID` = ? AND `AssigneeID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(calamityId);
        parameters.add(userId);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }
}
