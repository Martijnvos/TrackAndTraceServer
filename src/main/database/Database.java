package database;

import classes.Package;
import fontyspublisher.RemotePublisher;
import globals.Globals;
import interfaces.IPackageUpdates;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class Database implements IPackageUpdates, Serializable {
    private DatabaseConnection databaseConnection;

    private Timer timer;
    private AccountQueries accountQueries;
    private PackageQueries packageQueries;
    private RemotePublisher remotePublisher;

    public RemotePublisher getRemotePublisher() {
        return remotePublisher;
    }

    public Database() {
        databaseConnection = new DatabaseConnection();

        timer = new Timer();
        accountQueries = new AccountQueries(databaseConnection);
        packageQueries = new PackageQueries(databaseConnection);

        try {
            remotePublisher = new RemotePublisher();
            Globals.registry.rebind(Globals.remotePublisherPackageBindingName, remotePublisher);
            remotePublisher.registerProperty(Globals.remotePublisherPackageChangesString);

            bindToRegistry();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Globals.database = this;
    }

    public void bindObjectsToRegistry() {
        accountQueries.bindToRegistry();
        packageQueries.bindToRegistry();
    }

    public void setPackageLocationUpdates(int accountID) {
        Random randomDouble = new Random();
        timer = new Timer();

        ArrayList<Package> packages = packageQueries.getAllPackagesOfAccount(accountID);

        // Run every 5 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int index = (int) (Math.random() * packages.size());
                Package selectedPackage = packages.get(index);

                double latitude = selectedPackage.getLocationLat();
                double longitude = selectedPackage.getLocationLong();
                double randomDoublePointLatitude = randomDouble.nextDouble();
                double randomDoublePointLongitude = randomDouble.nextDouble();

                BigDecimal bigDecimalLatitude = new BigDecimal(latitude + randomDoublePointLatitude)
                        .setScale(6, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal bigDecimalLongitude = new BigDecimal(longitude + randomDoublePointLongitude)
                        .setScale(6, BigDecimal.ROUND_HALF_EVEN);

                selectedPackage.setLocationLat(bigDecimalLatitude.doubleValue());
                selectedPackage.setLocationLong(bigDecimalLongitude.doubleValue());

                packageQueries.updatePackage(selectedPackage);

                publishUpdate(selectedPackage);

            }
        }, 0, 5*1000);
    }

    public void unSetPackageLocationUpdates() {
        timer.cancel();
    }

    public void publishUpdate(Package newPackage) {
        if (Globals.database != null) {
            try {
                RemotePublisher remotePublisher = Globals.database.getRemotePublisher();
                remotePublisher.inform(Globals.remotePublisherPackageChangesString, null, newPackage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Bind interface of this class to Registry
     */
    public void bindToRegistry() {
        try {
            Remote stub = UnicastRemoteObject.exportObject(this, 0);

            // Bind the remote object's stub in the registry
            Globals.registry.rebind(Globals.packageUpdateBindingName, stub);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
