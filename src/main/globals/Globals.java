package globals;

import classes.Account;
import database.Database;

import java.rmi.registry.Registry;

public class Globals {
    // Database class
    public static Database database;

    // Database
    public static final String dbUrlFontys = "jdbc:sqlserver://mssql.fhict.local";
    public static final String dbUrlLocal = "jdbc:sqlserver://127.0.0.1";

    // Registry
    public static Registry registry;
    public static final String RMIHostNameIP = "127.0.0.1";
    public static final String remotePublisherPackageChangesString = "packageChanges";

    // Registry binding names
    public static final String accountQueriesBindingName = "accountQueriesBinding";
    public static final String packageQueriesBindingName = "packageQueriesBinding";
    public static final String remotePublisherPackageBindingName = "packageRemotePublisherBinding";
    public static final String packageUpdateBindingName = "packageUpdateBinding";

    // Account
    public static Account loggedInAccount;
}
