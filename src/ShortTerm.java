import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to parse apiObj weather from Open Weather Map API for Short term weather
 * The weather is in 3 hour increments for the next 24 hours
 * 
 * @author Curtis Vermeeren
 * @version 1.0
 * @since February 26, 2015
 */
public class ShortTerm {

	private JSONArray list;
	private double[] temps=new double[8],snow=new double[8],rain=new double[8]; 
	private String[] skyConditions=new String[9],icons=new String[8];
	private int[] times=new int[8]; 
	
	/**
	 * Creates a Short term weather object and stores the weather information
	 * Cover data for 24 hours
	 * Each index of an array represents a 3 hour period of time
	 * 
	 * @param apiObjJson A JSONObject of the short term weather from open weather map
	 * @throws JSONException
	 */
	public ShortTerm(JSONObject apiObjJson) throws JSONException{
		try{
			this.list = apiObjJson.getJSONArray("list");
			for (int i=1 ; i<9 ; i++){
				JSONObject increment = list.getJSONObject(i);
				
				this.times[i-1] = increment.getInt("dt");
				this.temps[i-1] = increment.getJSONObject("main").getDouble("temp");
				this.skyConditions[i-1] = increment.getJSONArray("weather").getJSONObject(0).getString("main");
				this.icons[i-1] = increment.getJSONArray("weather").getJSONObject(0).getString("icon");
				
				try {
					this.rain[i-1] = increment.getJSONObject("rain").getInt("3h");
					} catch (JSONException e){
						this.rain[i-1] = 0;
						//System.out.println("Rain not Found");
					}
					try {
					this.snow[i-1] = increment.getJSONObject("snow").getInt("3h");	
					} catch (JSONException e){
						this.snow[i-1] = 0;
						//System.out.println("snow not Found");
					}
				
			
			}
		} catch (NullPointerException e){
			System.out.println("Location not found");
		}
	}
	
	/**
	 * Method to get the Short term forecast sky conditions for 24 hours 
	 * Each index of the array is 3 hours
	 * @return String array of the sky conditions
	 */
	public String[] getSkyConditions() {
		return skyConditions;
	}

	/**
	 * Method to get the Short term forecast sky condition icons for 24 hours 
	 * Each index of the array is 3 hours
	 * Each string is a code used in a url to get a specific icon
	 * @return String array of the sky condition icons
	 */
	public String[] getIcons() {
		return icons;
	}

	/**
	 * @return an array storing the total snow and rain precipitation for the 24 hours
	 * each index is one 3 hour increment
	 */
	public double[] getPrecips() {
	double[] precips = new double[8];
	for (int x=0; x<8;x++){
		precips[x] = snow[x] + rain [x];
	}
	return precips;
	}

	/**
	 * Method to get the times of weather data for 24 hours
	 * Each index of the array is 3 hours
	 * @return String of integers each integer is a unix time
	 */
	public int[] getTimes() {
		return times;
	}
	
	/**
	 * Method to get the Short term forecast rain amount for 24 hours 
	 * Each index of the array is 3 hours
	 * @return Array with the amount of rain every 3 hours at each index
	 */
	public double[] getRain(){
		return rain;
	}
	
	/**
	 * Method to get the Short term forecast snow amount for 24 hours 
	 * Each index of the array is 3 hours
	 * @return Array with the amount of snow every 3 hours at each index
	 */
	public double[] getSnow(){
		return snow;
	}
	
	/**
	 * Method to get the Short term forecast temperatures for 24 hours 
	 * Each index of the array is 3 hours
	 * @return Array of doubles each a temperature
	 */
	public double[] getTemps(){
		return temps;
	}

	/**
	 * Method to return an array of strings containing just the times of weather data
	 * @return strings with times of weather data
	 */
	public String[] getTimeArray(){
		String[] timeStrings = new String[9];
		for (int t=0; t<9; t++){
			timeStrings[t] = unixToTime(times[t]);
		}
		return timeStrings;
	}
	
	/**
	 * Method to return an array of strongs containing times and dates of weather data
	 * @return string with time and date of weather data
	 */
	public String[] getDateArray(){
		String[] timeStrings = new String[8];
		for (int t=0; t<8; t++){
			timeStrings[t] = unixToDate(times[t]);
		}
		return timeStrings;
	}
	
	/**
	 * Helper method converts unix time to a formatted date
	 * 
	 * @param An integer of the unix time to be converted
	 * @return String of the formatted date and time
	 */
	private String unixToDate(int unixTime){
		int unixSeconds = unixTime;
		Date date = new Date(unixSeconds*1000L); 
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		String formattedDate = formatDate.format(date);
		return formattedDate;
	}
	
	
	/**
	 * Helper method converts unix time to a formatted time
	 * @param An integer of the unix time to be converted
	 * @return String of the formatted time
	 */
	private String unixToTime(int unixTime){
		int unixSeconds = unixTime;
		Date date = new Date(unixSeconds*1000L); 
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss z");
		String formattedTime = formatTime.format(date);
		return formattedTime;
	}
	
}

