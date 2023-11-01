package lanej.schedulingsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * A utility class for managing JDBC database connections.
 * Provides methods to open, close, and retrieve a connection to the database.
 * <p>
 * The JDBC connection details, including URL, driver, username, and password,
 * are defined as constants here. The class provides a centralized
 * way to manage the database connection for the scheduling system application.
 * </p>
 *
 * <p>Huge thank you to Malcolm Wabara (course instructor), who provided the
 * webinar and most of the logic that was used here. I referenced his
 * implementation of the JDBC heavily, but made a couple adjustments to fit
 * my application. (One example being that I would rather obtain the
 * Connection through a getter, in case I (or someone else) wants to maintain
 * the login on it at a later date.</p>
 *
 * @author Jonathan Lane
 * @version 1.0
 */
public abstract class JDBC {
    /** JDBC protocol prefix. */
    private static final String protocol = "jdbc";

    /** JDBC protocol prefix. */
    private static final String vendor = ":mysql:";

    /** Location of the database. */
    private static final String location = "//localhost/";

    /** Name of the database. */
    private static final String databaseName = "client_schedule";


    /** Full JDBC URL constructed from the individual components. */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";

    /** The JDBC driver class name for MySQL. */
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    /** Username for the database connection. */
    private static final String userName = "sqlUser";

    /** Password for the database connection. */
    private static final String password = "Passw0rd!";

    /** The active database connection. */
    private static Connection connection;

    /**
     * Opens a new connection to the database using the JDBC parameters
     * defined in this class. If the connection is successful, a message is
     * printed to the console.
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Retrieves the active JDBC database connection.
     *
     * @return The active JDBC {@link Connection}.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes the active JDBC database connection. If the connection is
     * successfully closed, a message is printed to the console.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
