package lanej.schedulingsystem.model;

/**
 * Represents a country with its unique identifier and name.
 * <p>
 * This class is used to model countries that will be associated with other entities within the application.
 * It provides methods to get and set the country's properties.
 * </p>
 *
 * @author Jonathan Lane
 */
public class Country {
    /** The unique identifier for the country. */
    private int countryId;
    /** The name of the country. */
    private String country;
    /**
     * Constructs a new Country with the specified ID and name.
     *
     * @param countryId The unique identifier for the country.
     * @param country   The name of the country.
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Sets the name of the country.
     *
     * @param country The new name for the country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sets the unique identifier for the country.
     *
     * @param countryId The new ID for the country.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Returns the unique identifier of the country.
     *
     * @return The country's ID.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Returns the name of the country.
     *
     * @return The country's name.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Provides a string representation of the country, which is its name.
     *
     * @return The name of the country.
     */
    @Override
    public String toString() {
        return country;
    }
}
