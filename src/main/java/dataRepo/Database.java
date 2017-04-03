package dataRepo;

import enums.QueryType;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which contains all the info and logic of the database.
 *
 * @author Jandie
 */
public final class Database implements AutoCloseable {

    /**
     * String that contains the name of the database properties file.
     */
    private static final String DB_PROPERTIES = "C:\\Users\\Jandie\\IdeaProjects\\web-api\\src\\main\\java\\dataRepo\\database.properties";
    /**
     * Instance of the database.
     */
    private static Database instance;
    /**
     * The connection state of the database.
     */
    private Connection conn;
    /**
     * The URL of the database.
     */
    private String dbUrl;
    /**
     * The username of the database.
     */
    private String dbUser;
    /**
     * The password of the database.
     */
    private String dbPass;

    /**
     * Constructor for the database class which creates a new instance of the
     * Database.
     */
    public Database() {
        initProps(DB_PROPERTIES);
        init(dbUrl, dbUser, dbPass);
    }

    /**
     * Alternative constructor for the database class which creates a new
     * instance of the Database. This constructor is only to be used in
     * unit tests.
     *
     * @param url  The url of the database for jdbc.
     * @param user The database user.
     * @param pass The database password.
     */
    public Database(String url, String user, String pass) {
        init(url, user, pass);
    }

    /**
     * Gets the instance or creates a new one depending whether or not the
     * instance is already instantiated.
     *
     * @return The instance of Database.
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    /**
     * Initialises the Database
     *
     * @param url  The url of the database for jdbc.
     * @param user The database user.
     * @param pass The database password.
     */
    public void init(String url, String user, String pass) {
        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Connection Failed! Cause:", e);
        }
    }

    /**
     * Gets the connection state of the database.
     *
     * @return Connection to see the state of the connection with the database.
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Executes a query to the database.
     *
     * @param query      The content of the query which is sent to the database.
     * @param parameters list of objects which are the parameters for the
     *                   prepared statement.
     * @param queryType  The type of query which has to be executed.
     * @return The result set sent by the Database.
     * @throws SQLException if the query isn't worked out by the database.
     */
    public ResultSet executeQuery(String query, List<Object> parameters, QueryType queryType) throws SQLException {

        List<Object> par = parameters;
        PreparedStatement statement = null;
        int counter = 1;

        if (par == null) {
            par = new ArrayList<>();
        }

        if (queryType == QueryType.INSERT) {
            statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } else {
            statement = conn.prepareStatement(query);
        }

        for (Object p : par) {
            if (statement != null) {
                statement.setObject(counter++, p);
            }
        }

        if (statement != null) {
            switch (queryType) {

                case QUERY:
                    return statement.executeQuery();

                case NON_QUERY:
                    statement.executeUpdate();
                    break;

                case INSERT:
                    statement.executeUpdate();
                    return statement.getGeneratedKeys();
            }
        }

        return null;
    }

    /**
     * Closes the connection with the database.
     *
     * @throws Exception if the connection to the database cant be closed.
     */
    @Override
    public void close() throws Exception {
        conn.close();
    }

    /**
     * Loads the database connection properties.
     *
     * @param properties The file location of the properties file.
     */
    public void initProps(String properties) {
        Properties prop = new Properties();

        try {
            FileInputStream input = new FileInputStream(properties);

            prop.load(input);
        } catch (Exception e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }

        dbUrl = prop.getProperty("dburl");
        dbPass = prop.getProperty("password");
        dbUser = prop.getProperty("user");
    }
}
