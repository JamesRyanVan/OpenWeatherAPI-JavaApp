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

    /* API key parameter for url string */
    private final static String[] APIKEY_LIST = {"&APPID=bb4d737a642c4e98afff6652ea5e0d17", 
    	"&APPID=908c95bd812f38ba32bea1ca5d314d4a", "&APPID=cbc953a49ef614915e55f21d9adb292b", 
    	"&APPID=a580c44950b8e76dfc649da83054c464"};
    
//    private final static String[] APIKEY_LIST = {"&APPID=bb4d737a642c4e98afff6652ea5e0d17"};

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
    public JSONObject getShortTerm() throws JSONException, IOException {
    		
		Random rng = new Random();
		String apiKey = APIKEY_LIST[rng.nextInt(APIKEY_LIST.length)];
    	String address = BASE + "forecast?id=" + cityID + apiKey + JSON;
    	
    	/* Adds the units parameter to the url address */
        if (viewMetric) {
            address = address + METRIC;
        } else {
            address = address + IMPERIAL;
        }
        
        JSONObject jsonObj = null;
	   
	    jsonObj  = getJSON(address);
    	
        
        /* Performs an API call and fetches JSONObject */
        return jsonObj;
}
    

    /**
     * Returns a JSONObject from a API call for the long-term forecast of a provided city.
     * The JSONObject contains daily weather data for the following 5 days.
     *
     * @return if valid call, the JSONObject for long-term forecast, null otherwise
     */
    public JSONObject getLongTerm() throws IOException, JSONException {
    	
    	Random rng = new Random();
		String apiKey = APIKEY_LIST[rng.nextInt(APIKEY_LIST.length)];
		
		String address = BASE + "forecast/daily?id=" + cityID + apiKey + JSON + "&cnt=7";
		/* Adds the units parameter to the url address */
        if (viewMetric) {
            address = address + METRIC;
        } else {
            address = address + IMPERIAL;
        }
        
        JSONObject jsonObj = null;
		
    	jsonObj = getJSON(address);
    	
        /* Perform API call and fetches JSONObject */
        return jsonObj;
    }

    /**
     * Returns a JSONObject from a API call for current weather of a provided city.
     * The JSONObject contains current weather data.
     *
     * @return if valid call, the JSONObject for current weather, null otherwise
     */
    public JSONObject getLocal() throws IOException, JSONException {
    	
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
    
    public static JSONObject getLikeCities(String cityName) throws IOException, LocationNotFoundException,
    JSONException {
    
    	Random rng = new Random();
		String apiKey = APIKEY_LIST[rng.nextInt(APIKEY_LIST.length)];
		
    	JSONObject jsonObj = getJSON(BASE + "find?q=" + cityName + "&type=like" + apiKey + JSON);
    	

    	/* Location not found and JSONObj has the message "like" */
    	if (jsonObj.get("message").equals("like")) {
        	if (jsonObj.get("count").equals(0)) {
        		throw new LocationNotFoundException("No location found.");
        	}
        /* Location not found and JSONObj has the message "" */
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
     * @return if valid call, the JSONObject for current weather, null otherwise
     */
//    private static JSONObject getJSON(String address, int requestType) {
//    	
// 
//        JSONObject jsonObj = new JSONObject();
//        
//        Scanner urlReader = null;
//        InputStream urlStream = null; 
//        String queryResult = "";
//        
//        try {
//             /* Does a query and saves query result as a string */
//            urlStream = new URL(address).openStream();
//            urlReader = new Scanner(urlStream);
//            while (urlReader.hasNext()) {
//                queryResult += urlReader.nextLine();
//            }
//            
//        } catch (IOException ioEx) {
//            ioEx.printStackTrace();	
//            return null;
//        } finally {
//        	if (urlReader != null) {
//        		urlReader.close();
//        	}
//        }
//        
//       try {
//	        /* Builds the JSON Object from query result */
//	        jsonObj = new JSONObject(queryResult);
//	        
//	        if (requestType == 0) {
//	            /* Checks if API call was valid */
//	            if (jsonObj.get("cod").equals("404")) {
//	                return null;
//	            }
//	        } else if (requestType == 1) {
//	        	/* Location not found and JSONObj has the message "like" */
//	        	if (jsonObj.get("message").equals("like")) {
//	            	if (jsonObj.get("count").equals(0)) {
//	            		return null;
//	            	}
//	            /* Location not found and JSONObj has the message "" */
//	        	} else if (jsonObj.get("message").equals("")) {
//	        		return null;
//	        	}
//	        }
//       } catch (JSONException jsonEx) {
//    	   jsonEx.printStackTrace();
//       }
//       
//
//        /* Return the JSON object */
//        return jsonObj;
//    }
    
    private static JSONObject getJSON(String address) throws JSONException,
    IOException {
    	 
        JSONObject jsonObj = new JSONObject();
        
        // Set up
        BufferedReader urlReader = null;
        HttpURLConnection connection = null;
        
        InputStream urlStream = null; 
        int http_status;
        StringBuilder queryResult = new StringBuilder();
        
        try {
        	// Open a connection and get the input stream
            connection = (HttpURLConnection) new URL(address).openConnection();
            connection.setReadTimeout(5000); // 5 secs to start reading data or timeout
            connection.setConnectTimeout(7000); // 7 secs to connect to server or timeout
          
            urlStream = connection.getInputStream();
            
            // Check response code, if not successful (code 200)
            http_status = connection.getResponseCode();
    
            System.out.println(http_status);
            
            if (http_status == HttpURLConnection.HTTP_OK) { // success = 200
            	try {
             	   urlReader = new BufferedReader(new InputStreamReader(urlStream));
            	   String line = urlReader.readLine();
             	   
                   while (line != null) {
                     queryResult.append(line);
                      line = urlReader.readLine();
                    }
             	   
             	
         	        /* Builds the JSON Object from query result */
         	        jsonObj = new JSONObject(queryResult.toString());
         	        
//         	       if (requestType == 0) {
//         	            /* Checks if API call was valid */
//         	            if (jsonObj.get("cod").equals("404")) {
//         	                throw new LocationNotFoundException("No location found.");
//         	            }
//         	       } 
         	       
                } catch (JSONException jsonEx) {
             	   throw new JSONException("JSONException in WeatherAPI");
                } catch (IOException ioEx) {
         		   System.out.println("IOException while building string");
         		   ioEx.printStackTrace();
             	   throw new IOException(); 
                } finally {
             	   try {
             		   urlReader.close();
             		   urlStream.close();
             	   } catch (IOException e) {
             		   System.out.println("IOException in finally");
             		   e.printStackTrace();
             		   throw new IOException();
             	   }
             	 
             	   connection.disconnect();
                }
                
            } else { // connection not successful (not 200)
           	
            	urlStream.close();
            	connection.disconnect();
            	
            	throw new IOException("Error connecting to OpenWeatherMap");
            }
      
        } catch (IOException ioEx) {
        	if (urlStream != null) {
        	 	urlStream.close();
        	}
        	if (connection != null) {
        		connection.disconnect();
        	}
  
 		   	throw new IOException("IOException while opening connection");
        } 
    
        

        /* Return the JSON object */
        return jsonObj;
    }
    



}
