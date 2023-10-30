package lanej.schedulingsystem.helper;

import lanej.schedulingsystem.dao.FirstLevelDivisionDAO;
import lanej.schedulingsystem.model.Country;
import lanej.schedulingsystem.model.FirstLevelDivision;

import java.util.List;

public abstract class ConverterUtility {
    public static List<FirstLevelDivision> getAllDivisionsOfCountry(Country country) {
        List<FirstLevelDivision> countryDivisions = null;
        List<FirstLevelDivision> allDivisions = FirstLevelDivisionDAO.getAllDivisions();
        for (FirstLevelDivision division:
             allDivisions) {
            if (division.getCountry().equals(country)) {
                countryDivisions.add(division);
            }
        }
        return countryDivisions;
    }

}
