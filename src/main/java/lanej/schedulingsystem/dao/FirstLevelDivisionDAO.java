package lanej.schedulingsystem.dao;

import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Country;
import lanej.schedulingsystem.model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class FirstLevelDivisionDAO {
    private static List<FirstLevelDivision> allDivisions = null;
    private static Boolean initialized = false;
    public static List<FirstLevelDivision> getAllDivisions() {
        // List of FirstLevelDivisions should not change during program execution,
        // so I will only retrieve this once.
        if (!initialized) {
            List<FirstLevelDivision> divisionList = null;
            try {
                PreparedStatement query = JDBC.getConnection().prepareStatement("SELECT * FROM first_level_divisions");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    // Find stored country that matches division
                    int countryId = rs.getInt("Country_ID");
                    Country countryToSet = null;
                    for (Country country :
                            CountryDAO.getAllCountries()) {
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
                System.out.println("Retrieved all first-level divisions from database.");
            }
        }
        return allDivisions;
    }
}
