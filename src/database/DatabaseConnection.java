package database;

import globals.Globals;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

public class DatabaseConnection {
    private static Connection connection;
    private static Properties connectionProps;

    public Connection getConnection() { return connection; }

    public DatabaseConnection() {
        connectionProps = new Properties();
        connectionProps.put("databaseName", "dbi367789");
        connectionProps.put("user", "dbi367789");
        connectionProps.put("password", "databaseAccess");

        try {
            initConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup connection with database
     * @throws SQLException when VPN is not connected, database can't be reached or credentials are false
     */
    private static void initConnection() throws SQLException {
        connection = DriverManager.getConnection(Globals.dbUrl, connectionProps);
        System.out.println("Successfully connected to database");
    }

    /**
     * Close connection to the database
     * @throws SQLException when database can't be reached
     */
    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("Connection to database closed");
    }
}
