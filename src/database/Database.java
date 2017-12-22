package database;

public class Database {
    private DatabaseConnection databaseConnection;

    private AccountQueries accountQueries;
    private PackageQueries packageQueries;

    public Database() {
        databaseConnection = new DatabaseConnection();

        accountQueries = new AccountQueries(databaseConnection);
        packageQueries = new PackageQueries(databaseConnection);
    }

    public void bindObjectsToRegistry() {
        accountQueries.bindToRegistry();
        packageQueries.bindToRegistry();
    }
}
