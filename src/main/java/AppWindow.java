import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JRadioButtonMenuItem; 
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.json.JSONObject;

/**
 * <h2>The AppWindow Class</h2>
 * <br>
 * This class is the main GUI and interacts with all other classes
 * 
 * @author	Group10 (James V, Li G, Curtis V, Alec W, Jeremy A)
 * @version	1.0
 * @since 	2015-02-01
 *  *
 */
public class AppWindow extends JFrame{
	
	 // Main JFrame used by window
	private JFrame mainFrame;
	
	// Global Control elements so we can update them
	private JComboBox locationCombo;
	private JTabbedPane tabbedPane;
	private JTextPane localText;
	private JTextPane shortText;
	private JTextPane longText;
	
	// Location Storage
	private String currentLocation = null;
	private int currentLocationID = 0;
	
	// Class Creation
	private WeatherAPI weather;
	private List<String> locationList;
	private Settings settings;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor initiates default window settings in the
	 * initUI() method
	 */
	public AppWindow() {
		this.initSettings();
		this.initUI(); // Default Constructor initiates default settings
	}
	
	/**
	 * Method that reads user settings
	 */
	private void initSettings() {
		settings = new Settings(true, true, true, true, true, true, true, 0);
	}
	
	/**
	 * Method that generates the main window and its properties
	 */
	private void initUI() {
		
		try { // Try to set window theme to Windows7
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			} catch (Exception e) {
			  }
		
		try { // If not using a Microsoft OS then use default theme
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}	catch (Exception e) {
			
		}
		
		locationList = new ArrayList<String>(); // Start the location list
		locationList.add("Add Location..");	// Add the default option
		locationCombo = new JComboBox(locationList.toArray(new String[locationList.size()])); // Make a new combo box
		mainFrame = new JFrame(); // Create the main frame
		
		// Window Properties
		
		mainFrame.setTitle("OpenWeather Application v1.0"); // Window Title
		this.setSize(1015,525); // Window Size
		this.setPreferredSize(new Dimension(1015,525)); // Window size fix for linux
		this.setMinimumSize(new Dimension(1015,525)); // Sets the minimum size the window can have
		this.setMaximumSize(new Dimension(1015,525)); // Sets the maximum size the window can have
		this.setLocationRelativeTo(null); // Sets default screen location 
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Menu Bar
		
		this.setJMenuBar(this.menuBar());
				
		// Tabbed Content
		
		this.tabbedContent();
		
	}
	
