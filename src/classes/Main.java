package classes;

import database.Database;
import globals.Globals;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main (String[] arguments) {
        Database database = new Database();

        try {
            Globals.registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        database.bindObjectsToRegistry();
    }
}
