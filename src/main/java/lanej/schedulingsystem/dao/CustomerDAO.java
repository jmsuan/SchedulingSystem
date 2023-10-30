package lanej.schedulingsystem.dao;

import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class CustomerDAO {

    public boolean insert(Customer customer) {
        boolean successful = false;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "INSERT INTO customers (" +
                            "Customer_ID, " +
                            "Customer_Name, " +
                            "Address, " +
                            "Postal_Code, " +
                            "Phone, " +
                            "Division_ID)" +
                        "VALUES (?, ?, ?, ?, ?, ?)");
            query.setInt(1, customer.getCustomerId());
            query.setString(2, customer.getCustomerName());
            query.setString(3, customer.getAddress());
            query.setString(4, customer.getPostalCode());
            query.setString(5, customer.getPhone());
            query.setInt(6, customer.getDivision().getDivisionId());
            successful = query.executeUpdate() > 0;
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when adding customer!\nMessage: " + sqlException.getMessage());
        }
        return successful;
    }
    public boolean update(Customer updatedCustomer) {
        boolean successful = false;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "UPDATE customers SET " +
                            "Customer_Name = ?, " +
                            "Address = ?, " +
                            "Postal_Code = ?, " +
                            "Phone = ?, " +
                            "Division_ID = ?" +
                        "WHERE Customer_ID = ?");
            query.setString(1, updatedCustomer.getCustomerName());
            query.setString(2, updatedCustomer.getAddress());
            query.setString(3, updatedCustomer.getPostalCode());
            query.setString(4, updatedCustomer.getPhone());
            query.setInt(5, updatedCustomer.getDivision().getDivisionId());
            query.setInt(6, updatedCustomer.getCustomerId());
            successful = query.executeUpdate() > 0;
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when updating customer!\nMessage: " + sqlException.getMessage());
        }
        return successful;
    }
    public boolean delete(Customer customer) {
        boolean successful = false;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "DELETE FROM customers WHERE Customer_ID = ?");
            query.setInt(1, customer.getCustomerId());
            successful = query.executeUpdate() > 0;
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when deleting customer!\nMessage: " + sqlException.getMessage());
        }
        return successful;
    }
    public static List<Customer> getAllCustomers() throws SQLException {
        // List of Customers may change during program execution,
        // so I will retrieve a fresh list each time this is called.
        PreparedStatement query = JDBC.getConnection().prepareStatement("SELECT * FROM customers");
        List<Customer> customerList = null;
        ResultSet rs = query.executeQuery();
        while(rs.next()) {
            // Find stored division that matches customer
            int divisionId = rs.getInt("Division_ID");
            FirstLevelDivision divisionToSet = null;
            for (FirstLevelDivision division:
                 FirstLevelDivisionDAO.getAllDivisions()) {
                if (divisionId == division.getDivisionId()) {
                    divisionToSet = division;
                    break;
                }
            }
            // Set values to new Customer object
            customerList.add(new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    divisionToSet));
        }
        return customerList;
    }
}
