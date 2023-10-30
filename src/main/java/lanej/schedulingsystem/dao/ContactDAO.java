package lanej.schedulingsystem.dao;

import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ContactDAO {
    private static List<Contact> allContacts = new ArrayList<>();
    private static boolean initialized = false;
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
                System.out.println("Retrieved all contacts from database.");
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

    public static Contact getContactById(int contactId) {
        Contact contact = null;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "SELECT * FROM contacts WHERE Contact_ID = ?");
            query.setInt(1, contactId);
            ResultSet rs = query.executeQuery();
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
