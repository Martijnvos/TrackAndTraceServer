package database;

import classes.Account;
import classes.Package;
import enums.ShippingType;
import enums.Status;
import globals.Globals;
import interfaces.IAccountQueries;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;

public class AccountQueries implements IAccountQueries, Serializable {

    private DatabaseConnection connection;

    public AccountQueries(DatabaseConnection connection) {
        this.connection = connection;
    }

    /**
     * Get Account from database which corresponds to the given ID
     * @param accountID the ID of the Account requested
     * @return The Account that corresponds to the given accountID, otherwise null
     */
    @Override
    public Account getAccount(int accountID) {
        String getAccountQuery = "EXEC GetAccount ?";
        PreparedStatement statement;
        ResultSet result;

        try {
            statement = connection.getConnection().prepareStatement(getAccountQuery);
            statement.setInt(1, accountID);
            result = statement.executeQuery();
            if (result.next()){
                Account correspondingAccount = new Account(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getBoolean(4),
                        result.getString(5),
                        result.getString(6));

                return correspondingAccount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add the given Account to the database
     * @param account correct Account instantiation with corresponding information
     * @return true/false depending on success of saving in database
     */
    @Override
    public boolean addAccount(Account account) {
        String addAccountQuery = "EXEC InsertAccount ?,?,?,?,?";
        PreparedStatement statement;

        try {
            statement = connection.getConnection().prepareStatement(addAccountQuery);
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setBoolean(3, account.isEmployee());
            statement.setString(4, account.getAddress());
            statement.setString(5, account.getEmailAddress());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update the given Account in the database
     * @param account correct Account instantiation with updated information
     * @return true/false depending on success of update in database
     */
    @Override
    public boolean updateAccount(Account account) {
        String updateAccountQuery = "EXEC UpdateAccount ?,?,?,?,?,?";
        PreparedStatement statement;

        try {
            statement = connection.getConnection().prepareStatement(updateAccountQuery);
            statement.setInt(1, account.getID());
            statement.setString(2, account.getUsername());
            statement.setString(3, account.getPassword());
            statement.setBoolean(4, account.isEmployee());
            statement.setString(5, account.getAddress());
            statement.setString(6, account.getEmailAddress());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete given Account from database
     * @param accountID ID of Account that has to be deleted
     * @return true/false depending on success of deletion in database
     */
    @Override
    public boolean deleteAccount(int accountID) {
        String updateAccountQuery = "EXEC DeleteAccount ?";
        PreparedStatement statement;

        try {
            statement = connection.getConnection().prepareStatement(updateAccountQuery);
            statement.setInt(1, accountID);

            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Log user in by checking credentials against database
     * @param username username provided
     * @param password corresponding password
     * @return Account object when given right credentials, otherwise null
     */
    @Override
    public Account logIn(String username, String password) {
        String loginQuery = "EXEC LogAccountIn ?,?";
        PreparedStatement statement;
        ResultSet result;

        try {
            statement = connection.getConnection().prepareStatement(loginQuery);
            statement.setString(1, username);
            statement.setString(2, password);
            result = statement.executeQuery();
            if (result.next()) {
                Account correspondingAccount = new Account(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getBoolean(4),
                        result.getString(5),
                        result.getString(6));
                return correspondingAccount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean logOut(Account account) {
        throw new NotImplementedException();
    }

    /**
     * Bind interface of this class to Registry
     */
    public void bindToRegistry() {
        try {
            Remote stub = UnicastRemoteObject.exportObject(this, 0);

            // Bind the remote object's stub in the registry
            Globals.registry.rebind(Globals.accountQueriesBindingName, stub);

            System.out.println("AccountQueries bound to registry");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
