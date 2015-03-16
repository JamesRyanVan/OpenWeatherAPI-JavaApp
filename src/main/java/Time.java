import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to get a formatted date for a unix time integer. 
 * 
 * @author Curtis Vermeeren
 * @version 1.0
 * @since March 16, 2015
 */
public class Time {

	private int unixTime;
	
	/**
	 * @param An integer of a unix time
	 * 
	 */
	public Time(int unixTime){
		this.unixTime = unixTime;
	}
	
	/**
	 * A string of the time and date of the time object
	 * @return A string of the formatted date
	 */
	public String unixToDate(){
		int unixSeconds = unixTime;
		Date date = new Date(unixSeconds*1000L); 
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		String formattedDate = formatDate.format(date);
		return formattedDate;
	}
	
	/**
	 * A string of the time of the time object with no date
	 * @return a sting of the formatted time
	 */
	public String unixToTime(){
		int unixSeconds = unixTime;
		Date date = new Date(unixSeconds*1000L); 
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss z");
		String formattedTime = formatTime.format(date);
		return formattedTime;
	}
	
	/**
	 * 
	 * @return the integer of the unit time 
	 */
	public int getUnixTime(){
		return unixTime;
	}
	
	
}
