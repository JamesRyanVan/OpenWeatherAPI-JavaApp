package main.java;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.ImageIcon;

/**
 * A class to get an image icon of the current weather location
 * @author	Group10 (James V, Li G, Curtis V, Alec W, Jeremy A)
 * @version	1.0
 * @since 	March 23, 2015
 */
public class Map {
	/* The Map Created by the constructor*/
	private ImageIcon map;
	
	/**
	 * Constructor to create a Map object.
	 * An Image of the map from the Google Static Maps API.
	 * This map image includes a marker with the icon of the current weather conditions.
	 * @param latitude of the location of the map
	 * @param longtitude of the location of the map
	 * @param icon code for the weather coditions at the location of the map
	 */
	public Map(String latitude, String longtitude,String icon ){
			/* Try to call the Google Static Maps api */
	        try {
	        	/*Build the URL using the Long and Lat of the location, add the weather conditions icon marker*/
	        	String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center="+URLEncoder.encode(latitude, "UTF-8")+","+URLEncoder.encode(longtitude, "UTF-8")+"&maptype=terrain&zoom=10&size=355x249&markers=icon:http://openweathermap.org/img/w/"+URLEncoder.encode(icon, "UTF-8")+".png|"+URLEncoder.encode(latitude, "UTF-8")+","+URLEncoder.encode(longtitude, "UTF-8");
	            String destinationFile = "image.jpg";
	            URL url = new URL(imageUrl);
	            java.io.InputStream is = url.openStream();
	            /*Save the image as image.jpg to be used as an icon*/ 
	            OutputStream os = new FileOutputStream(destinationFile);

	            byte[] b = new byte[2048];
	            int length;
	            /* Write the image to the local image.jpg file */
	            while ((length = is.read(b)) != -1) {
	                os.write(b, 0, length);
	            }

	            is.close();
	            os.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        /*Catch if the map was not found*/ 
	        } catch (NullPointerException e){
	        	System.out.println("Map Not Found");
	        }
	        /*Create a new Image icon from the image.jpg file*/
	        map = new ImageIcon((new ImageIcon("image.jpg")).getImage());
			}
	
	/**
	 * @return An image of the current city with a marker of the current weather conditions.
	 */
	public ImageIcon getMap(){
		return this.map;
	}
	}

