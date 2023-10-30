package lanej.schedulingsystem.model;

public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private Country country;
    public FirstLevelDivision(int divisionId, String division, Country country) {
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;
    }
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    public void setCountry(Country country) {
        this.country = country;
    }
    public void setDivision(String division) {
        this.division = division;
    }
    public int getDivisionId() {
        return divisionId;
    }
    public String getDivisionName() {
        return division;
    }
    public Country getCountry() {
        return country;
    }
    @Override
    public String toString() {
        return division;
    }
}
