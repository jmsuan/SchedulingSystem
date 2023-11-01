package lanej.schedulingsystem.model;

/**
 * Represents a contact within the scheduling system, consisting of an identifier, a name, and an email.
 *
 * <p>The contact information is critical for various operations within the system such as sending notifications,
 * managing appointments, and for user interaction purposes.</p>
 *
 * @author Jonathan Lane
 */
public class Contact {

    /** The unique identifier for the contact. */
    private int contactId;

    /** The name of the contact. */
    private String contactName;

    /** The email address of the contact. */
    private String email;

    /**
     * Constructs a Contact with the specified identifier, name, and email.
     *
     * @param contactId   The unique identifier for the contact.
     * @param contactName The name of the contact.
     * @param email       The email address of the contact.
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Sets the unique identifier for this contact.
     *
     * @param contactId The unique identifier to set.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Sets the name of this contact.
     *
     * @param contactName The name to set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Sets the email address for this contact.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the unique identifier of this contact.
     *
     * @return The unique identifier of the contact.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Retrieves the name of this contact.
     *
     * @return The name of the contact.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Retrieves the email address of this contact.
     *
     * @return The email address of the contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Provides a string representation of the contact, typically used for display purposes.
     *
     * @return The name of the contact.
     */
    @Override
    public String toString() {
        return contactName;
    }
}
