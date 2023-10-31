package lanej.schedulingsystem.model;

/**
 * Represents a customer within the scheduling system.
 * <p>
 * This class provides a structured representation of a customer's details,
 * including their name, address, postal code, phone number, and associated
 * first-level division (or region). As an implementation of the TableSearchable
 * interface, customers can be easily displayed and searched for within table views.
 * </p>
 *
 * @author Jonathan Lane
 */
public class Customer implements TableSearchable {
    private Integer customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private FirstLevelDivision division;

    /**
     * Initializes a new Customer with the specified details.
     *
     * @param customerId    Unique identifier for the customer.
     * @param customerName  Name of the customer.
     * @param address       Customer's address.
     * @param postalCode    Customer's postal code.
     * @param phone         Customer's phone number.
     * @param division      The first-level division associated with the customer.
     */
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

    /**
     * Sets the unique ID of the customer.
     *
     * @param customerId Unique identifier for the customer.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Sets the name of the customer.
     *
     * @param customerName Name of the customer.
     */
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
