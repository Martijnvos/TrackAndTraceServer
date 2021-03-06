package database;

import globals.Globals;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

public class DatabaseConnection {
    private static Connection connection;
    private static Properties connectionProps;

    public Connection getConnection() { return connection; }

    public DatabaseConnection() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Localhost IP Address: " + localhost.getHostAddress());

            setLocalConnectionProperties();

            initConnection();
        } catch (UnknownHostException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor used for testing purposes
     * @param databaseName the name of the test database to connect with
     * @param forTestDatabase boolean to make sure you understand your connecting with the test database
     */
    public DatabaseConnection(String databaseName, boolean forTestDatabase) {
        if (!forTestDatabase) return;

        connectionProps = new Properties();
        connectionProps.put("databaseName", databaseName);
        connectionProps.put("integratedSecurity", "true");

        try {
            initConnection();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connection properties for the MSSQL database found on the Fontys servers (requires VPN to vdi.fhict.nl)
     */
    private void setFontysConnectionProperties() {
        connectionProps = new Properties();
        connectionProps.put("databaseName", "dbi367789");
        connectionProps.put("user", "dbi367789");
        connectionProps.put("password", "databaseAccess");
    }

    /**
     * Connection properties for the local MSSQL database
     */
    private void setLocalConnectionProperties() {
        connectionProps = new Properties();
        connectionProps.put("databaseName", "master");
        connectionProps.put("integratedSecurity", "true");
    }

    /**
     * Setup connection with database
     * @throws SQLException when VPN is not connected, database can't be reached or credentials are false
     */
    private static void initConnection() throws SQLException {
        connection = DriverManager.getConnection(Globals.dbUrlLocal, connectionProps);
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
