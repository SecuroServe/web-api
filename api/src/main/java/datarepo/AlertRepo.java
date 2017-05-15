package datarepo;

import datarepo.database.Database;
import library.Alert;
import library.Location;

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
public class AlertRepo {

    private Database database;

    public AlertRepo(Database database) {
        this.database = database;
    }

    /**
     * Add a new Alert to the database
     *
     * @param Alert the Alert to add into the database
     * @return the updated Alert with the correct ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public void addAlert(Alert Alert) throws SQLException {
        new LocationRepo(database).addLocation(Alert.getLocation());

        String query = "INSERT INTO `Alert` " +
                "(`LocationID`, `CreatedByUserID`, `CalamityID`, `Time`, `Title`, `Description`, `Urgency`) " +
                "VALUES (?, ?, -1, ?, ?, ?, ?)";

        List<Object> parameters = new ArrayList<>();

        parameters.add(Alert.getLocation().getId());
        parameters.add(Alert.getUser().getId());
        parameters.add(Alert.getDate());
        parameters.add(Alert.getName());
        parameters.add(Alert.getDescription());
        parameters.add(Alert.getUrgency());

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.INSERT)) {

            if (rs != null && rs.next()) {
                Alert.setId(rs.getInt(1));
            }
        }
    }

    /**
     * Update an existing Alert into the database
     *
     * @param Alert the updated Alert with the correct ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public void updateAlert(Alert Alert) throws SQLException {

        Location location = new LocationRepo(database).updateLocation(Alert.getLocation());

        String query = "UPDATE `Alert` " +
                "SET `LocationID` = ?," +
                "`CreatedByUserID` = ?," +
                "`CalamityID` = ?," +
                "`Time` = ?," +
                "`Title` = ?," +
                "`Description` = ?, " +
                "`Urgency` = ? " +
                "WHERE `ID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(location.getId());
        parameters.add(Alert.getUser().getId());
        parameters.add(Alert.getCalamityId());
        parameters.add(Alert.getDate());
        parameters.add(Alert.getName());
        parameters.add(Alert.getDescription());
        parameters.add(Alert.getUrgency());
        parameters.add(Alert.getId());

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Get a Alert by an ID
     *
     * @param id the ID of a Alert
     * @return the Alert with the given ID
     * @throws SQLException             exception when an SQL Error occurs
     * @throws ParseException           exception when an Parse Error occurs
     * @throws NoSuchAlgorithmException exception when Algorithm is not found
     */
    public Alert getAlert(int id) throws SQLException, ParseException, NoSuchAlgorithmException {
        Alert Alert = null;
        UserRepo userRepo = new UserRepo(database);
        LocationRepo locationRepo = new LocationRepo(database);

        String query = "SELECT `LocationID`, `CreatedByUserID`, `CalamityID`, `Time`, `Title`, `Description`, `Urgency` " +
                "FROM `Alert` " +
                "WHERE `ID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs != null && rs.next()) {
                int locationId = rs.getInt(1);
                int createdByUserId = rs.getInt(2);
                int calamityId = rs.getInt(3);
                Date time = rs.getDate(4);
                String name = rs.getString(5);
                String description = rs.getString(6);
                int urgency = rs.getInt(7);

                Alert = new Alert(id, locationRepo.getLocation(locationId), userRepo.getUserById(createdByUserId),
                        time, name, description, urgency, calamityId);
            }
        }

        return Alert;


    }

    /**
     * Returns a list of all alerts.
     *
     * @param unassigned Determines whether to retrieve unassigned (not belonging to a calamity) alerts.
     * @return The list of alerts.
     * @throws SQLException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    public List<Alert> allAlert(boolean unassigned) throws SQLException, ParseException, NoSuchAlgorithmException {
        List<Alert> calamities = new ArrayList<>();

        UserRepo userRepo = new UserRepo(database);
        LocationRepo locationRepo = new LocationRepo(database);

        String query;

        if (unassigned) {
            query = "SELECT `ID`, `LocationID`, `CreatedByUserID`, `Time`, `Title`, `Description`, `Urgency` " +
                    "FROM `Alert` WHERE `CalamityID` < 0";
        } else {
            query = "SELECT `ID`, `LocationID`, `CreatedByUserID`, `Time`, `Title`, `Description`, `Urgency` " +
                    "FROM `Alert`";
        }

        List<Object> parameters = new ArrayList<>();

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            while (rs != null && rs.next()) {

                int id = rs.getInt(1);
                int locationId = rs.getInt(2);
                int createdByUserId = rs.getInt(3);
                Date time = rs.getDate(4);
                String title = rs.getString(5);
                String description = rs.getString(6);
                int urgency = rs.getInt(7);

                calamities.add(new Alert(
                        id,
                        locationRepo.getLocation(locationId),
                        userRepo.getUserById(createdByUserId),
                        time,
                        title,
                        description,
                        urgency
                ));
            }
        }

        return calamities;
    }

    /**
     * Delete an existing Alert from the Database
     *
     * @param id the id of a Alert
     * @throws SQLException             exception when an SQL Error occurs
     * @throws ParseException           exception when an Parse Error occurs
     * @throws NoSuchAlgorithmException exception when Algorithm is not found
     */
    public void deleteAlert(int id) throws ParseException, NoSuchAlgorithmException, SQLException {
        String query = "DELETE FROM Location WHERE ID = (SELECT LocationId FROM Alert WHERE ID = ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);

        query = "DELETE FROM Alert WHERE ID = ?";

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }
}
