package datarepo;

import datarepo.database.Database;
import exceptions.NoSuchCalamityException;
import library.*;

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
     * Add a new Calamity to the database
     *
     * @param calamity the Calamity to add into the database
     * @return the updated Calamity with the correct ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public void addCalamity(Calamity calamity) throws SQLException {
        new LocationRepo(database).addLocation(calamity.getLocation());

        String query = "INSERT INTO `Calamity` " +
                "(`LocationID`, `CreatedByUserID`, `isConfirmed`, `isClosed`, `Time`, `Title`, `Message`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(calamity.getLocation().getId());
        parameters.add(calamity.getUser() == null ? -1 : calamity.getUser().getId());
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
     * Update an existing Calamity into the database
     *
     * @param calamity the updated Calamity with the correct ID
     * @throws SQLException exception when an SQL Error occurs
     */
    public void updateCalamity(Calamity calamity) throws SQLException {

        Location location = new LocationRepo(database).updateLocation(calamity.getLocation());

        String query = "UPDATE `Calamity` SET `LocationID` = ?, `CreatedByUserID` = ?, `isConfirmed` = ?, `isClosed` = ?, `Time` = ?, `Title` = ?, `Message` = ? WHERE `id` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(location.getId());
        parameters.add(calamity.getUser().getId());
        parameters.add(calamity.getConfirmation() ? 1 : 0);
        parameters.add(calamity.getStatus() ? 1 : 0);
        parameters.add(calamity.getDate());
        parameters.add(calamity.getTitle());
        parameters.add(calamity.getMessage());
        parameters.add(calamity.getId());

        if (calamity.getLocation() != null) {
            new LocationRepo(database).updateLocation(calamity.getLocation());
        }

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Get a Calamity by an ID
     *
     * @param id the ID of a calamity
     * @return the Calamity with the given ID
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
                "FROM `Calamity` " +
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

                calamity.setPosts(getPostsPerCalamity(id));
                calamity.setPlan(getPlanOfCalamity(id));
            }
        }
        return calamity;
    }

    public Calamity getAssignedCalamity(int id) throws SQLException, ParseException, NoSuchAlgorithmException {
        Calamity calamity = null;
        LocationRepo locationRepo = new LocationRepo(database);

        String query =
                "SELECT `LocationID`, " +
                        "`isConfirmed`, " +
                        "`isClosed`, " +
                        "`Time`, " +
                        "`Title`, " +
                        "`Message` " +
                "FROM `Calamity` " +
                "WHERE `ID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                int locationId = rs.getInt(1);
                boolean isConfirmed = rs.getInt(2) == 1;
                boolean isClosed = rs.getInt(3) == 1;
                Date time = rs.getDate(4);
                String title = rs.getString(5);
                String message = rs.getString(6);

                calamity = new Calamity(id, locationRepo.getLocation(locationId), null,
                        isConfirmed, isClosed, time, title, message);
            }
        }
        return calamity;
    }

    /**
     * Delete an existing Calamity from the Database
     *
     * @param id the id of a Calamity
     * @throws SQLException             exception when an SQL Error occurs
     * @throws ParseException           exception when an Parse Error occurs
     * @throws NoSuchAlgorithmException exception when Algorithm is not found
     */
    public void deleteCalamity(int id) throws ParseException, NoSuchAlgorithmException, SQLException {
        String query = "DELETE FROM Location WHERE ID = (SELECT LocationId FROM Calamity WHERE ID = ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);

        query = "DELETE FROM Calamity WHERE ID = ?";

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    public List<Calamity> allCalamity() throws SQLException, ParseException, NoSuchAlgorithmException {
        List<Calamity> calamities = new ArrayList<>();

        UserRepo userRepo = new UserRepo(database);
        LocationRepo locationRepo = new LocationRepo(database);

        String query =
                "SELECT `ID`, " +
                        "`LocationID`, " +
                        "`CreatedByUserID`, " +
                        "`isConfirmed`, " +
                        "`isClosed`, " +
                        "`Time`, " +
                        "`Title`, " +
                        "`Message` " +
                "FROM `Calamity`";

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

                List<User> assignees = this.getCalamityAssignees(id);

                Calamity calamity = new Calamity(id, locationRepo.getLocation(locationId), userRepo.getUserById(createdByUserId),
                        isConfirmed, isClosed, time, title, message);
                calamity.setAssignees(assignees);

                calamity.setPosts(getPostsPerCalamity(id));
                calamity.setPlan(getPlanOfCalamity(id));

                calamities.add(calamity);
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

        String query =
                "SELECT u.`ID`, " +
                        "u.`UserTypeID`, " +
                        "u.`BuildingID`, " +
                        "u.`Username`, " +
                        "u.`Email`, " +
                        "u.`City` " +
                "FROM `CalamityAssignee` ca " +
                "INNER JOIN `User` u ON ca.AssigneeID = u.ID " +
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

    /**
     * Adds a post to a calamity
     *
     * @param user       The user who added the post.
     * @param calamityId The if of the calamity to add the post to.
     * @param text       The text in the post.
     * @return Confirmation message with feedback about the addition.
     */
    public Post addPostToCalamity(User user, int calamityId, String text) throws ParseException, NoSuchAlgorithmException, SQLException,
            NoSuchCalamityException {
        Calamity calamity = this.getCalamity(calamityId);
        Post post = new Post(0, user, text);

        if (calamity == null) {
            throw new NoSuchCalamityException("Calamity does not exsist.");
        }

        post = addPost(post);

        String query = "INSERT INTO `CalamityPost` (`CalamityID`, `PostID`) VALUES (?, ?);";

        List<Object> parameters = new ArrayList<>();
        parameters.add(calamityId);
        parameters.add(post.getId());

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);

        return post;
    }

    private Post addPost(Post post) throws SQLException {
        String query = "INSERT INTO `Post` (`UserID`, `Text`) VALUES (?, ?);";

        List<Object> parameters = new ArrayList<>();
        parameters.add(post.getUser().getId());
        parameters.add(post.getText());

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.INSERT)) {
            if (rs.next()) {
                post.setId(rs.getInt(1));
            }
        }

        return post;
    }

    private List<Post> getPostsPerCalamity(int calamityId) throws SQLException, ParseException, NoSuchAlgorithmException {
        List<Post> posts = new ArrayList<>();

        String query = "SELECT p.ID, p.UserID, p.Text FROM Post p INNER JOIN CalamityPost cp ON p.ID = cp.PostID WHERE cp.CalamityID = ?;";

        List<Object> parameters = new ArrayList<>();
        parameters.add(calamityId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            while (rs.next()) {
                int id = rs.getInt(1);
                int userId = rs.getInt(2);
                String text = rs.getString(3);

                User user = new UserRepo(database).getUserById(id);

                posts.add(new Post(id, user, text));
            }
        }

        return posts;
    }

    /**
     * Adds a plan to a calamity
     *
     * @param calamityId The id of the calamity to add the plan to.
     * @param plan       The plan to add.
     * @return The new plan.
     */
    public Plan addPlan(int calamityId, Plan plan) throws SQLException, ParseException, NoSuchAlgorithmException,
            NoSuchCalamityException {
        Calamity calamity = this.getCalamity(calamityId);

        if (calamity == null) {
            throw new NoSuchCalamityException("Calamity does not exsist.");
        }

        String query = "INSERT INTO `Plan` (`CalamityID`, `Description`) VALUES (?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(calamityId);
        parameters.add(plan.getDescription());

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.INSERT)) {
            if (rs.next()) {
                plan.setId(rs.getInt(1));
            }
        }

        return plan;
    }

    private Plan getPlanOfCalamity(int calamityId) throws SQLException {
        List<Plan> plans = new ArrayList<>();

        String query = "SELECT `ID`, `Description` FROM `Plan` WHERE `CalamityID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(calamityId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String description = rs.getString(2);

                Plan plan = new Plan(id, description);

                plans.add(plan);
            }
        }

        return plans.size() > 0 ? plans.get(plans.size() - 1) : null;
    }
}
