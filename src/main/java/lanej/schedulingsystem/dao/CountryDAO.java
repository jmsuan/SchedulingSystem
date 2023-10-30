package lanej.schedulingsystem.dao;

import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Country;
import lanej.schedulingsystem.model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class CountryDAO {
    private static List<Country> allCountries = null;
    private static Boolean initialized = false;
    public static List<Country> getAllCountries() {
        // List of Countries should not change during program execution,
        // so I will only retrieve this once.
        if (!initialized) {
            List<Country> countryList = null;
            try {
                PreparedStatement query = JDBC.getConnection().prepareStatement("SELECT * FROM countries");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    countryList.add(new Country(
                            rs.getInt("Country_ID"),
                            rs.getString("Country")
                    ));
                }
            } catch (SQLException sqlException) {
                ScreenUtility.alert("Error when getting all the Countries!\nMessage: " +
                        sqlException.getMessage());
            } finally {
                allCountries = countryList;
                initialized = true;
                System.out.println("All countries retrieved.");
            }
        }
        return allCountries;
    }
}
