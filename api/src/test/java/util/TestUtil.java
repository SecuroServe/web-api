package util;

import controllers.LoginController;
import controllers.UserController;
import datarepo.UserRepo;
import datarepo.database.Database;
import datarepo.database.MySqlParser;
import exceptions.WrongUsernameOrPasswordException;
import interfaces.ConfirmationMessage;
import library.User;
import org.junit.Assert;

/**
 * Created by Jandie on 12-Apr-17.
 */
public class TestUtil {
    public static final String USERNAME = "testuser789987";
    public static final String USERNAME2 = "testu87";
    public static final String PASSWORD = "testpwd*()1223";
    public static final String EMAIL = "testuser789987@test123weqr456.nl";
    public static final String CITY = "Amsterdam";
    public static final String TEST_DB_PROPERTIES = "/properties/test_db.properties";
    public static final String TEST_DB_SCRIPT = "/properties/database.sql";

    public static Database cleanAndBuildTestDatabase() throws Exception {
        Database database = new Database(TestUtil.TEST_DB_PROPERTIES);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        MySqlParser mySqlParser = new MySqlParser(database, Database.class.getResourceAsStream(TestUtil.TEST_DB_SCRIPT));
        mySqlParser.execute();

        return database;
    }

    public static void deleteTempUser(User user, Database database) throws Exception {
        LoginController lc = new LoginController(database);
        UserRepo userRepo = new UserRepo(database);

        userRepo.deleteUser(user.getId());

        ConfirmationMessage cm = lc.login(USERNAME, PASSWORD);
        Assert.assertEquals(ConfirmationMessage.StatusType.ERROR, cm.getStatus());
        Assert.assertEquals(true,
                cm.getReturnObject() instanceof WrongUsernameOrPasswordException);
    }

    public static User createTempUser(Database database) throws Exception {
        UserController uc = new UserController(database);
        UserRepo userRepo = new UserRepo(database);

        User user = userRepo.register(1, -1,
                USERNAME, PASSWORD, EMAIL, CITY);

        user = (User) uc.getUser(user.getToken()).getReturnObject();
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(CITY, user.getCity());
        Assert.assertNotEquals(null, user.getUserType());

        return user;
    }

    public static User createTempNoPermissionUser(Database database) throws Exception {
        UserRepo userRepo = new UserRepo(database);

        User user = userRepo.register(3, -1,
                USERNAME2, PASSWORD, EMAIL, CITY);

        return user;
    }
}