	/**
	 * The JMenuBar generates the top menu bar. This method also includes
	 * the add location combo box feature.
	 * @return MenuBar
	 */
	private JMenuBar menuBar() {
		
		JMenuBar menubar = new JMenuBar(); // Creates menu bar
		
		JMenu menuFile = new JMenu("File"); // Creates file button
		menuFile.setMnemonic(KeyEvent.VK_F);
		
		menubar.add(menuFile);
		
			// Locations menu list
		
			JMenu menuFileLocation = new JMenu("Locations");
			menuFileLocation.setMnemonic(KeyEvent.VK_S);
			menuFileLocation.setToolTipText("Locations");
			
			menuFile.add(menuFileLocation);
			
				// New Location sub menu button
			
				JMenuItem menuFileLocationNew = new JMenuItem("New");
				menuFileLocationNew.setToolTipText("New Location");
				menuFileLocationNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
				menuFileLocationNew.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							newLocation();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				menuFileLocation.add(menuFileLocationNew);
				
		
			// Refresh menu button
		
			JMenuItem menuFileRefresh = new JMenuItem("Refresh");
			menuFileRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
			menuFileRefresh.setMnemonic(KeyEvent.VK_E);
			menuFileRefresh.setToolTipText("Refresh current data");
			menuFileRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			
			});
			
			menuFile.add(menuFileRefresh);
		
			// Exit menu button
		
			JMenuItem menuFileExit = new JMenuItem("Exit");
			menuFileExit.setMnemonic(KeyEvent.VK_E);
			menuFileExit.setToolTipText("Exit application");
			menuFileExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
						System.exit(0); 
				}
			});
			
			menuFile.addSeparator();
			menuFile.add(menuFileExit);
			
		JMenu menuView = new JMenu("View"); // Creates view button
		menuView.setMnemonic(KeyEvent.VK_F);
		
		menubar.add(menuView);
		
			// View temperature
		
			JCheckBoxMenuItem menuViewTemp = new JCheckBoxMenuItem("Temperature");
			menuViewTemp.setMnemonic(KeyEvent.VK_E);
			menuViewTemp.setToolTipText("View Temperature");
			menuViewTemp.setSelected(true);
			menuViewTemp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
						
				}
			});
	
			menuView.add(menuViewTemp);
			
			// View Wind Speed
			
			JCheckBoxMenuItem menuViewWind = new JCheckBoxMenuItem("Wind Speed");
			menuViewWind.setMnemonic(KeyEvent.VK_E);
			menuViewWind.setToolTipText("View WindSpeed");
			menuViewWind.setSelected(true);
			menuViewWind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
						
				}
			});
	
			menuView.add(menuViewWind);
			
			// View Sky Condition
			
			JCheckBoxMenuItem menuViewSky = new JCheckBoxMenuItem("Sky Conditions");
			menuViewSky.setMnemonic(KeyEvent.VK_E);
			menuViewSky.setToolTipText("View Sky Conditions");
			menuViewSky.setSelected(true);
			menuViewSky.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				}
			});
				
			menuView.add(menuViewSky);
			
			// View Air Pressure
			
			JCheckBoxMenuItem menuViewAir = new JCheckBoxMenuItem("Air Pressure");
			menuViewAir.setMnemonic(KeyEvent.VK_E);
			menuViewAir.setToolTipText("View Air Pressure");
			menuViewAir.setSelected(true);
			menuViewAir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				}
			});
			
			menuView.add(menuViewAir);
		
			// View Humidity
			
			JCheckBoxMenuItem menuViewHumidity = new JCheckBoxMenuItem("Humidity");
			menuViewHumidity.setMnemonic(KeyEvent.VK_E);
			menuViewHumidity.setToolTipText("View Air Pressure");
			menuViewHumidity.setSelected(true);
			menuViewHumidity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				}
			});
			
			menuView.add(menuViewHumidity);
			
			// View Sunset/Rise
			
			JCheckBoxMenuItem menuViewSun = new JCheckBoxMenuItem("Sunset/Sunrise");
			menuViewSun.setMnemonic(KeyEvent.VK_E);
			menuViewSun.setToolTipText("View Sunset/Sunrise");
			menuViewSun.setSelected(true);
			menuViewSun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				}
			});
			
			menuView.add(menuViewSun);
			
			ButtonGroup unitsGroup = new ButtonGroup(); // Used to group ratio buttons to allow toggle
			
			menuView.addSeparator();
			
			// Metric Ratio Button
			
			JRadioButtonMenuItem menuViewMetric = new JRadioButtonMenuItem("Metric");
			menuViewMetric.setMnemonic(KeyEvent.VK_E);
			menuViewMetric.setToolTipText("Set Metric");
			menuViewMetric.setSelected(true);
			menuViewMetric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				}
			});
			
			unitsGroup.add(menuViewMetric);
			menuView.add(menuViewMetric);
			
			// Imperial Ratio Button
			
			JRadioButtonMenuItem menuViewImperial = new JRadioButtonMenuItem("Imperial");
			menuViewImperial.setMnemonic(KeyEvent.VK_E);
			menuViewImperial.setToolTipText("Set Imperial");
			menuViewImperial.setSelected(false);
			menuViewImperial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				}
			});
			
			unitsGroup.add(menuViewImperial);
			menuView.add(menuViewImperial);
			
			
		JMenu menuOptions = new JMenu("Options"); // Create Options button
		menuOptions.setMnemonic(KeyEvent.VK_F);
		
			JMenuItem menuOptionsHelp = new JMenuItem("Help");
			menuOptionsHelp.setMnemonic(KeyEvent.VK_E);
			menuOptionsHelp.setToolTipText("Help Documentation");
		
			menuOptions.add(menuOptionsHelp);
			
		menubar.add(menuOptions);
		
		// Location Drop Down box
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		menubar.add(panel);
		panel.setLayout(null);
		
		locationComboBox();		
		
		this.getContentPane().setLayout(null);
		
		return menubar;
		
	}
	
	
	/**
	 * Method that creates the combobox to control locations
	 */
	private void locationComboBox() {

		locationCombo.setBounds(829, -1, 181, 23);
		locationCombo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        String s = (String) locationCombo.getSelectedItem();

		        switch (s) {
		            case "Add Location..":
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
		
		this.add(locationCombo);
		
	}
	
	/**
	 * tabbedContent method produces a tabbed area in the main window
	 * which separates different weather data.
	 */
	private void tabbedContent() {
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(1, 0, 1009, 474);
		this.getContentPane().add(tabbedPane);
		
		localText = new JTextPane();
		localText.setText("Please add a city");
		localText.setEditable(false);
		
		shortText = new JTextPane();
		shortText.setText("Please add a city");
		shortText.setEditable(false);
		
		longText = new JTextPane();
		longText.setText("Please add a city");
		longText.setEditable(false);
		
		JTabbedPane tabbedPane_local = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_local.setBackground(SystemColor.menu);
		tabbedPane.addTab("Local", null, localText, null);
		
		JTabbedPane tabbedPane_short = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_short.setBackground(SystemColor.menu);
		tabbedPane.addTab("ShortTerm", null, shortText, null);
		
		JTabbedPane tabbedPane_long = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_long.setBackground(SystemColor.menu);
		tabbedPane.addTab("LongTerm", null, longText, null);
				
	}
	
	/**
	 * Method that takes user input as location and adds it to the combo menu
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void newLocation() throws IOException {
		System.out.println("Adding a new Location");
		String locationPopUp = (String)JOptionPane.showInputDialog(mainFrame, "Enter city name:", "Add new location", JOptionPane.PLAIN_MESSAGE); // Location pop up window
		
		if (locationPopUp == null) {
			System.out.println("No location entered");
		}
		else {
			System.out.println("Location added: " + locationPopUp);
			locationList.add(locationPopUp);	 // Adds location to list
			locationCombo.addItem(locationPopUp); // Adds location to combo box
			locationCombo.setSelectedItem(locationPopUp); // Sets it as the selected item
			currentLocation = locationPopUp; // Sets it to the current locatiopn
			
			getJSON(currentLocation); // Gets the JSON for that location
			
		}
		
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
			
			/////////////////////////// WHERE JSON WRITES TO WINDOW /////////////////////////////////////////
			
			localText.setText(local.toString());
			shortText.setText(shortTerm.toString());
			longText.setText(longTerm.toString());	
			
			// Need to add formatting
			
			
			/////////////////////////////////////////////////////////////////////////////////////////////////
			
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
		    System.out.println(inputStream);
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
	
}
