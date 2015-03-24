package main.java;

import java.io.IOException;

/**
 * Class that stores user settings/preferences
 * 
 * 
 * @author Alec White
 * @version 1.0
 * @since March 2 2015
 *
 */

public class Settings
{
	/* Boolean values to determine whether or not user wishes to view corresponding data */
	boolean Temp, WindSpeedAndDir, SkyCondition, AirPressure, Humidity,
	SunsetAndRise, MetricUnits;
	
	/* Array storing city ID's */
	int[] cityIDList = new int[50];
	
	/* Int value representing how many cities user has stored */
	int listSize = 0;
	
	/* Int value pointing to cell in cityIDList array which corresponds to current city */
	int currentCityID;

	/*
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
	 */
	public Settings(boolean temp, boolean windSpeed, boolean sky, boolean airPressure, boolean humidity,
			boolean sunset, boolean metric, int city)
	{
		Temp = temp;
		WindSpeedAndDir = windSpeed;
		SkyCondition = sky;
		AirPressure = airPressure;
		Humidity = humidity;
		SunsetAndRise = sunset;
		MetricUnits = metric;
		
		cityIDList[listSize] = city;
		listSize++;
		currentCityID = 0;
	}
	
	/*
	 * @return true if user wishes to view temperature
	 */
	public boolean viewTemp()
	{
		return Temp;
	}
	
	/*
	 * @return true is user wishes to view wind speed and direction
	 */
	public boolean viewWindSpeedAndDir()
	{
		return WindSpeedAndDir;
	}
	
	/*
	 * @return true if user wishes to view sky condition
	 */
	public boolean viewSkyCondition()
	{
		return SkyCondition;
	}
	
	/*
	 * @return true if user wishes to view air pressure
	 */
	public boolean viewAirPressure()
	{
		return AirPressure;
	}
	
	/*
	 * @return true if user wishes to view humidity
	 */
	public boolean viewHumidity()
	{
		return Humidity;
	}
	
	/*
	 * @return true if user wishes to view sunset and sunrise times
	 */
	public boolean viewSunsetAndRise()
	{
		return SunsetAndRise;
	}
	
	/*
	 * @return true if user wishes to view measurements in metric, false if imperial
	 */
	public boolean viewMetricUnits()
	{
		return MetricUnits;
	}
	
	/*
	 * @param temp true if user wishes to view temperature
	 */
	public void setViewTemp(boolean temp)
	{
		Temp = temp;
	}
	
	/*
	 * @param windSpeed true if user wishes to view wind speed and direction
	 */
	public void setViewWindSpeedAndDir(boolean windSpeed)
	{
		WindSpeedAndDir = windSpeed;
	}
	
	/*
	 * @param sky true if user wishes to view sky condition
	 */
	public void setSkyCondition(boolean sky)
	{
		SkyCondition = sky;
	}
	
	/*
	 * @param airPressure true if user wishes to view air pressure
	 */
	public void setViewAirPressure(boolean airPressure)
	{
		AirPressure = airPressure;
	}
	
	/*
	 * @param humidity true if user wishes to view humidity
	 */
	public void setViewHumidity(boolean humidity)
	{
		Humidity = humidity;
	}
	
	/*
	 * @param sunset true if user wishes to view sunset and sunrise times
	 */
	public void setViewSunsetAndRise(boolean sunset)
	{
		SunsetAndRise = sunset;
	}
	
	/*
	 * @param metric true if user wishes to view measurements in metric, false if imperial
	 */
	public void setViewMetricUnits(boolean metric)
	{
		MetricUnits = metric;
	}
	
	/*
	 * @param newLoc int value of new city ID to be added to city ID list
	 */
	public void addLocation(int newLoc)
	{
		cityIDList[listSize] = newLoc;
		listSize++;
	}
	
	/*
	 * Linear searches cityIDList for matching city ID and sets currentCityID to point
	 * to corresponding cell.
	 * Throws exception otherwise.
	 * 
	 * @param newCurrent int value of city ID to be changed to current city
	 * @exception IOException if city ID not found in cityIDList
	 */
	public void changeCurrentCity(int newCurrent) throws IOException
	{
		for (int i=0; i<listSize; i++)
		{
			if (cityIDList[i] == newCurrent)
			{
				currentCityID = i;
				return;
			}
		}
		throw new IOException("City not found");
	}
}