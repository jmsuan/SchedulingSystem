package lanej.schedulingsystem.model;

public class Country {
    private int countryID;
    private String country;
    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    public int getCountryID() {
        return countryID;
    }
    public String getCountry() {
        return country;
    }
    @Override
    public String toString() {
        return country;
    }
}
