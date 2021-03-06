package database;

import classes.Package;
import enums.ShippingType;
import enums.Status;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import fontyspublisher.RemotePublisher;
import globals.Globals;
import interfaces.IPackageQueries;
import jdk.nashorn.internal.objects.Global;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PackageQueries implements IPackageQueries, Serializable {

    private DatabaseConnection connection;

    public PackageQueries(DatabaseConnection connection) {
        this.connection = connection;
    }

    /**
     * Get Package corresponding to given ID
     * @param packageID ID of Package wished to be received
     * @return Package instantiation corresponding to given ID
     */
    @Override
    public Package getPackage(int packageID) {
        String getPackageQuery = "EXEC GetPackage ?";
        PreparedStatement statement;
        ResultSet result;

        try {
            statement = connection.getConnection().prepareStatement(getPackageQuery);
            statement.setInt(1, packageID);
            result = statement.executeQuery();
            if (result.next()){
                Package correspondingPackage = new Package(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        ShippingType.valueOf(result.getString(5)),
                        Status.valueOf(result.getString(6)),
                        result.getString(7),
                        result.getInt(8),
                        result.getString(9),
                        result.getDate(10).toLocalDate(),
                        result.getDouble(11),
                        result.getDouble(12));

                return correspondingPackage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Helper method for tests in which the complete Package is fetched by name
     * @param name name of the Package to be fetched
     * @return package instance corresponding to the name or null of not applicable
     */
    public Package getPackageByName(String name) {
        String getPackageQuery = "EXEC GetPackageByName ?";
        PreparedStatement statement;
        ResultSet result;

        try {
            statement = connection.getConnection().prepareStatement(getPackageQuery);
            statement.setString(1, name);
            result = statement.executeQuery();
            if (result.next()){
                Package correspondingPackage = new Package(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        ShippingType.valueOf(result.getString(5)),
                        Status.valueOf(result.getString(6)),
                        result.getString(7),
                        result.getInt(8),
                        result.getString(9),
                        result.getDate(10).toLocalDate(),
                        result.getDouble(11),
                        result.getDouble(12));

                return correspondingPackage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get all packages corresponding to the Account of the given accountID
     * @param accountID ID of the Account from which all packages have to be fetched
     * @return list of packages corresponding to the accountID passed
     */
    @Override
    public ArrayList<Package> getAllPackagesOfAccount(int accountID) {
        ArrayList<Package> allPackagesOfAccount = new ArrayList<>();

        String getAllPackagesOfAccountQuery = "EXEC GetAllPackagesOfAccount ?";
        PreparedStatement statement;
        ResultSet result;

        try {
            statement = connection.getConnection().prepareStatement(getAllPackagesOfAccountQuery);
            statement.setInt(1, accountID);
            result = statement.executeQuery();
            while (result.next()){
                Package correspondingPackage = new Package(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        ShippingType.valueOf(result.getString(5)),
                        Status.valueOf(result.getString(6)),
                        result.getString(7),
                        result.getInt(8),
                        result.getString(9),
                        result.getDate(10).toLocalDate(),
                        result.getDouble(11),
                        result.getDouble(12));

                allPackagesOfAccount.add(correspondingPackage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        if (allPackagesOfAccount.size() != 0) return allPackagesOfAccount;

        return null;
    }

    /**
     * Add Package to database
     * @param packageInstantiation Package instantiation with correct values
     * @return true/false depending on success of saving in database
     */
    @Override
    public boolean addPackage(Package packageInstantiation) {
        String addPackageQuery = "EXEC InsertPackage ?,?,?,?,?,?,?,?,?,?,?";
        PreparedStatement statement;

        try {
            statement = connection.getConnection().prepareStatement(addPackageQuery);
            statement.setInt(1, packageInstantiation.getAccountID());
            statement.setString(2, packageInstantiation.getName());
            statement.setString(3, packageInstantiation.getFromCompany());
            statement.setString(4, packageInstantiation.getShippingType().name());
            statement.setString(5, packageInstantiation.getStatus().name());
            statement.setString(6, packageInstantiation.getSize());
            statement.setInt(7, packageInstantiation.getWeight());
            statement.setString(8, packageInstantiation.getContents());
            statement.setObject(9, packageInstantiation.getExpectedDeliveryDate());
            statement.setDouble(10, packageInstantiation.getLocationLat());
            statement.setDouble(11, packageInstantiation.getLocationLong());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Reset the package updates to propagate a new list of packages for the specific Account
        if (Globals.database != null) {
            Globals.database.unSetPackageLocationUpdates();
            Globals.database.setPackageLocationUpdates(packageInstantiation.getAccountID());
        }

        return true;
    }

    /**
     * Update Package details in database according to given Package
     * @param packageInstantiation Package instantiation with updated values
     * @return true/false depending on success of updating in database
     */
    @Override
    public boolean updatePackage(Package packageInstantiation) {
        String updatePackageQuery = "EXEC UpdatePackage ?,?,?,?,?,?,?,?,?,?,?,?";
        PreparedStatement statement;

        try {
            statement = connection.getConnection().prepareStatement(updatePackageQuery);
            statement.setInt(1, packageInstantiation.getID());
            statement.setInt(2, packageInstantiation.getAccountID());
            statement.setString(3, packageInstantiation.getName());
            statement.setString(4, packageInstantiation.getFromCompany());
            statement.setString(5, packageInstantiation.getShippingType().name());
            statement.setString(6, packageInstantiation.getStatus().name());
            statement.setString(7, packageInstantiation.getSize());
            statement.setInt(8, packageInstantiation.getWeight());
            statement.setString(9, packageInstantiation.getContents());
            statement.setObject(10, packageInstantiation.getExpectedDeliveryDate());
            statement.setDouble(11, packageInstantiation.getLocationLat());
            statement.setDouble(12, packageInstantiation.getLocationLong());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete Package corresponding to given ID
     * @param packageID ID of Package to be removed
     * @return true/false depending on success of deletion from database
     */
    @SuppressWarnings("Duplicates")
    @Override
    public boolean deletePackage(int packageID) {
        String deleteAccountQuery = "EXEC DeletePackage ?";
        PreparedStatement statement;

        try {
            statement = connection.getConnection().prepareStatement(deleteAccountQuery);
            statement.setInt(1, packageID);

            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Bind interface of this class to Registry
     */
    public void bindToRegistry() {
        try {
            Remote stub = UnicastRemoteObject.exportObject(this, 0);

            // Bind the remote object's stub in the registry
            Globals.registry.rebind(Globals.packageQueriesBindingName, stub);

            System.out.println("PackageQueries bound to registry");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
