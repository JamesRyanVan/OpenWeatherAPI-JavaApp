package main.java;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Class that performs a call on the Open Weather Map API and fetches the JSONObject.
 *
 * @author Jeremy Alcorta
 * @version 1.0
 * @since February 24, 2015
 */
public class WeatherAPI {

    /* City id for the query. */
    private int cityID;

    /* Boolean to determine units used for query. */
    private boolean viewMetric;

    /* Base url string */
    private final static String BASE = "http://api.openweathermap.org/data/2.5/";

    /* Metric units parameter for url string */
    private final static String METRIC = "&units=metric";

    /* Imperial units parameter for url string */
    private final static String IMPERIAL = "&units=imperial";

    /* API key parameter for url string */
    private final static String APIKEY = "&APPID=bb4d737a642c4e98afff6652ea5e0d17";

    /* JSON parameter for url string */
    private final static String JSON = "&mode=json";

    /**
     * Creates a WeatherAPI object. Sets the city ID and units to be used (metric or imperial).
     *
     * @param cityID the city id for the query
     * @param viewMetric true if metric units are being used, false if imperial units are used
     */
    public WeatherAPI(int cityID, boolean viewMetric) {
        this.cityID = cityID;
        this.viewMetric = viewMetric;
    }

    /**
     * Returns a JSONObject from an API call for the short-term forecast of a provided city.
     * Returns null if invalid call made.
     * The JSONObject contains weather data in 3 hour increments for 5 days.
     *
     * @return if valid call, the JSONObject for short-term forecast, null otherwise
     */
    public JSONObject getShortTerm() {
    	
    	String address = BASE + "/forecast?id=" + cityID + APIKEY + JSON;
    	/* Adds the units parameter to the url address */
        if (viewMetric) {
            address = address + METRIC;
        } else {
            address = address + IMPERIAL;
        }
        
        /* Performs an API call and fetches JSONObject */
        return getJSON(address, 0);
}
    

    /**
     * Returns a JSONObject from a API call for the long-term forecast of a provided city.
     * The JSONObject contains daily weather data for the following 5 days.
     *
     * @return if valid call, the JSONObject for long-term forecast, null otherwise
     */
    public JSONObject getLongTerm() {

		String address = BASE + "/forecast/daily?id=" + cityID + APIKEY + JSON + "&cnt=7";
        
		/* Adds the units parameter to the url address */
        if (viewMetric) {
            address = address + METRIC;
        } else {
            address = address + IMPERIAL;
        }
        System.out.println(address);
        /* Perform API call and fetches JSONObject */
        return getJSON(address, 0);
    }

    /**
     * Returns a JSONObject from a API call for current weather of a provided city.
     * The JSONObject contains current weather data.
     *
     * @return if valid call, the JSONObject for current weather, null otherwise
     */
    public JSONObject getLocal() {
    	
    		String address = BASE + "/weather?id=" + cityID + APIKEY + JSON;
	         
    		/* Adds the units parameter to the url address */
	        if (viewMetric) {
	            address = address + METRIC;
	        } else {
	            address = address + IMPERIAL;
	        }

        /* Performs API call and fetches JSONObject */
        return getJSON(address, 0);
    }
    
    
    public static JSONObject getLikeCities(String cityName) {
    
    	return getJSON(BASE + "find?q=" + cityName + "&type=like" + APIKEY + JSON, 1);
    }



    /**
     * Helper method that performs the API call for long-term or short-term forecast or current weather.
     *
     * @param address the base url address for the API call (NOT including parameter for units)
     * @param requestType 0 for long-term, short-term and current weather requests, 1 for like request
     * @return if valid call, the JSONObject for current weather, null otherwise
     */
    private static JSONObject getJSON(String address, int requestType) {
    	
 
        JSONObject jsonObj = new JSONObject();
        
        Scanner urlReader = null;
        InputStream urlStream = null; 
        String queryResult = "";
        
        try {
             /* Does a query and saves query result as a string */
            urlStream = new URL(address).openStream();
            urlReader = new Scanner(urlStream);
            while (urlReader.hasNext()) {
                queryResult += urlReader.nextLine();
            }
            
        } catch (IOException ioEx) {
            ioEx.printStackTrace();	
            return null;
        } finally {
        	if (urlReader != null) {
        		urlReader.close();
        	}
        }
        
       try {
	        /* Builds the JSON Object from query result */
	        jsonObj = new JSONObject(queryResult);
	        
	        if (requestType == 0) {
	            /* Checks if API call was valid */
	            if (jsonObj.get("cod").equals("404")) {
	                return null;
	            }
	        } else if (requestType == 1) {
	        	/* Location not found and JSONObj has the message "like" */
	        	if (jsonObj.get("message").equals("like")) {
	            	if (jsonObj.get("count").equals(0)) {
	            		return null;
	            	}
	            /* Location not found and JSONObj has the message "" */
	        	} else if (jsonObj.get("message").equals("")) {
	        		return null;
	        	}
	        }
       } catch (JSONException jsonEx) {
    	   jsonEx.printStackTrace();
       }
       

        /* Return the JSON object */
        return jsonObj;
    }
    
    



}
