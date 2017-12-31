package globals;

import classes.Account;

import java.rmi.registry.Registry;

public class Globals {
    // Database
    public static final String dbUrlFontys = "jdbc:sqlserver://mssql.fhict.local";
    public static final String dbUrlLocal = "jdbc:sqlserver://DESKTOP-35IBOF2";

    // Registry
    public static Registry registry;

    // Registry binding names
    public static final String accountQueriesBindingName = "accountQueriesBinding";
    public static final String packageQueriesBindingName = "packageQueriesBinding";

    // Account
    public static Account loggedInAccount;
}
