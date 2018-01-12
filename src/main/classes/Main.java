package classes;

import database.Database;
import globals.Globals;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Main {
    public static void main (String[] arguments) {
        System.setProperty("java.rmi.hostname", Globals.RMIHostNameIP);

        try {
            Globals.registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Database database = new Database();
        database.bindObjectsToRegistry();
    }
}
