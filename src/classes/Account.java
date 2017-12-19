package classes;

public class Account {
    private int ID;
    private String username;
    private String password;
    private boolean isEmployee;
    private String address;

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmployee() {
        return isEmployee;
    }
    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Account(int ID, String username, String password, boolean isEmployee, String address) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.isEmployee = isEmployee;
        this.address = address;
    }

    public Account(String username, String password, boolean isEmployee, String address) {
        this.username = username;
        this.password = password;
        this.isEmployee = isEmployee;
        this.address = address;
    }
}
