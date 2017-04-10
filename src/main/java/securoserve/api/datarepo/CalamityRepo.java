package securoserve.api.datarepo;

import securoserve.api.enums.QueryType;
import securoserve.library.Calamity;
import securoserve.library.Location;

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

        try (ResultSet rs = database.executeQuery(query, parameters, QueryType.INSERT)) {
            if (rs.next()) {
                calamity.setId(rs.getInt(1));
            }
        }

        return calamity;
    }


    public Calamity updateCalamity(Calamity calamity) throws SQLException {

        Location location = new LocationRepo(database).updateLocation(calamity.getLocation());

        String query = "UPDATE `securoserve`.`Calamity` SET `LocationID` = ?, `CreatedByUserID` = ?, `isConfirmed` = ?, `isClosed` = ?, `Time` = ?, `Title` = ?, `Message` = ? WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(location.getId());
        parameters.add(calamity.getUser().getId());
        parameters.add(calamity.getConfirmation() ? 1 : 0);
        parameters.add(calamity.getStatus() ? 1 : 0);
        parameters.add(calamity.getDate());
        parameters.add(calamity.getTitle());
        parameters.add(calamity.getMessage());
        parameters.add(calamity.getId());

        database.executeQuery(query, parameters, QueryType.NON_QUERY);

        return calamity;
    }

    public Calamity getCalamity(int id) throws SQLException, ParseException, NoSuchAlgorithmException {
        Calamity calamity = null;
        UserRepo userRepo = new UserRepo(database);
        LocationRepo locationRepo = new LocationRepo(database);

        String query = "SELECT `LocationID`, `CreatedByUserID`, `isConfirmed`, " +
                "`isClosed`, `Time`, `Title`, `Message` " +
                "FROM `securoserve`.`Calamity` " +
                "WHERE `ID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try (ResultSet rs = database.executeQuery(query, parameters, QueryType.QUERY)) {
            int locationId = rs.getInt(1);
            int createdByUserId = rs.getInt(2);
            boolean isConfirmed = rs.getInt(3) == 1;
            boolean isClosed = rs.getInt(4) == 1;
            Date time = rs.getDate(5);
            String title = rs.getString(6);
            String message = rs.getString(7);

            calamity = new Calamity(id, locationRepo.getLocation(locationId), userRepo.getUserById(createdByUserId),
                    isConfirmed, isClosed, time, title, message);
        }

        return calamity;
    }
}
