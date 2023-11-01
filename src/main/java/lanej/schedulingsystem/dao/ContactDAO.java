package lanej.schedulingsystem.dao;

import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides data access operations for the Contact class.
 * It contains methods to fetch contacts based on various criteria,
 * and retrieve all contacts from the database.
 * <p>
 * The operations are designed to interact with a database using JDBC.
 * Errors during operations are communicated through alerts using the ScreenUtility class.
 * </p>
 *
 * @author Jonathan Lane
 * @version 1.0
 */
public abstract class ContactDAO {
    private static List<Contact> allContacts = new ArrayList<>();
    private static boolean initialized = false;

    /**
     * Retrieves all contacts from the database.
     * The list of contacts is retrieved only once as it shouldn't change during program execution.
     *
     * @return A list of all contacts.
     */
    public static List<Contact> getAllContacts() {
        // List of Contacts should not change during program execution,
        // so I will only retrieve this once.
        if (!initialized) {
            List<Contact> contactList = new ArrayList<>();
            try {
                PreparedStatement query = JDBC.getConnection().prepareStatement("SELECT * FROM contacts");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    contactList.add(new Contact(
                            rs.getInt("Contact_ID"),
                            rs.getString("Contact_Name"),
                            rs.getString("Email")
                    ));
                }
                // System.out.println("Retrieved all contacts from database.");
            } catch (SQLException sqlException) {
                ScreenUtility.alert("Error when getting all the Contacts!\nMessage: " +
                        sqlException.getMessage());
            } finally {
                allContacts = contactList;
                initialized = true;
            }
        }
        return allContacts;
    }

    /**
     * Retrieves a contact from the database based on the provided contact ID.
     *
     * @param contactId The ID of the contact.
     * @return The Contact object if found, otherwise null.
     */
    public static Contact getContactById(int contactId) {
        Contact contact = null;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "SELECT * FROM contacts WHERE Contact_ID = ?");
            query.setInt(1, contactId);
            ResultSet rs = query.executeQuery();
            // Move past header to Contact records
            rs.next();
            contact = new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")
            );
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when trying to find contact by ID!\nMessage: " +
                    sqlException.getMessage());
        }
        return contact;
    }
}
