package main.java;

import java.io.Serializable;

/**
 * Class that represents a city.
 * 
 * @author Group 10
 *
 */
public class City implements Serializable {
    
	/* city's ID */
    private int cityID;
    /* city's name */
    private String cityName;
    /* city's country name */
    private String countryName;
    
	   
	/**
	 * Constuctor method.
	 * 
	 * @param cityID
	 * @param cityName
	 * @param countryName
	 */
    public City(int cityID, String cityName, String countryName) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.countryName = countryName;
    }

    /**
     * Gets the city's ID.
     * 
     * @return the city's ID
     */
    public int getCityID() {
        return cityID;
    }

    /**
     * Gets the city's name.
     * 
     * @return the city's name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Gets the city's country name.
     * 
     * @return the city's country name
     */
    public String getCountryName() {
        return countryName;
    }
    
    /**
     * Returns a string representation of the city object as "cityName, countryName."
     * If cityName is blank, returns "countryName."
     * 
     * @return String representation of city object
     */
    public String toString() {
    	if (!cityName.equals("")) {
        	return cityName + ", " + countryName;
    	} 
    	return countryName;
    }
}
