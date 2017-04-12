package securoserve.api.datarepo;

import securoserve.api.enums.QueryType;
import securoserve.library.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationRepo {

    private Database database;

    public LocationRepo(Database database) {
        this.database = database;
    }

    /**
     * Add a Location into the database
     * @param location the Location to insert into the database
     * @return the updated Location with ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public void addLocation(Location location) throws SQLException {
        String query = "INSERT INTO `securoserve`.`Location` " +
                "(`Latitude`, `Longitude`, `Radius`) " +
                "VALUES (?, ?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(location.getLatitude());
        parameters.add(location.getLongitude());
        parameters.add(location.getRadius());

        try (ResultSet rs = database.executeQuery(query, parameters, QueryType.INSERT)) {
            if (rs.next()) {
                location.setId(rs.getInt(1));
            }
        }
    }

    /**
     * Update an existing Location into the database
     * @param location the updated Location with the correct ID
     * @return the updated Location
     * @throws SQLException exception when an SQL Error occurs
     */
    public Location updateLocation(Location location) throws SQLException {

        String query = "UPDATE `securoserve`.`Location` SET `Latitude` = ?, `Longitude` = ?, `Radius` = ? WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(location.getLatitude());
        parameters.add(location.getLongitude());
        parameters.add(location.getRadius());
        parameters.add(location.getId());

        database.executeQuery(query, parameters, QueryType.NON_QUERY);

        return location;
    }

    /**
     * Delete an existing Location from the database
     * @param id the id of the Location to delete
     * @throws SQLException exception when an SQL Error occurs
     */
    public void deleteLocation(int id) throws SQLException {
        String query = "DELETE FROM `securoserve`.`Location` WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        database.executeQuery(query, parameters, QueryType.NON_QUERY);
    }

    /**
     * Get a Location by an ID
     * @param id the ID of a Location
     * @return the Location with the given ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public Location getLocation(int id) throws SQLException {
        Location location = null;

        String query = "SELECT `ID`, `Latitude`, `Longitude`, `Radius` FROM `securoserve`.`Location` WHERE `ID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try (ResultSet rs = database.executeQuery(query, parameters, QueryType.QUERY)) {
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
