import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
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
    private final String BASE = "http://api.openweathermap.org/data/2.5/";

    /* Metric units parameter for url string */
    private final String METRIC = "&units=metric";

    /* Imperial units parameter for url string */
    private final String IMPERIAL = "&units=imperial";

    /* API key parameter for url string */
    private final String APIKEY = "&APPID=bb4d737a642c4e98afff6652ea5e0d17";

    /* JSON parameter for url string */
    private final String JSON = "&mode=json";

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

        /* Performs an API call and fetches JSONObject */
        return this.getWeather(BASE + "/forecast?id=" + cityID + APIKEY + JSON);
}

    /**
     * Returns a JSONObject from a API call for the long-term forecast of a provided city.
     * The JSONObject contains daily weather data for the following 5 days.
     *
     * @return if valid call, the JSONObject for long-term forecast, null otherwise
     */
    public JSONObject getLongTerm() {

        /* Perform API call and fetches JSONObject */
        return this.getWeather(BASE + "/forecast/daily?id=" + cityID + APIKEY + JSON + "&cnt=5");
    }

    /**
     * Returns a JSONObject from a API call for current weather of a provided city.
     * The JSONObject contains current weather data.
     *
     * @return if valid call, the JSONObject for current weather, null otherwise
     */
    public JSONObject getLocal() {

        /* Performs API call and fetches JSONObject */
        return this.getWeather(BASE + "/weather?id=" + cityID + APIKEY + JSON);
    }

    /**
     * Helper method that performs the API call for long-term or short-term forecast or current weather.
     *
     * @param address the base url address for the API call (NOT including parameter for units)
     * @return if valid call, the JSONObject for current weather, null otherwise
     */
    private JSONObject getWeather(String address) {

         /* Adds the units parameter to the url address */
        if (viewMetric) {
            address = address + METRIC;
        } else {
            address = address + IMPERIAL;
        }

        JSONObject jsonObj = new JSONObject();
        try {
             /* Does a query and saves query result as a string */
            String queryResult = "";
            Scanner urlReader = new Scanner(new URL(address).openStream());
            while (urlReader.hasNext()) {
                queryResult += urlReader.nextLine();
            }
            urlReader.close();

            /* Builds the JSON Object from query result */
            jsonObj = new JSONObject(queryResult);

            /* Checks if API call was valid */
            if (jsonObj.get("cod").equals("404")) {
                return null;
            }

        } catch (MalformedURLException malEx) {
            malEx.printStackTrace();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (JSONException jsonEx) {
            jsonEx.printStackTrace();
        }

        /* Return the JSON object */
        return jsonObj;
    }


    public static void main(String[] args) {
        WeatherAPI weather = new WeatherAPI(1851632, false);

//        System.out.println(weather.getLocal());
        System.out.println(weather.getShortTerm());
//        System.out.println(weather.getLongTerm());
    }


}
