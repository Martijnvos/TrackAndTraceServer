import classes.Package;
import database.DatabaseConnection;
import database.PackageQueries;
import enums.ShippingType;
import enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PackageQueriesTest {

    private PackageQueries packageQueries;
    private Package examplePackage;

    @BeforeAll
    public void initialize() {
        DatabaseConnection databaseConnection = new DatabaseConnection("tests", true);
        packageQueries = new PackageQueries(databaseConnection);

        examplePackage = new Package(1, "Test", "Philips", ShippingType.Car, Status.ShippingOrder, "12x12x12cm", 12, "Philips test shipment",
                LocalDate.of(2017, 12, 25), 51.641832, 5.318477);
    }

    @Test
    public void getPackage() {
        comparePackages(examplePackage, packageQueries.getPackage(3));
    }

    @Test
    public void getAllPackagesOfAccount() {
        int accountID = 1;

        // Add a few Packages so it can be tested how reliable the method is
        Package package1 = new Package(1, "Test2", "Philips", ShippingType.Car, Status.ShippingOrder, "12x12x12cm", 12, "Philips test shipment",
                LocalDate.of(2017, 12, 25), 51.641832, 5.318477);
        Package package2 = new Package(1, "Test3", "Philips", ShippingType.Car, Status.ShippingOrder, "12x12x12cm", 12, "Philips test shipment",
                LocalDate.of(2017, 12, 25), 51.641832, 5.318477);
        Package package3 = new Package(1, "Test4", "Philips", ShippingType.Car, Status.ShippingOrder, "12x12x12cm", 12, "Philips test shipment",
                LocalDate.of(2017, 12, 25), 51.641832, 5.318477);

        packageQueries.addPackage(package1);
        packageQueries.addPackage(package2);
        packageQueries.addPackage(package3);

        ArrayList<Package> expectedPackages = new ArrayList<>();
        expectedPackages.add(packageQueries.getPackageByName(examplePackage.getName()));
        expectedPackages.add(packageQueries.getPackageByName(package1.getName()));
        expectedPackages.add(packageQueries.getPackageByName(package2.getName()));
        expectedPackages.add(packageQueries.getPackageByName(package3.getName()));

        ArrayList<Package> packages = packageQueries.getAllPackagesOfAccount(accountID);

        // Check for similarity
        for(int i = 0; i < packages.size(); i++) {
            comparePackages(packages.get(i), expectedPackages.get(i));
        }

        packageQueries.deletePackage(packageQueries.getPackageByName(package1.getName()).getID());
        packageQueries.deletePackage(packageQueries.getPackageByName(package2.getName()).getID());
        packageQueries.deletePackage(packageQueries.getPackageByName(package3.getName()).getID());
    }

    @Test
    public void addPackage() {
        Package packageToAdd = new Package(1, "Test2", "Philips", ShippingType.Car, Status.ShippingOrder,
                "12x12x12cm", 12, "Philips test shipment",
                LocalDate.of(2017, 12, 25), 51.641832, 5.318477);

        boolean succeeded = packageQueries.addPackage(packageToAdd);

        if (!succeeded) {
            fail("Could not add Package. See error for more information");
        }
    }

    @Test
    public void updatePackage() {
        Package packageToUpdate = packageQueries.getPackageByName("Test2");

        packageToUpdate.setName("updateTest");

        boolean succeeded = packageQueries.updatePackage(packageToUpdate);

        if (succeeded) {
            packageQueries.deletePackage(packageToUpdate.getID());
        } else {
            fail("Could not update Package. See error for more information");
        }
    }

    @Test
    public void deletePackage() {
        // Add Package to test database to be able to test the removal afterwards
        Package packageToBeDeleted = new Package(1, "deleteMe", "Philips", ShippingType.Car, Status.ShippingOrder, "12x12x12cm", 12, "Philips test shipment",
                LocalDate.of(2017, 12, 25), 51.641832, 5.318477);
        packageQueries.addPackage(packageToBeDeleted);

        // Get ID of recently inserted Package to be able to test deletion
        Package insertedPackage = packageQueries.getPackageByName(packageToBeDeleted.getName());
        boolean succeeded = packageQueries.deletePackage(insertedPackage.getID());

        assertEquals(succeeded, true);
    }

    private void comparePackages(Package package1, Package package2) {
        if (package1.getID() != 0 && package2.getID() != 0) {
            assertEquals(package1.getID(), package2.getID());
        }

        assertEquals(package1.getAccountID(), package2.getAccountID());
        assertEquals(package1.getName(), package2.getName());
        assertEquals(package1.getFromCompany(), package2.getFromCompany());
        assertEquals(package1.getShippingType(), package2.getShippingType());
        assertEquals(package1.getStatus(), package2.getStatus());
        assertEquals(package1.getSize(), package2.getSize());
        assertEquals(package1.getWeight(), package2.getWeight());
        assertEquals(package1.getContents(), package2.getContents());
        assertEquals(package1.getExpectedDeliveryDate(), package2.getExpectedDeliveryDate());
        assertEquals(package1.getLocationLat(), package2.getLocationLat());
        assertEquals(package1.getLocationLong(), package2.getLocationLong());
    }
}
