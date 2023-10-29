package lanej.schedulingsystem.model;

public class FirstLevelDivision {
    private int divisionID;
    private String division;
    private Country country;
    public FirstLevelDivision(int divisionID, String division, Country country) {
        this.divisionID = divisionID;
        this.division = division;
        this.country = country;
    }
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    public void setCountry(Country country) {
        this.country = country;
    }
    public void setDivision(String division) {
        this.division = division;
    }
    public int getDivisionID() {
        return divisionID;
    }
    public String getDivision() {
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
