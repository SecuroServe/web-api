package dataRepo;

import enums.QueryType;
import library.Calamity;
import library.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the database operations for calamities.
 * Created by yannic on 20/03/2017.
 */
public class CalamityRepo {

    //todo create querries and implement this.
    private Database database;

    public CalamityRepo(Database database) {
        this.database = database;
    }

    public Calamity addCalamity(Calamity calamity) throws SQLException {
        Location location = new LocationRepo(database).addLocation(calamity.getLocation());

        String query = "INSERT INTO `securoserve`.`Calamity` " +
                "(`LocationID`, `CreatedByUserID`, `isConfirmed`, `isClosed`, `Time`, `Title`, `Message`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(location.getId());
        parameters.add(calamity.getUser().getId());
        parameters.add(calamity.getConfirmation() ? 1 : 0);
        parameters.add(calamity.getStatus() ? 1 : 0);
        parameters.add(calamity.getDate());
        parameters.add(calamity.getTitle());
        parameters.add(calamity.getMessage());

        try (ResultSet rs = database.executeQuery(query, parameters, QueryType.INSERT)){
            if (rs.next()) {
                calamity.setId(rs.getInt(1));
            }
        }

        return calamity;
    }


    public Calamity updateCalamity(int id, String name, String description, Location location) {
        Calamity calamity = null;

        return calamity;
    }

    public Calamity getCalamity(int id) {
        Calamity calamity = null;

        return calamity;
    }
}
