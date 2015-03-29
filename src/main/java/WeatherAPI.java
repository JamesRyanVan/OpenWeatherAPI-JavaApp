package main.java;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Random;
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

    /* API keys for url string */
    private final static String[] APIKEY_LIST = {"&APPID=bb4d737a642c4e98afff6652ea5e0d17", 
    	"&APPID=908c95bd812f38ba32bea1ca5d314d4a", "&APPID=cbc953a49ef614915e55f21d9adb292b", 
    	"&APPID=a580c44950b8e76dfc649da83054c464"};


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
     * The JSONObject contains weather data in 3 hour increments for the next 24 hours.
     *
     * @throws JSONException if an error occurred with the fetched JSONObject
     * @throws IOException if an input or output exception occurred (i.e. could not reach
     * 					   server)
     * @return the JSONObject for short-term forecast of a provided city
     */
    public JSONObject getShortTerm() throws JSONException, IOException {
    		
    	/* Picks an API key at random */ 
		Random rng = new Random();
		String apiKey = APIKEY_LIST[rng.nextInt(APIKEY_LIST.length)];
    	
    	
    	/* Creates and adds the units parameter to the url address */
		String address = BASE + "forecast?id=" + cityID + apiKey + JSON;
		if (viewMetric) {
            address = address + METRIC;
        } else {
            address = address + IMPERIAL;
        }
        
        /* Performs an API call and fetches JSONObject */
        JSONObject jsonObj = null;
	    jsonObj  = getJSON(address);
        return jsonObj;
}
    

    /**
     * Returns a JSONObject from a API call for the long-term forecast of a provided city.
     * The JSONObject contains daily weather data for the following 5 days.
     *
     * @throws JSONException if an error occurred with the fetched JSONObject
     * @throws IOException if an input or output exception occurred (i.e. could not reach
     * 					   server)
     * @return the JSONObject for long-term forecast of a provided city
     */
    public JSONObject getLongTerm() throws IOException, JSONException {
    	
    	/* Picks an API key at random */ 
    	Random rng = new Random();
		String apiKey = APIKEY_LIST[rng.nextInt(APIKEY_LIST.length)];
		
		
		/* Creates and adds the units parameter to the url address */
		String address = BASE + "forecast/daily?id=" + cityID + apiKey + JSON + "&cnt=7";
        if (viewMetric) {
            address = address + METRIC;
        } else {
            address = address + IMPERIAL;
        }
        
        /* Perform API call and fetches JSONObject */
        JSONObject jsonObj = null;
    	jsonObj = getJSON(address);
        return jsonObj;
    }

    /**
     * Returns a JSONObject from a API call for current weather of a provided city.
     * The JSONObject contains current weather data.
     * 
     * @throws JSONException if an error occurred with the fetched JSONObject
     * @throws IOException if an input or output exception occurred (i.e. could not reach
     *					   server)
     * @return the JSONObject for current weather of a provided city
     */
    public JSONObject getLocal() throws IOException, JSONException {
    	
    	/* Picks an API key at random */ 
    	Random rng = new Random();
		String apiKey = APIKEY_LIST[rng.nextInt(APIKEY_LIST.length)];
		
    	String address = BASE + "weather?id=" + cityID + apiKey + JSON;
        
		/* Adds the units parameter to the url address */
        if (viewMetric) {
            address = address + METRIC;
        } else {
            address = address + IMPERIAL;
        }
        
    	JSONObject jsonObj = null;
    
	    jsonObj = getJSON(address);
	  

    	return jsonObj;
    
    }
    
	/**
	 *  Returns a JSONObject from a API call using a "like" search in OpenWeatherMap of a provided city.
	 * The JSONObject contains the like search data.
	 *
	 * @param String desired city name to search
	 * @throws JSONException if an error occurred with the fetched JSONObject
	 * @throws IOException if an input or output exception occurred (i.e. could not reach
	 *					  server)
	 * @throws LocationNotFoundException if there are no matches for the like search
	 * 
	 * @return the JSONObject for current weather of a provided city
	 */
	    public static JSONObject getLikeCities(String cityName) throws IOException, LocationNotFoundException,
	    JSONException {
	    
	    	/* Picks an API key at random */ 
	    	Random rng = new Random();
			String apiKey = APIKEY_LIST[rng.nextInt(APIKEY_LIST.length)];
			
			/* Get JSON object from a "like" search */
	    	JSONObject jsonObj = getJSON(BASE + "find?q=" + cityName + "&type=like" + apiKey + JSON);
	    	
	
	    	/* Cases where location was not found: throw an LocationNotFound exception */
	    	if (jsonObj.get("message").equals("like")) {
	        	if (jsonObj.get("count").equals(0)) {
	        		throw new LocationNotFoundException("No location found.");
	        	}
	    	} else if (jsonObj.get("message").equals("")) {
	    		throw new LocationNotFoundException("No location found.");
	    	}
	    	
	    	return jsonObj;
	    }
	


    /**
     * Helper method that performs the API call for long-term or short-term forecast or current weather.
     *
     * @param address the base url address for the API call (NOT including parameter for units)
     * @param requestType 0 for long-term, short-term and current weather requests, 1 for like request
     * 
     * @throws JSONException if an error occurred with the fetched JSONObject
	 * @throws IOException if an input or output exception occurred (i.e. could not reach
	 *					  server)
     * 
     * @return the JSONObject from the API call
     */
    
    private static JSONObject getJSON(String address) throws JSONException,
    IOException {
  
        //* Initialize objects needed for establishing connection/creating stream *//
    	JSONObject jsonObj = new JSONObject();
    	 
        BufferedReader urlReader = null;
        HttpURLConnection connection = null;
        InputStream urlStream = null; 
        int http_status;
      
        StringBuilder queryResult = new StringBuilder();
        
        try {
        	///* Opens connection and get the input stream *////
            
        	connection = (HttpURLConnection) new URL(address).openConnection();
            /* Set read/connect timeout limits */
            connection.setReadTimeout(5000); /* 5 secs to start reading data */
            connection.setConnectTimeout(7000); /* 7 secs to connect to server */

            urlStream = connection.getInputStream();
            
            /* Gets http status code for connection */
            http_status = connection.getResponseCode();
    
        
            /* Connection was successful (status was 200) */
            if (http_status == HttpURLConnection.HTTP_OK) { 
            	try {
            		
            		/* Read the input stream and build the query result as a string */
            		urlReader = new BufferedReader(new InputStreamReader(urlStream));
	            	String line = urlReader.readLine();
	            	while (line != null) {
	            		queryResult.append(line);
	            		line = urlReader.readLine();
            		}
	             	
	         	    /* Builds the JSON Object from query result */
	         	    jsonObj = new JSONObject(queryResult.toString());
         	       
         	    /* Catch and rethrow exceptions while reading stream / building string */
                } catch (JSONException jsonEx) {
             	   throw new JSONException("JSONException while reading stream");
                } catch (IOException ioEx) {
             	   throw new IOException("IOException while reading stream"); 
                } finally {
             	
             		   /* Close stream, connection and readers */
             		   if (urlReader != null) {
             			   urlReader.close();
             		   }
   
             		   urlStream.close();
             	
             		   connection.disconnect();
                }
            
            /* Connection was not successful (status was not 200) */
            } else { 
           	
            	/* Close the stream and connection */
            	urlStream.close();
            	connection.disconnect();
            
            	/* Throw an IOException */
            	throw new IOException("Error connecting to OpenWeatherMap");
            }
        
        /* Catch exceptions while opening connection/getting input stream */
        } catch (IOException ioEx) {
 		   	throw new IOException("Error occurred reaching the server"); 
        } finally {
        	
        	/* Close the stream and connection */
	     	if (urlStream != null) {
	    	 	urlStream.close();
	    	}
	    	if (connection != null) {
	    		connection.disconnect();
	    	}
        }
    
        /* Return the JSON object */
        return jsonObj;
    }
    



}
