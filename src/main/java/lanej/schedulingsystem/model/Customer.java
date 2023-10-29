package lanej.schedulingsystem.model;

public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private FirstLevelDivision division;
    public Customer(int customerID,
                    String customerName,
                    String address,
                    String postalCode,
                    String phone,
                    FirstLevelDivision division) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setDivision(FirstLevelDivision division) {
        this.division = division;
    }
    public int getCustomerID() {
        return customerID;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getAddress() {
        return address;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getPhone() {
        return phone;
    }
    public FirstLevelDivision getDivision() {
        return division;
    }
    @Override
    public String toString() {
        return customerName;
    }
}
