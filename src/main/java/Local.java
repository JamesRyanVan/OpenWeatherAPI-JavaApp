package main.java;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to parse apiObj weather from Open Weather Map API for local weather
 * 
 * @author Curtis Vermeeren
 * @version 1.0
 * @since February 26, 2015
 */
public class Local {
	private JSONObject apiObj;
	private Time sunriseTime, sunsetTime, updateTime;
	private double temp, windSpeed, windDir, airPressure, humidity, tempMax, tempMin ;
	private String skyCondition, icon;
	
	/**
	 * Creates a apiObj object. Stores the apiObj weather data.
	 * 
	 * @param apiObjJSON the JSON object contain the apiObj weather data
	 * @throws JSONException 
	 */
	public Local(JSONObject apiObjJSON) throws JSONException{
		
		this.apiObj= apiObjJSON;
		/* Get the system data from JSON object */
		try{
			JSONObject sys = apiObj.getJSONObject("sys");
			this.sunriseTime = new Time(sys.getInt("sunrise"));
			this.sunsetTime  = new Time(sys.getInt("sunset"));
			
			/* Get the weather data from JSON object*/
			JSONObject weather = apiObj.getJSONArray("weather").getJSONObject(0);
			this.skyCondition = weather.getString("main");
			this.icon = weather.getString("icon");
			
			/* Get the main data from JSON object */
			JSONObject main = apiObj.getJSONObject("main");
			this.temp = main.getDouble("temp");
			this.tempMax = main.getDouble("temp_max");
			this.tempMin = main.getDouble("temp_min");
			this.airPressure = main.getDouble("pressure");
			this.humidity = main.getDouble("humidity");
			
			/* Get the wind data from JSON object */
			JSONObject wind = apiObj.getJSONObject("wind");
			this.windSpeed = wind.getDouble("speed");
			this.windDir = wind.getDouble("deg");
			
			/* Get the time data from the JSON object */
			this.updateTime = new Time(apiObj.getInt("dt"));
		
		/* Ensure the location was valid and not null */
		} catch (NullPointerException e){
			System.out.println("Location not found");
		}
			
	}
	
	/**
	 *The getter methods for the local weather data 
	 */
	
	//Gets the dates and times as strings
	/**
	 * @return a Time object of the sunrise 
	 */
	public Time getSunriseTime(){
		return this.sunriseTime;
	}
	
	/**
	 * @return a Time object of the sunset
	 */
	public Time getSunsetTime(){
		return this.sunsetTime;
	}
	
	/**
	 * @return a Time object of the time of the data collection
	 */
	public Time getUpdateTime(){
		return this.updateTime;
	}
	
	/**
	 * @return double of the current temperature
	 */
	public double getTemp() {
		return temp;
	}

	/**
	 * @return double of the wind speed
	 */
	public double getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @return double of the wind direction
	 */
	public double getWindDir() {
		return windDir;
	}
	
	/**
	 * @return double of the air speed
	 */
	public double getAirPressure() {
		return airPressure;
	}

	/**
	 * @return double of the humidity
	 */
	public double getHumidity() {
		return humidity;
	}

	/**
	 * @return String of the sky conditions currently
	 */
	public String getSkyCondition() {
		return skyCondition;
	}

	/**
	 * @return String of the location of the sky conditions icon.
	 */
	public String getIcon() {
		return icon;
	}
	
	/**
	 * @return double of the maximum temperature of the day
	 */
	public double getTempMax() {
		return tempMax;
	}
	
	/**
	 * @return double of the minimum temperature of the day
	 */
	public double getTempMin() {
		return tempMin;
	}
	
}
