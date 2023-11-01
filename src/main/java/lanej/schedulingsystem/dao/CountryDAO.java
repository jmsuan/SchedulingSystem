package lanej.schedulingsystem.dao;

import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Country;
import lanej.schedulingsystem.model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Country} entities.
 * <p>
 * This class provides an abstraction over the database operations related to
 * the Country class. It primarily contains a method for fetching all countries
 * from the database.
 * </p>
 * <p>
 * The list of countries is expected to remain constant during the application's
 * runtime, so the {@link #getAllCountries()} method uses caching to minimize
 * redundant database queries.
 * </p>
 *
 * @author Malcolm Wabara
 * @author Jonathan Lane
 * @version 1.0
 */
public abstract class CountryDAO {
    /** A cache for all the countries retrieved from the database. */
    private static List<Country> allCountries = new ArrayList<>();

    /** Indicator for whether the countries have been initialized in the cache. */
    private static boolean initialized = false;

    /**
     * Retrieves all countries from the database.
     * <p>
     * If the countries have been previously fetched and cached, the cached
     * list is returned to minimize unnecessary database access.
     * </p>
     *
     * @return A list of all {@link Country} entities from the database.
     */
    public static List<Country> getAllCountries() {
        // List of Countries should not change during program execution,
        // so I will only retrieve this once.
        if (!initialized) {
            List<Country> countryList = new ArrayList<>();
            try {
                PreparedStatement query = JDBC.getConnection().prepareStatement("SELECT * FROM countries");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    countryList.add(new Country(
                            rs.getInt("Country_ID"),
                            rs.getString("Country")
                    ));
                }
                // System.out.println("Retrieved all countries from database.");
            } catch (SQLException sqlException) {
                ScreenUtility.alert("Error when getting all the Countries!\nMessage: " +
                        sqlException.getMessage());
            } finally {
                allCountries = countryList;
                initialized = true;
            }
        }
        return allCountries;
    }
}
