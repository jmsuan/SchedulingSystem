package lanej.schedulingsystem.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This class provides data access operations for the Customer class.
 * It contains methods to insert, update, delete, and fetch customers from the database.
 * <p>
 * The operations are designed to interact with a database using JDBC.
 * Errors during operations are communicated through alerts using the ScreenUtility class.
 * </p>
 *
 * @author Jonathan Lane
 * @version 1.0
 */
public abstract class CustomerDAO {
    /**
     * Inserts a new customer into the database.
     *
     * @param customer The customer to be inserted.
     */
    public static void insert(Customer customer) {
        try {
            // Let database handle ID creation
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "INSERT INTO customers (" +
                            "Customer_Name, " +
                            "Address, " +
                            "Postal_Code, " +
                            "Phone, " +
                            "Division_ID)" +
                        "VALUES (?, ?, ?, ?, ?)");
            query.setString(1, customer.getCustomerName());
            query.setString(2, customer.getAddress());
            query.setString(3, customer.getPostalCode());
            query.setString(4, customer.getPhone());
            query.setInt(5, customer.getDivision().getDivisionId());
            query.executeUpdate();
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when adding customer!\nMessage: " + sqlException.getMessage());
        }
    }

    /**
     * Updates the details of an existing customer in the database.
     *
     * @param updatedCustomer The customer with updated information.
     */
    public static void update(Customer updatedCustomer) {
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "UPDATE customers SET " +
                            "Customer_Name = ?, " +
                            "Address = ?, " +
                            "Postal_Code = ?, " +
                            "Phone = ?, " +
                            "Division_ID = ? " +
                        "WHERE Customer_ID = ?");
            query.setString(1, updatedCustomer.getCustomerName());
            query.setString(2, updatedCustomer.getAddress());
            query.setString(3, updatedCustomer.getPostalCode());
            query.setString(4, updatedCustomer.getPhone());
            query.setInt(5, updatedCustomer.getDivision().getDivisionId());
            query.setInt(6, updatedCustomer.getCustomerId());
            query.executeUpdate();
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when updating customer!\nMessage: " + sqlException.getMessage());
        }
    }

    /**
     * Deletes a customer from the database.
     *
     * @param customer The customer to be deleted.
     */
    public static void delete(Customer customer) {
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "DELETE FROM customers WHERE Customer_ID = ?");
            query.setInt(1, customer.getCustomerId());
            query.executeUpdate();
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when deleting customer!\nMessage: " + sqlException.getMessage());
        }
    }

    /**
     * Retrieves all customers from the database.
     * This operation fetches a fresh list each time it's called,
     * as the list of customers may change during program execution.
     *
     * @return An observable list of customers.
     */
    public static ObservableList<Customer> getAllCustomers() {
        // List of Customers may change during program execution,
        // so I will retrieve a fresh list each time this is called.
        ObservableList<Customer> customerList = FXCollections.observableList(new LinkedList<>());
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement("SELECT * FROM customers");
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
                // Find stored division that matches customer
                int divisionId = rs.getInt("Division_ID");
                FirstLevelDivision divisionToSet = null;
                for (FirstLevelDivision division: FirstLevelDivisionDAO.getAllDivisions()) {
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
            // System.out.println("Retrieved all customers from database.");
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when retrieving all Customers!\nMessage: " +
                    sqlException.getMessage());
        }
        return customerList;
    }
}
