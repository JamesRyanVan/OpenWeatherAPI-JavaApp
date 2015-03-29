package main.java;


/**
 * Class that represents an exception when a location is not found from
 * an API call on OpenWeatherMap.
 * 
 * @author Team 10
 * @version 1.0
 * @since February 26, 2015
 */

public class LocationNotFoundException extends Exception {

	/**
	 * Constructor method.
	 *
	 * @param message String desired message when exception is thrown
	 * 
	 */
	public LocationNotFoundException(String message) {
		super(message);
	}
}
