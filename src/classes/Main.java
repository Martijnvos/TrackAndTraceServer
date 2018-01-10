package classes;

import database.Database;
import globals.Globals;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Main {
    public static void main (String[] arguments) {
        addAuthDLL();

        try {
            Globals.registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Database database = new Database();
        database.bindObjectsToRegistry();
    }

    private static void addAuthDLL(){
        String path = System.getProperty("java.library.path");
        path = System.getProperty("user.dir") + "\\dlls;" + path;
        System.setProperty("java.library.path", path);

        try {
            final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
