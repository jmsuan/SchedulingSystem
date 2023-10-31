package lanej.schedulingsystem.model;

/**
 * Represents a first-level division (e.g., state, province) within a country.
 * <p>
 * This class is used to model subdivisions within countries in the application. Each division
 * is associated with a specific country and has a unique identifier and name.
 * </p>
 *
 * @author Jonathan Lane
 */
public class FirstLevelDivision {
    /** The unique identifier for the division. */
    private int divisionId;

    /** The name of the division. */
    private String division;

    /** The country to which the division belongs. */
    private Country country;

    /**
     * Constructs a new FirstLevelDivision with the specified ID, name, and associated country.
     *
     * @param divisionId The unique identifier for the division.
     * @param division   The name of the division.
     * @param country    The country to which the division belongs.
     */
    public FirstLevelDivision(int divisionId, String division, Country country) {
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;
    }

    /**
     * Sets the unique identifier for the division.
     *
     * @param divisionId The new ID for the division.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Associates the division with a specific country.
     *
     * @param country The country to which the division belongs.
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Sets the name of the division.
     *
     * @param division The new name for the division.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Returns the unique identifier of the division.
     *
     * @return The division's ID.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Returns the name of the division.
     *
     * @return The division's name.
     */
    public String getDivisionName() {
        return division;
    }

    /**
     * Returns the country to which the division belongs.
     *
     * @return The associated country.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Provides a string representation of the division, which is its name.
     *
     * @return The name of the division.
     */
    @Override
    public String toString() {
        return division;
    }
}
