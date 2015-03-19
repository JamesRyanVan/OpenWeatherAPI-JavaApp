package main.java;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JProgressBar;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.json.JSONObject;

import main.java.Settings;

public class AppWindow {

	static JFrame frmOpenweatherapp;
	
	private JTabbedPane tabbedPane;
	private JPanel panel_local;
	private JPanel panel_short;
	private JPanel panel_long;
	private JComboBox<String> comboBox_location;

	private AddLocationDialog locationDialog;
	
	private WeatherAPI weather;
	private Settings settings;
	
	private List<String> locationList;
	private DefaultComboBoxModel<City> locationModel;
	
	// Location Storage
	private String currentLocation = null;
	private int currentLocationID = 0;

	/** Test 
	private JComboBox<City> comboBox_location;
	*/

	/**
	 * Create the application.
	 */
	public AppWindow() {
		initializeSettings();
		initialize();
	}

	private void initializeSettings() {
		settings = new Settings(true, true, true, true, true, true, true, 0);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try 
	    {
	      UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
	    } 
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	    }		
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frmOpenweatherapp = new JFrame();
		
		frmOpenweatherapp.getContentPane().setBackground(SystemColor.menu);
		frmOpenweatherapp.setFont(new Font("Courier New", Font.PLAIN, 12));
		frmOpenweatherapp.setTitle("OpenWeatherApp");
		frmOpenweatherapp.setBounds(100, 100, 793, 550);
		frmOpenweatherapp.setResizable(false);
		frmOpenweatherapp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (frmOpenweatherapp.getWidth() / 2), middle.y - (frmOpenweatherapp.getHeight() / 2));
		frmOpenweatherapp.setLocation(newLocation);
		
		frmOpenweatherapp.setJMenuBar(menubar());
		
		
	
		locationList = new ArrayList<String>();
		locationList.add("Add Location");
	
		comboBox_location = new JComboBox<String>(locationList.toArray(new String[locationList.size()]));
		
		/* Test
		 locationModel = new DefaultComboBoxModel<City>();
		 locationModel.addElement(new City(0, "", "Add Location")); 
		 comboBox_location = new JComboBox<City>(locationModel);
		*/
	
		
		frmOpenweatherapp.getContentPane().add(locationPanel());
		frmOpenweatherapp.getContentPane().add(tabbedPane());
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(631, 457, 146, 14);
		frmOpenweatherapp.getContentPane().add(progressBar);
		
		JLabel lblProgramstatusnull = new JLabel("program_status_null");
		lblProgramstatusnull.setBounds(12, 454, 138, 14);
		frmOpenweatherapp.getContentPane().add(lblProgramstatusnull);
		
	}
	
	private JMenuBar menubar() {
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnLocations = new JMenu("Locations");
		mnFile.add(mnLocations);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					newLocation();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnLocations.add(mntmNew);
		
		JMenuItem mntmRemove = new JMenuItem("Remove");
		mnLocations.add(mntmRemove);
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mnFile.add(mntmRefresh);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JCheckBoxMenuItem chckbxmntmTemperature = new JCheckBoxMenuItem("Temperature");
		mnView.add(chckbxmntmTemperature);
		
		JCheckBoxMenuItem chckbxmntmWindSpeed = new JCheckBoxMenuItem("Wind Speed");
		mnView.add(chckbxmntmWindSpeed);
		
		JCheckBoxMenuItem chckbxmntmSkyConditions = new JCheckBoxMenuItem("Sky Conditions");
		mnView.add(chckbxmntmSkyConditions);
		
		JCheckBoxMenuItem chckbxmntmAirPressure = new JCheckBoxMenuItem("Air Pressure");
		mnView.add(chckbxmntmAirPressure);
		
		JCheckBoxMenuItem chckbxmntmHumidity = new JCheckBoxMenuItem("Humidity");
		mnView.add(chckbxmntmHumidity);
		
		JCheckBoxMenuItem chckbxmntmSunsetsunrise = new JCheckBoxMenuItem("Sunset/Sunrise");
		mnView.add(chckbxmntmSunsetsunrise);
		
		JSeparator separator = new JSeparator();
		mnView.add(separator);
		
		JRadioButtonMenuItem rdbtnmntmMetric = new JRadioButtonMenuItem("Metric");
		mnView.add(rdbtnmntmMetric);
		
		JRadioButtonMenuItem rdbtnmntmImperial = new JRadioButtonMenuItem("Imperial");
		mnView.add(rdbtnmntmImperial);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelpDoc = new JMenuItem("Help Doc");
		mnHelp.add(mntmHelpDoc);
		frmOpenweatherapp.getContentPane().setLayout(null);
		
		return menuBar;
		
	}
	
	private JPanel locationPanel() {
		
		JPanel panel = new JPanel();
		panel.setBounds(516, -2, 266, 35);
		panel.setBackground(SystemColor.menu);
		
		panel.setLayout(null);
		
		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		try {
		    Image img = ImageIO.read(getClass().getResource("/main/resource/refresh-icon.png"));
		    btnRefresh.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }

		btnRefresh.setBounds(233, 4, 31, 31);
		panel.add(btnRefresh);
		
		comboBox_location.setBounds(31, 6, 198, 27);
		comboBox_location.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		    	String s = (String) comboBox_location.getSelectedItem();
		    	/* Test
		        String s = ((City) comboBox_location.getSelectedItem()).toString();
		        */


		        switch (s) {
		            case "Add Location":
					try {
						newLocation();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		                break;
		            default: 
		            	System.out.println("Selected City");
		            	break;
		        }
		    }
		});
		
		panel.add(comboBox_location);
		
		
		return panel;
	}
	
	private void newLocation() throws IOException {
		
		locationDialog = new AddLocationDialog(frmOpenweatherapp);
		locationDialog.setDialogListener(new DialogListener() {
			@Override
			public void dialogEventOccurred(DialogEvent event) {
				// If user picks a new location....
				if (event != null) {
					////// Do something - Ryan please change this to desired behaviour //////
//					currentLocationID = event.getCityID();
//					currentLocation = event.getCityName() + ", " + event.getCountryName();

					/* Test
					locationModel.addElement(event.getCityObj());
					*/
			
				}
			}
		});
	}
	
	/**
	 * Method that gets the json from WeatherAPI.
	 * @param location
	 * @throws IOException
	 */
	private void getJSON(String location) throws IOException {
		
		int cityID = findCityID(location); // Takes the city string and finds the id (int)
		
		if (cityID != 0) {
			
			weather = new WeatherAPI(cityID, settings.viewMetricUnits()); // Gets the JSON data from WeatherAPI
			
			JSONObject local = weather.getLocal();
			JSONObject shortTerm = weather.getShortTerm();
			JSONObject longTerm = weather.getLongTerm();
			
		}
		else {
			System.out.println("Error: City not found");
		}
		
	}
	
	/**
	 * Method that is responsible for searching through the city list to find the appropriate cityID
	 * @param currentLocation
	 * @return
	 * @throws IOException
	 */
	
	private int findCityID(String currentLocation) throws IOException {
		
		java.io.InputStream inputStream = null; // Starts new stream and scanner
		Scanner sc = null;
		try {
			getClass().getClassLoader();
		    inputStream = ClassLoader.getSystemResourceAsStream("city.list"); // File containing the list
		    sc = new Scanner(inputStream);
		    while (sc.hasNextLine()) { // While lines exist
		        String line = sc.nextLine();
		        
		        if(line.toLowerCase().contains(currentLocation.toLowerCase())) { // If the line contains the city name 
		        	
		        	System.out.println(line);
		        	
		        	String[] lineArray = line.split("\\s+"); // Split the line up where spaces are
		        	currentLocationID = Integer.parseInt(lineArray[0]); // Take the ID number
		        	
		        	System.out.println(currentLocationID);
		        	
		        	return currentLocationID; // Return that number
		        	
		        }
		        
		    }
		    if (sc.ioException() != null) { // If any error is caught then 0 is returned
		        throw sc.ioException();
		    }
		} catch (FileNotFoundException e){
			JOptionPane.showMessageDialog (null, "Error", "City list file not found", JOptionPane.ERROR_MESSAGE);
		}
		
		finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		return 0;
		
	}
	
	private JTabbedPane tabbedPane() {
		
		panel_local = new JPanel();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 11, 765, 440);
		
		if (currentLocation == null) {
			tabbedPane.setEnabled(false);
		}
		
		panel_local.setBackground(Color.WHITE);
		tabbedPane.addTab("Local", null, panel_local, null);
		panel_local.setLayout(null);
		
		if (currentLocation == null) {
			panel_blank();
		} else {
			panel_local_content();
		}
		
		
		panel_short = new JPanel();
		panel_short.setBackground(Color.WHITE);
		tabbedPane.addTab("ShortTerm", null, panel_short, null);
		
		panel_short_content();
		
		panel_long = new JPanel();
		panel_long.setBackground(Color.WHITE);
		tabbedPane.addTab("LongTerm", null, panel_long, null);
		
		panel_long_content();
		
		return tabbedPane;
		
	}
	
	private void panel_blank() {
		
		JLabel lblHelloToStart = new JLabel("Hello! To start add a location");
		lblHelloToStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHelloToStart.setBounds(225, 11, 375, 58);
		panel_local.add(lblHelloToStart);
		
	}
	
	private void panel_local_content() {
		
		JLabel lblTemperature = new JLabel("00");
		lblTemperature.setFont(new Font("Tahoma", Font.PLAIN, 45));
		lblTemperature.setBounds(40, 56, 77, 44);
		panel_local.add(lblTemperature);
		
		JLabel lblWeatherReportFor = new JLabel("null, null");
		lblWeatherReportFor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWeatherReportFor.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblWeatherReportFor.setBounds(583, 11, 161, 33);
		panel_local.add(lblWeatherReportFor);
		
		JLabel lblSkycondition = new JLabel("SkyCondition");
		lblSkycondition.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSkycondition.setBounds(40, 122, 150, 14);
		panel_local.add(lblSkycondition);
		
		JLabel lblWindspeed = new JLabel("WindSpeed");
		lblWindspeed.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWindspeed.setBounds(40, 146, 150, 33);
		panel_local.add(lblWindspeed);
		
		JLabel lblWindDir = new JLabel("Wind Direction");
		lblWindDir.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWindDir.setBounds(40, 185, 122, 25);
		panel_local.add(lblWindDir);
		
		JLabel lblAirPressure = new JLabel("Air Pressure");
		lblAirPressure.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAirPressure.setBounds(40, 217, 113, 25);
		panel_local.add(lblAirPressure);
		
		JLabel lblSunrise = new JLabel("SunRise");
		lblSunrise.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSunrise.setBounds(40, 303, 110, 33);
		panel_local.add(lblSunrise);
		
		JLabel lblSunset = new JLabel("SunSet");
		lblSunset.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSunset.setBounds(40, 338, 93, 33);
		panel_local.add(lblSunset);
		
		JLabel lblHumidity = new JLabel("Humidity");
		lblHumidity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHumidity.setBounds(40, 251, 122, 25);
		panel_local.add(lblHumidity);
		
		JLabel lblLastUpdate = new JLabel("Last Updated:");
		lblLastUpdate.setBounds(650, 389, 77, 20);
		panel_local.add(lblLastUpdate);
		
		JLabel lblUpdatedtime = new JLabel("null");
		lblUpdatedtime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUpdatedtime.setBounds(724, 392, 27, 14);
		panel_local.add(lblUpdatedtime);
		
		JLabel skycondvalue = new JLabel("null");
		skycondvalue.setBounds(200, 122, 46, 14);
		panel_local.add(skycondvalue);
		
		JLabel windspeedvalue = new JLabel("null");
		windspeedvalue.setBounds(200, 157, 46, 14);
		panel_local.add(windspeedvalue);
		
		JLabel windDirvalue = new JLabel("null");
		windDirvalue.setBounds(200, 192, 46, 14);
		panel_local.add(windDirvalue);
		
		JLabel airpressurevalue = new JLabel("null");
		airpressurevalue.setBounds(200, 224, 46, 14);
		panel_local.add(airpressurevalue);
		
		JLabel humidityvalue = new JLabel("null");
		humidityvalue.setBounds(200, 257, 46, 14);
		panel_local.add(humidityvalue);
		
		JLabel sinriseValue = new JLabel("null");
		sinriseValue.setBounds(200, 314, 46, 14);
		panel_local.add(sinriseValue);
		
		JLabel Sunsetvalue = new JLabel("null");
		Sunsetvalue.setBounds(200, 349, 46, 14);
		panel_local.add(Sunsetvalue);
		
		JLabel lblDailyHigh = new JLabel("High:");
		lblDailyHigh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDailyHigh.setBounds(198, 25, 60, 26);
		panel_local.add(lblDailyHigh);
		
		JLabel lblCurrent = new JLabel("Current:");
		lblCurrent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCurrent.setBounds(40, 24, 85, 28);
		panel_local.add(lblCurrent);
		
		JLabel lblLow = new JLabel("Low:");
		lblLow.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLow.setBounds(324, 31, 46, 14);
		panel_local.add(lblLow);
		
		JLabel label = new JLabel("00");
		label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label.setBounds(197, 61, 58, 33);
		panel_local.add(label);
		
		JLabel label_1 = new JLabel("00");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label_1.setBounds(323, 61, 60, 32);
		panel_local.add(label_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(389, 122, 355, 249);
		panel_local.add(panel_1);
		
		JLabel location_image = new JLabel("");
		location_image.setBackground(Color.GRAY);
		location_image.setIcon(null);
		panel_1.add(location_image);
		
		JLabel lblMap = new JLabel("Map:");
		lblMap.setBounds(389, 97, 46, 14);
		panel_local.add(lblMap);
		
	}
	
	private void panel_short_content() {
		
	}
	
	private void panel_long_content() {
		
	}
	
}
