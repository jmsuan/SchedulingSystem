package lanej.schedulingsystem.model;

public class Customer implements TableSearchable {
    private Integer customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private FirstLevelDivision division;
    public Customer(Integer customerId,
                    String customerName,
                    String address,
                    String postalCode,
                    String phone,
                    FirstLevelDivision division) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
    public int getId() {
        return this.getCustomerId();
    }
    public String getName() {
        return this.getCustomerName();
    }
    public int getCustomerId() {
        return customerId;
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
