import java.text.SimpleDateFormat;
import java.util.Date;
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
	private int sunriseTime, sunsetTime, updateTime;
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
			this.sunriseTime = sys.getInt("sunrise");
			this.sunsetTime  = sys.getInt("sunset");
			
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
			this.updateTime = apiObj.getInt("dt");
		
		/* Ensure the location was valid and not null */
		} catch (NullPointerException e){
			System.out.println("Location not found");
		}
			
	}
	public double getTempMax() {
		return tempMax;
	}
	public double getTempMin() {
		return tempMin;
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

	public String getSunriseString(){
		return unixToTime(this.sunriseTime);
	}
	
	public String getSunsetString(){
		return unixToTime(this.sunsetTime);
	}
	
	public String getUpdateTimeString(){
		return unixToDate(this.updateTime);
	}
	
	public int getSunriseTime() {
		return sunriseTime;
	}

	public int getSunsetTime() {
		return sunsetTime;
	}

	public int getUpdateTime() {
		return updateTime;
	}

	public double getTemp() {
		return temp;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public double getWindDir() {
		return windDir;
	}

	public double getAirPressure() {
		return airPressure;
	}

	public double getHumidity() {
		return humidity;
	}

	public String getSkyCondition() {
		return skyCondition;
	}

	public String getIcon() {
		return icon;
	}
}
