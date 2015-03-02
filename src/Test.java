
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {
	
	public static void main(String [] args) throws JSONException, IOException{
		WeatherAPI a = new WeatherAPI(2643743,true);
		JSONObject apiObj = a.getLocal();
		Local l =  new Local(apiObj);
		System.out.println("THE LOCAL FORECAST IS: ");
		System.out.println("Time of data collection is: "+l.getUpdateTimeString()+"\nThe weather conditions are:"+l.getSkyCondition());
		System.out.println("The current temperature is: "+l.getTemp()+"\nThe maximum temp is: "+l.getTempMax()+"\nThe minimum temp is: "+l.getTempMin());
		System.out.println("The time of sunset is: "+l.getSunsetString()+"\nThe time of sunrise is: "+l.getSunriseString());
		
		System.out.println("\nTHE SHORT TERM FORECAST IS: ");
		ShortTerm st = new ShortTerm(a.getShortTerm());
		for (int i=0; i<8; i++){
			String[] t = st.getDateArray();
			double[] d = st.getTemps();
			String[] sc = st.getSkyConditions();
			double [] p = st.getPrecips();
		System.out.println(t[i]+"\nThe temp is: "+d[i]+" degrees.\nThe Sky conditions are: "+sc[i]+".\nThe expected precipitation is: "+p[i]+"\n");
		}
	}
}
	
