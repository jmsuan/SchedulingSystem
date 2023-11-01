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
 * Data Access Object (DAO) class for managing {@link FirstLevelDivision} entities.
 * <p>
 * This class provides an abstraction over the database operations related to
 * the {@code FirstLevelDivision} entity. It contains methods for fetching all
 * divisions from the database.
 * </p>
 * <p>
 * The list of first-level divisions is expected to remain constant during the application's
 * runtime, so the {@link #getAllDivisions()} method uses caching to minimize
 * unnecessary database queries.
 * </p>
 *
 * @author Jonathan lane
 * @version 1.0
 */
public abstract class FirstLevelDivisionDAO {
    /** A cache for all the first-level divisions retrieved from the database. */
    private static List<FirstLevelDivision> allDivisions = new ArrayList<>(); // Low number of writes/inserts to middle

    /** Indicator for whether the first-level divisions have been initialized in the cache. */
    private static Boolean initialized = false;

    /**
     * Retrieves all first-level divisions from the database.
     * <p>
     * If the divisions have been previously fetched and cached, the cached
     * list is returned to minimize redundant database access.
     * </p>
     *
     * @return A list of all {@link FirstLevelDivision} entities from the database.
     */
    public static List<FirstLevelDivision> getAllDivisions() {
        // List of FirstLevelDivisions should not change during program execution,
        // so I will only retrieve this once.
        if (!initialized) {
            List<FirstLevelDivision> divisionList = new ArrayList<>();
            try {
                PreparedStatement query = JDBC.getConnection().prepareStatement(
                        "SELECT * FROM first_level_divisions");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    // Find stored country that matches division
                    int countryId = rs.getInt("Country_ID");
                    Country countryToSet = null;
                    for (Country country : CountryDAO.getAllCountries()) {
                        if (countryId == country.getCountryId()) {
                            countryToSet = country;
                            break;
                        }
                    }
                    // Set values to new FirstLevelDivision object
                    divisionList.add(new FirstLevelDivision(
                            rs.getInt("Division_ID"),
                            rs.getString("Division"),
                            countryToSet)
                    );
                }
            } catch (SQLException sqlException) {
                ScreenUtility.alert("Error when getting all the first-level divisions!\nMessage: " +
                        sqlException.getMessage());
            } finally {
                allDivisions = divisionList;
                initialized = true;
                // System.out.println("Retrieved all first-level divisions from database.");
            }
        }
        return allDivisions;
    }
}
