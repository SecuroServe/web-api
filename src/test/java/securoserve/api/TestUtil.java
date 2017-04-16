package securoserve.api;

import org.junit.Assert;
import securoserve.api.controllers.LoginController;
import securoserve.api.controllers.UserController;
import securoserve.api.datarepo.UserRepo;
import securoserve.api.datarepo.database.Database;
import securoserve.api.datarepo.database.MySqlParser;
import securoserve.api.interfaces.ConfirmationMessage;
import securoserve.library.User;
import securoserve.library.exceptions.WrongUsernameOrPasswordException;

/**
 * Created by Jandie on 12-Apr-17.
 */
public class TestUtil {
    public static final String USERNAME = "testuser789987";
    public static final String PASSWORD = "testpwd*()1223";
    public static final String EMAIL = "testuser789987@test123weqr456.nl";
    public static final String CITY = "Amsterdam";
    public static final String TEST_DB_PROPERTIES = "/properties/test_db.properties";
    public static final String TEST_DB_SCRIPT = "./src/main/resources/properties/database.sql";

    public static void cleanAndBuildTestDatabase() throws Exception {
        Database database = new Database(TestUtil.TEST_DB_PROPERTIES);
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        MySqlParser mySqlParser = new MySqlParser(database, TestUtil.TEST_DB_SCRIPT);

        mySqlParser.execute();
    }

    public static void deleteTempUser(User user) throws Exception {
        LoginController lc = new LoginController();
        Database db = new Database();
        UserRepo userRepo = new UserRepo(db);

        userRepo.deleteUser(user.getId());

        ConfirmationMessage cm = lc.login(USERNAME, PASSWORD);
        Assert.assertEquals(ConfirmationMessage.StatusType.ERROR, cm.getStatus());
        Assert.assertEquals(true,
                cm.getReturnObject() instanceof WrongUsernameOrPasswordException);
    }

    public static User createTempUser() throws Exception {
        UserController uc = new UserController();
        Database db = new Database();
        UserRepo userRepo = new UserRepo(db);

        User user = userRepo.register(1, -1,
                USERNAME, PASSWORD, EMAIL, CITY);

        user = (User) uc.getUser(user.getToken()).getReturnObject();
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(CITY, user.getCity());
        Assert.assertNotEquals(null, user.getUserType());

        return user;
    }
}
