package main.java;

import java.io.Serializable;

/**
 * Class that stores user settings/preferences.
 * 
 * 
 * @author Alec White
 * @version 1.0
 * @since March 2 2015
 *
 */

public class Settings implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* Boolean values to determine whether or not user wishes to view corresponding data */
	private boolean Temp, WindSpeedAndDir, SkyCondition, AirPressure, Humidity,
	SunsetAndRise, MetricUnits, precipitation;
	
	/* Array storing city's */
	private City[] cityList;
	
	/* Int value representing how many cities user has stored */
	private int listSize = 0;
	
	/* Int value pointing to cell in cityIDList array which corresponds to current city */
	private City currentCity;
	
	/**
	 * Creates a settings object. Initialized upon users first use setting boolean values to true
	 * if user wishes to view corresponding data.
	 * 
	 * @param temp true if user wishes to view temperature
	 * @param windSpeed true if user wishes to view wind speed and direction
	 * @param sky true if user wishes to view sky condition
	 * @param airPressure true if user wishes to view air pressure
	 * @param humidity true if user wishes to view humidity
	 * @param sunset true if user wishes to view sunset and sunrise times
	 * @param metric true if user wishes to view measurements in metric, false if imperial
	 * @param city int value of city ID for current city. Initially chosen upon startup
	 * @param precip true if user wishes to view precipitation for next 24 hours
	 */
	public Settings(boolean temp, boolean windSpeed, boolean sky, boolean airPressure, boolean humidity,
			boolean sunset, boolean metric, City city,boolean precip)
	{
		Temp = temp;
		WindSpeedAndDir = windSpeed;
		SkyCondition = sky;
		AirPressure = airPressure;
		Humidity = humidity;
		SunsetAndRise = sunset;
		MetricUnits = metric;
		cityList = new City[50];
		cityList[listSize] = city;
		listSize++;
		currentCity = city;
		precipitation = precip;
	}
	
	/**
	 * @return true if user wishes to view temperature
	 */
	public boolean viewTemp()
	{
		return Temp;
	}
	
	/**
	 * @return true is user wishes to view wind speed and direction
	 */
	public boolean viewWindSpeedAndDir()
	{
		return WindSpeedAndDir;
	}
	
	/**
	 * @return true if user wishes to view sky condition
	 */
	public boolean viewSkyCondition()
	{
		return SkyCondition;
	}
	
	/**
	 * @return true if user wishes to view air pressure
	 */
	public boolean viewAirPressure()
	{
		return AirPressure;
	}
	
	/**
	 * @return true if user wishes to view humidity
	 */
	public boolean viewHumidity()
	{
		return Humidity;
	}
	
	/**
	 * @return true if user wishes to view sunset and sunrise times
	 */
	public boolean viewSunsetAndRise()
	{
		return SunsetAndRise;
	}
	
	/**
	 * @return true if user wishes to view measurements in metric, false if imperial
	 */
	public boolean viewMetricUnits()
	{
		return MetricUnits;
	}
	
	/**
	 * @return true if user wishes to view 24 hour precipitation levels
	 */
	public boolean viewPrecipitation(){
		return precipitation;
	}
	
	/**
	 * @param temp true if user wishes to view temperature
	 */
	public void setViewTemp(boolean temp)
	{
		Temp = temp;
	}
	
	
	/**
	 * @param windSpeed true if user wishes to view wind speed and direction
	 */
	public void setViewWindSpeedAndDir(boolean windSpeed)
	{
		WindSpeedAndDir = windSpeed;
	}
	
	/**
	 * @param sky true if user wishes to view sky condition
	 */
	public void setSkyCondition(boolean sky)
	{
		SkyCondition = sky;
	}
	
	/**
	 * @param airPressure true if user wishes to view air pressure
	 */
	public void setViewAirPressure(boolean airPressure)
	{
		AirPressure = airPressure;
	}
	
	/**
	 * @param humidity true if user wishes to view humidity
	 */
	public void setViewHumidity(boolean humidity)
	{
		Humidity = humidity;
	}
	
	/**
	 * @param sunset true if user wishes to view sunset and sunrise times
	 */
	public void setViewSunsetAndRise(boolean sunset)
	{
		SunsetAndRise = sunset;
	}
	
	/**
	 * @param metric true if user wishes to view measurements in metric, false if imperial
	 */
	public void setViewMetricUnits(boolean metric)
	{
		MetricUnits = metric;
	}
	
	/**
	 * @param precip true if user wishes to view 24 hour precipitation levels
	 */
	public void setViewPrecipitation(boolean precip){
		precipitation = precip;
	}
	
	/**
	 * @param newLoc int value of new city ID to be added to city ID list
	 */
	public void addLocation(City newLoc)
	{
		cityList[listSize] = newLoc;
		listSize++;
	}
	
	/**
	 * Updates to the current city ID.
	 * 
	 * @param newCurrent int value of city ID to be changed to current city
	 * 
	 */
	public void changeCurrentCity(City newCurrent)
	{
		currentCity = newCurrent;
	}
	
	
	/**
	 * Gets the City object for the current city ID.
	 * 
	 * @return the City object for the current city ID
	 * 
	 */
	public City getCity(){
		return this.currentCity;
	}
	
	
	/**
	 * Gets the list of City objects that have been stored.
	 * 
	 * @return cityList the list of City objects that have been stored
	 * 
	 */
	public City[] getCityList() {
		return cityList;
	}
	
	
}
