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
	
	public String[] getSkyConditions() {
		return skyConditions;
	}

	public String[] getIcons() {
		return icons;
	}

	public double[] getPrecips() {
	double[] precips = new double[8];
	for (int x=0; x<8;x++){
		precips[x] = snow[x] + rain [x];
	}
	return precips;
	}

	public int[] getTimes() {
		return times;
	}
	
	public double[] getTemps(){
		return temps;
	}

	public String[] getTimeArray(){
		String[] timeStrings = new String[9];
		for (int t=0; t<9; t++){
			timeStrings[t] = unixToTime(times[t]);
		}
		return timeStrings;
	}
	
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

