package securoserve.api.datarepo.database;

import org.junit.Test;
import securoserve.api.TestUtil;

/**
 * Created by Jandie on 2017-04-16.
 */
public class MySqlParserTest {
    @Test
    public void testParser() throws Exception {
        Database database = new Database(TestUtil.TEST_DB_PROPERTIES);
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        MySqlParser mySqlParser = new MySqlParser(database, TestUtil.TEST_DB_SCRIPT);

        mySqlParser.Execute();
    }
}