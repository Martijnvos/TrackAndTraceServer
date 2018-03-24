import classes.Account;
import database.AccountQueries;
import database.DatabaseConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountQueriesTest {

    private AccountQueries accountQueries;
    private Account exampleAccount;

    @BeforeAll
    public void initialize() {
        DatabaseConnection databaseConnection = new DatabaseConnection("tests", true);
        accountQueries = new AccountQueries(databaseConnection);

        exampleAccount = new Account(1, "test", "test", true,
                "Fontys Stappegoor", "test@trackandtrace.com");
    }

    @Test
    public void getAccount() {
        Account fetchedAccount = accountQueries.getAccount(exampleAccount.getID());
        compareAccounts(exampleAccount, fetchedAccount);
    }

    @Test
    public void getAccountByUsername() {
        String username = "test";
        Account receivedAccount = accountQueries.getAccountByUserName(username);

        compareAccounts(exampleAccount, receivedAccount);
    }

    @Test
    public void addAccount() {
        Account accountToBeAdded = new Account("test2", "test", true,
                "Fontys Stappegoor", "test2@trackandtrace.com");

        boolean succeeded = accountQueries.addAccount(accountToBeAdded);
        if (succeeded) {
            compareAccounts(accountToBeAdded, accountQueries.getAccountByUserName(accountToBeAdded.getUsername()));
        } else {
            fail("There might be a duplicated value for username or email address");
        }
    }

    @Test
    public void updateAccount() {
        Account toUpdate = accountQueries.getAccountByUserName("test2");
        toUpdate.setUsername("updateTest");

        boolean succeeded = accountQueries.updateAccount(toUpdate);

        if (succeeded) {
            compareAccounts(toUpdate, accountQueries.getAccountByUserName("updateTest"));

            // Remove added and updated Account
            accountQueries.deleteAccount(toUpdate.getID());
        } else {
            fail("Failed updating Account");
        }
    }

    @Test
    public void deleteAccount() {
        // Add Account to test database to be able to test the removal afterwards
        Account accountToBeDeleted = new Account("deleteMe", "deleteMe", false,
                "Fontys Stappegoor", "deleteme@trackandtrace.com");
        accountQueries.addAccount(accountToBeDeleted);

        // Get ID of recently inserted Account to be able to test deletion
        Account toDelete = accountQueries.getAccountByUserName("deleteMe");
        boolean succeeded = accountQueries.deleteAccount(toDelete.getID());

        assertEquals(succeeded, true);
    }

    private void compareAccounts(Account account1, Account account2) {
        if (account1.getID() != 0 && account2.getID() != 0) {
            assertEquals(account1.getID(), account2.getID());
        }

        assertEquals(account1.getUsername(), account2.getUsername());
        assertEquals(account1.getPassword(), account2.getPassword());
        assertEquals(account1.getAddress(), account2.getAddress());
        assertEquals(account1.getEmailAddress(), account2.getEmailAddress());
    }
}