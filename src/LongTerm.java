package longterm;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to parse apiObj weather from Open Weather Map API for Short term
 * weather It is a daily forecast for the following 5 days.
 * 
 * @author Li Guo
 *
 */
public class LongTerm {
	private JSONArray list;
	private double[] temps = new double[5], minTemps = new double[5],
			maxTemps = new double[5], snow = new double[5],
			rain = new double[5];
	private String[] skyConditions = new String[5], icons = new String[5];
	private int[] days = new int[5];

	/**
	 * Creates a long term weather object and stores the weather information
	 * Cover data for next 5 days
	 * 
	 * @param apiObjJson
	 *            A JSONObject of the long term weather from open weather map
	 * @throws JSONException
	 */
	public LongTerm(JSONObject apiObjJson) throws JSONException {
		try {
			this.list = apiObjJson.getJSONArray("list");
			for (int i = 1; i < 6; i++) {
				JSONObject increment = list.getJSONObject(i);

				this.days[i - 1] = increment.getInt("dt");
				this.temps[i - 1] = increment.getJSONObject("temp").getDouble(
						"day");
				this.minTemps[i - 1] = increment.getJSONObject("temp")
						.getDouble("min");
				this.maxTemps[i - 1] = increment.getJSONObject("temp")
						.getDouble("max");
				this.skyConditions[i - 1] = increment.getJSONArray("weather")
						.getJSONObject(0).getString("main");
				this.icons[i - 1] = increment.getJSONArray("weather")
						.getJSONObject(0).getString("icon");

				try {
					this.rain[i - 1] = increment.getDouble("rain");
				} catch (JSONException e) {
					this.rain[i - 1] = 0;

				}
				try {
					this.snow[i - 1] = increment.getDouble("snow");
				} catch (JSONException e) {
					this.snow[i - 1] = 0;

				}

			}
		} catch (NullPointerException e) {
			System.out.println("Location not found");
		}
	}

	public double[] getTemps() {
		return temps;
	}

	/**
	 * /** Method to get the Short term forecast sky conditions for 5 days Each
	 * index of the array is one day
	 * 
	 * @return String array of the sky conditions
	 */
	public String[] getSkyConditions() {
		return skyConditions;
	}

	/**
	 * Method to get the long term forecast sky condition icons for 5 days Each
	 * index of the array is a day Each string is a code used in a url to get a
	 * specific icon
	 * 
	 * @return String array of the sky condition icons
	 */
	public String[] getIcons() {
		return icons;
	}
	/**
	 * Method to get the Short term forecast rain amount for 5 days
	 * Each index of the array is one day
	 * @return Array with the amount of rain every day
	 */
	public double[] getRain(){
		return rain;
	}
	
	/**
	 * Method to get the Short term forecast snow amount for 5 days
	 * Each index of the array is one day
	 * @return Array with the amount of snow every day
	 */
	public double[] getSnow(){
		return snow;
	}
	
	/**
	 * 
	 * @return an array storing the total snow and rain precipitation for 5 days
	 *  Each index is one day
	 */

	public double[] getPrecips() {
		double[] precips = new double[10];
		for (int x = 0; x < 6; x++) {
			precips[x] = snow[x] + rain[x];
		}
		return precips;
	}

	/**
	 * Method to get the long term forecast minimum temperature for 5 days Each
	 * index of the array is one day
	 * 
	 * @return double array of minimum temperature
	 */
	public double[] getMinTemps() {
		return minTemps;
	}

	/**
	 * Method to get the long term forecast maximum temperature for 5 days Each
	 * index of the array is one day
	 * @return double array of maximum temperature
	 */
	public double[] getMaxTemps() {
		return maxTemps;
	}

	/**
	 * Method to get the times of weather data for 5 days
	 * Each index of the array is one day
	 * @return String of integers each integer is a unix time
	 */
	public int[] getTimes() {
		return days;
	}

	/**
	 * Method to return an array of strongs containing times and dates of weather data
	 * @return string with time and date of weather data
	 */
	public String[] getDateArray() {
		String[] timeStrings = new String[10];
		for (int t = 0; t < 6; t++) {
			timeStrings[t] = unixToDate(days[t]);
		}
		return timeStrings;
	}

	/**
	 * Helper method converts unix time to a formatted date
	 * 
	 * @param An
	 *            integer of the unix time to be converted
	 * @return String of the formatted date and time
	 */
	private String unixToDate(int unixTime) {
		int unixSeconds = unixTime;
		Date date = new Date(unixSeconds * 1000L);
		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss z");
		String formattedDate = formatDate.format(date);
		return formattedDate;
	}

}
