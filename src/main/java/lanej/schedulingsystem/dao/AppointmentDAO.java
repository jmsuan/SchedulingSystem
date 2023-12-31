package lanej.schedulingsystem.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Appointment;
import lanej.schedulingsystem.model.Contact;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This class provides data access operations for the Appointment class.
 * It contains methods to insert, update, delete, and fetch appointments based on various criteria.
 * <p>
 * The operations are designed to interact with a database using JDBC.
 * Errors during operations are communicated through alerts using the ScreenUtility class.
 * </p>
 *
 * @author Jonathan Lane
 * @version 1.0
 */
public abstract class AppointmentDAO {

    /**
     * Inserts a new appointment into the database.
     *
     * @param appointment The appointment to insert.
     * @return true if the insertion was successful, otherwise false.
     */
    public static boolean insert(Appointment appointment) {
        boolean successful = false;
        try {
            // Let database handle id creation
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "INSERT INTO appointments (" +
                            "Title, " +
                            "Description, " +
                            "Location, " +
                            "Type, " +
                            "Start, " +
                            "End, " +
                            "Customer_ID, " +
                            "User_ID, " +
                            "Contact_ID)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            query.setString(1, appointment.getTitle());
            query.setString(2, appointment.getDescription());
            query.setString(3, appointment.getLocation());
            query.setString(4, appointment.getType());
            query.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            query.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            query.setInt(7, appointment.getCustomer().getCustomerId());
            query.setInt(8, appointment.getUser().userId());
            query.setInt(9, appointment.getContact().getContactId());
            successful = query.executeUpdate() > 0;
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when trying to insert appointment!\nMessage: " +
                    sqlException.getMessage());
        }
        return successful;
    }

    /**
     * Updates an existing appointment in the database.
     *
     * @param appointmentToUpdate The appointment with updated details.
     * @return true if the update was successful, otherwise false.
     */
    public static boolean update(Appointment appointmentToUpdate) {
        boolean successful = false;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "UPDATE appointments SET " +
                            "Title = ?, " +
                            "Description = ?, " +
                            "Location = ?, " +
                            "Type = ?, " +
                            "Start = ?, " +
                            "End = ?, " +
                            "Customer_ID = ?, " +
                            "User_ID = ?, " +
                            "Contact_ID = ? " +
                        "WHERE Appointment_ID = ?");
            query.setString(1, appointmentToUpdate.getTitle());
            query.setString(2, appointmentToUpdate.getDescription());
            query.setString(3, appointmentToUpdate.getLocation());
            query.setString(4, appointmentToUpdate.getType());
            query.setTimestamp(5, Timestamp.valueOf(appointmentToUpdate.getStart()));
            query.setTimestamp(6, Timestamp.valueOf(appointmentToUpdate.getEnd()));
            query.setInt(7, appointmentToUpdate.getCustomer().getCustomerId());
            query.setInt(8, appointmentToUpdate.getUser().userId());
            query.setInt(9, appointmentToUpdate.getContact().getContactId());
            query.setInt(10, appointmentToUpdate.getId());
            successful = query.executeUpdate() > 0;
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when updating appointment!\nMessage: " + sqlException.getMessage());
        }
        return successful;
    }

    /**
     * Deletes an appointment from the database.
     *
     * @param appointment The appointment to delete.
     * @return true if the deletion was successful, otherwise false.
     */
    public static boolean delete(Appointment appointment) {
        boolean successful = false;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "DELETE FROM appointments WHERE Appointment_ID = ?");
            query.setInt(1, appointment.getAppointmentId());
            successful = query.execute();
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when deleting appointment!\nMessage: " + sqlException.getMessage());
        }
        return successful;
    }

    /**
     * Retrieves all appointments from the database.
     * Unlike some other data, the list of appointments may change during program execution,
     * so this method retrieves a fresh list each time it is called.
     *
     * @return An observable list of all appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        // List of Appointments may change during program execution,
        // so I will retrieve a fresh list each time this is called.
        ObservableList<Appointment> appointmentList = FXCollections.observableList(new LinkedList<>());
        List<Customer> fetchedCustomerList = CustomerDAO.getAllCustomers();
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement("SELECT * FROM appointments");
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
                // Find appropriate Customer
                int appointmentCustomerId = rs.getInt("Customer_ID");
                Customer customerForAppointment = null;
                for (Customer customer:
                    fetchedCustomerList) {
                    if (appointmentCustomerId == customer.getCustomerId()) {
                        customerForAppointment = customer;
                        break;
                    }
                }
                // Find appropriate User and Contact
                User userForAppointment = UserDAO.getUserById(rs.getInt("User_ID"));
                Contact contactForAppointment = ContactDAO.getContactById(rs.getInt("Contact_ID"));

                // Set values to new Customer object
                appointmentList.add(new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        customerForAppointment,
                        userForAppointment,
                        contactForAppointment
                ));
            }
            // System.out.println("Retrieved all appointments from database.");
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when retrieving all Appointments!\nMessage: " +
                    sqlException.getMessage());
        }
        return appointmentList;
    }
}
