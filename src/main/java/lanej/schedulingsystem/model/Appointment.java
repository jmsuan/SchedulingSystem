package lanej.schedulingsystem.model;

import java.time.LocalDateTime;

/**
 * Represents an appointment within the scheduling system, capturing essential details
 * such as title, description, timing, and associated entities like customer, user, and contact.
 *
 * <p>This class serves as a core component for managing and organizing appointments,
 * essential for the overall functionality of the scheduling system.</p>
 *
 * <p>Appointments implement the TableSearchable interface, as we plan to list Appointments as one of the main
 * elements of this application. The user will be able to filter and sort this item while it's represented in a
 * TableView.</p>
 *
 * @author Jonathan Lane
 */
public class Appointment implements TableSearchable {

    /** The unique identifier for the appointment. */
    private final Integer appointmentId;

    /** The title of the appointment. */
    private String title;

    /** Detailed description or agenda of the appointment. */
    private String description;

    /** Location or venue of the appointment. */
    private String location;

    /** The type or category of the appointment. */
    private String type;

    /** The start time of the appointment. */
    private LocalDateTime start;

    /** The end time of the appointment. */
    private LocalDateTime end;

    /** The customer associated with the appointment. */
    private Customer customer;

    /** The user or employee managing the appointment. */
    private User user;

    /** The contact or representative for the appointment. */
    private Contact contact;

    /**
     * The direct-parameter constructor for an appointment. The aim of providing this as opposed to having multiple
     * constructors or adding values to the object over time, is to allow for clear chunks of code where it's easy
     * to read what the intention is. With the number of classes there are, if I can keep the instantiation of one
     * to one/two statements, then that's preferred.
     *
     * <p>Takes the following parameters (null if necessary), and collects that into a defined information "relation"
     * which is an accurate representation of what it is (especially considering the ERD model it was based on).</p>
     *
     * @param appointmentId The unique identifier of the Appointment.
     *                      The database is responsible for determining the ID.
     * @param title         The name/title of the appointment.
     * @param description   The description of the appointment.
     * @param location      The location or venue of the appointment.
     * @param type          The type or category of the appointment.
     * @param start         The LocalDateTime of the appointment start.
     * @param end           The LocalDateTime of the appointment ending.
     * @param customer      The customer that the appointment is for.
     * @param user          The user (employee) that is assigned to (and should attend) this appointment.
     * @param contact       The contact or representative for the appointment.
     */
    public Appointment(Integer appointmentId,
                       String title,
                       String description,
                       String location,
                       String type,
                       LocalDateTime start,
                       LocalDateTime end,
                       Customer customer,
                       User user,
                       Contact contact) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer = customer;
        this.user = user;
        this.contact = contact;
    }

    /**
     * Sets the title of the appointment.
     *
     * @param title The new title for the appointment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the appointment.
     *
     * @param description The new description for the appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the location where the appointment is to be held.
     *
     * @param location The new location for the appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the type or category of the appointment.
     *
     * @param type The new type for the appointment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the start time of the appointment.
     *
     * @param start The new start time for the appointment.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Sets the end time of the appointment.
     *
     * @param end The new end time for the appointment.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Sets the customer associated with the appointment.
     *
     * @param customer The new customer associated with the appointment.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sets the user or employee who will manage the appointment.
     *
     * @param user The new user for the appointment.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the contact or representative for the appointment.
     *
     * @param contact The new contact for the appointment.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Retrieves the ID of the appointment.Fulfills the TableSearchable interface.
     *
     * @return The appointment ID.
     */
    public int getId() {
        return this.getAppointmentId();
    }

    /**
     * Retrieves the title of the appointment. Fulfills the TableSearchable interface.
     *
     * @return The title of the appointment.
     */
    public String getName() {
        return this.getTitle();
    }

    /**
     * Retrieves the ID of the appointment.
     *
     * @return The appointment ID.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Retrieves the title of the appointment.
     *
     * @return The title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the description of the appointment.
     *
     * @return The description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the location where the appointment will be held.
     *
     * @return The location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Retrieves the type or category of the appointment.
     *
     * @return The type of the appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the start time (and date) of the appointment.
     *
     * @return The start time of the appointment as a LocalDateTime.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Retrieves the end time (and date) of the appointment.
     *
     * @return The end time of the appointment as a LocalDateTime.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Retrieves the customer associated with the appointment.
     *
     * @return The customer of the appointment.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Retrieves the user or employee associated with the appointment.
     *
     * @return The user of the appointment.
     */
    public User getUser() {
        return user;
    }

    /**
     * Retrieves the contact or representative for the appointment.
     *
     * @return The contact of the appointment.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Overrides the default toString method for the Appointment record.
     *
     * @return A string representation of the Appointment, just spelling out the name/title of the Appointment.
     */
    @Override
    public String toString() {
        return title;
    }
}
