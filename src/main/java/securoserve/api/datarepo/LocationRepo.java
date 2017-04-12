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
    public Location addLocation(Location location) throws SQLException {
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
        return location;
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
     * Remove an existing Location from the database
     * @param location the Location to remove
     * @throws SQLException exception when an SQL Error occurs
     */
    public void removeLocation(Location location) throws SQLException {
        int locationId = location.getId();
        String query = "DELETE FROM `securoserve`.`Location` WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(locationId);

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
                float latitude = rs.getFloat(2);
                float longitude = rs.getFloat(3);
                float radius = rs.getFloat(4);

                location = new Location(latitude, longitude, radius);
            }
        }

        return location;
    }

}