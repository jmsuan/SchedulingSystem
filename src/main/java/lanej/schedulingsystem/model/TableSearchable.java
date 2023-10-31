package lanej.schedulingsystem.model;

/**
 * Provides some standard functionality for an objects that is searchable within a table.
 * <p>
 * This interface enforces that any implementing class provides methods to
 * retrieve an ID and a name, which can be essential for searching and displaying
 * objects within a table view. I use this in the CustomerAppointmentController class
 * as a means to search both tables easily.
 * </p>
 *
 * @author Jonathan Lane
 */
public interface TableSearchable {
    /**
     * Returns the unique identifier of the object.
     *
     * @return The object's ID.
     */
    int getId();

    /**
     * Returns the name or title of the object.
     *
     * @return The object's name.
     */
    String getName();
}
