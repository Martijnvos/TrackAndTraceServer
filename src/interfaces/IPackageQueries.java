package interfaces;

public interface IPackageQueries {
    Package getPackage(int packageID);
    boolean addPackage(Package packageInstantiation);
    boolean updatePackage(Package packageInstantiation);
    boolean deletePackage(int packageID);
}
