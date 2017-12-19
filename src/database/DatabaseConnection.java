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

    public static void initConnection() throws SQLException {
        connection = DriverManager.getConnection(Globals.dbUrl, connectionProps);
        System.out.println("Successfully connected to database");
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("DatabaseConnection to database closed");
    }
}
