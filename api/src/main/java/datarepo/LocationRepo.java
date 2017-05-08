package datarepo;

import datarepo.database.Database;
import library.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages library.Location in the database.
 */
public class LocationRepo {

    private Database database;

    public LocationRepo(Database database) {
        this.database = database;
    }

    /**
     * Add a library.Location into the database
     *
     * @param location the library.Location to insert into the database
     * @return the updated library.Location with ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public void addLocation(Location location) throws SQLException {
        String query = "INSERT INTO `library.Location` " +
                "(`Latitude`, `Longitude`, `Radius`) " +
                "VALUES (?, ?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(location.getLatitude());
        parameters.add(location.getLongitude());
        parameters.add(location.getRadius());

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.INSERT)) {
            if (rs.next()) {
                location.setId(rs.getInt(1));
            }
        }
    }

    /**
     * Update an existing library.Location into the database
     *
     * @param location the updated library.Location with the correct ID
     * @return the updated library.Location
     * @throws SQLException exception when an SQL Error occurs
     */
    public Location updateLocation(Location location) throws SQLException {

        String query = "UPDATE `library.Location` SET `Latitude` = ?, `Longitude` = ?, `Radius` = ? WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(location.getLatitude());
        parameters.add(location.getLongitude());
        parameters.add(location.getRadius());
        parameters.add(location.getId());

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);

        return location;
    }

    /**
     * Delete an existing library.Location from the database
     *
     * @param id the id of the library.Location to delete
     * @throws SQLException exception when an SQL Error occurs
     */
    public void deleteLocation(int id) throws SQLException {
        String query = "DELETE FROM `library.Location` WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Get a library.Location by an ID
     *
     * @param id the ID of a library.Location
     * @return the library.Location with the given ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public Location getLocation(int id) throws SQLException {
        Location location = null;

        String query = "SELECT `ID`, `Latitude`, `Longitude`, `Radius` FROM `library.Location` WHERE `ID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                int locationId = rs.getInt(1);
                double latitude = rs.getLong(2);
                double longitude = rs.getLong(3);
                double radius = rs.getLong(4);

                location = new Location(locationId, latitude, longitude, radius);
            }
        }

        return location;
    }

}
