

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
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
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;

import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.json.JSONException;
import org.json.JSONObject;

public class AppWindow {

	JFrame frmOpenweatherapp;
	
	private ImageIcon mapIcon;
	
	private JTabbedPane tabbedPane;
	private JPanel panel_local;
	private JPanel panel_short;
	private JPanel panel_long;

	private AddLocationDialog locationDialog;
	
	private WeatherAPI weather;
	private Settings settings;
	private Time time;
	
	// Location ComboBox
	private DefaultComboBoxModel<City> locationModel;
	private JComboBox<City> comboBox_location;
	
	private JButton btnRefresh;
	
	// Location Storage
	private City currentLocation = null; // Active location
	
	// Status Label
	public JLabel programStatus;
	
	// Panel Labels
	private JLabel lblHelloToStart = new JLabel();
	
	private JLabel temperature = new JLabel();
	private JLabel temp_max = new JLabel();
	private JLabel temp_min = new JLabel();
	private JLabel lblUpdatedtime = new JLabel();
	private JLabel locationName = new JLabel();
	private JLabel skycondvalue = new JLabel();
	private JLabel windspeedvalue = new JLabel();
	private JLabel windDirvalue = new JLabel();
	private JLabel airpressurevalue = new JLabel();
	private JLabel humidityvalue = new JLabel();
	private JLabel sunriseValue = new JLabel();
	private JLabel sunsetValue = new JLabel();
	private JLabel lblSkycondition = new JLabel();
	private JLabel lblWindspeed = new JLabel();
	private JLabel lblWindDir = new JLabel();
	private JLabel lblAirPressure = new JLabel();
	private JLabel lblSunrise = new JLabel();
	private JLabel lblSunset = new JLabel();
	private JLabel lblDailyHigh = new JLabel();
	private JLabel lblCurrent = new JLabel();
	private JLabel lblHumidity = new JLabel();
	private JLabel lblLastUpdate = new JLabel();
	private JLabel lblLow = new JLabel();
	private JLabel map = new JLabel();
	
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

		locationModel = new DefaultComboBoxModel<City>();
		locationModel.addElement(new City(0, "", "Add Location")); 
		comboBox_location = new JComboBox<City>(locationModel);

		frmOpenweatherapp.getContentPane().add(locationPanel());
		try {
			frmOpenweatherapp.getContentPane().add(tabbedPane());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(631, 457, 146, 14);
		frmOpenweatherapp.getContentPane().add(progressBar);
		
		programStatus = new JLabel();
		programStatus.setBounds(12, 454, 400, 14);
		
		if (currentLocation == null) 
			programStatus.setText("Waiting for Location");
			
		frmOpenweatherapp.getContentPane().add(programStatus);
		
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
		mntmRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentLocation != null)
				refresh(currentLocation.getCityID());
				else
					programStatus.setText("No Data to refresh");
			}
		});
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmOpenweatherapp.dispatchEvent(new WindowEvent(frmOpenweatherapp, WindowEvent.WINDOW_CLOSING));
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JCheckBoxMenuItem chckbxmntmTemperature = new JCheckBoxMenuItem("Temperature");
		chckbxmntmTemperature.setSelected(settings.viewTemp());
		chckbxmntmTemperature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setViewTemp(!settings.viewTemp());
				temperature.setVisible(settings.viewTemp());
				temp_max.setVisible(settings.viewTemp());
				temp_min.setVisible(settings.viewTemp());
				lblDailyHigh.setVisible(settings.viewTemp());
				lblLow.setVisible(settings.viewTemp());
				lblCurrent.setEnabled(settings.viewTemp());
			}
		});
		mnView.add(chckbxmntmTemperature);
		
		JCheckBoxMenuItem chckbxmntmSkyConditions = new JCheckBoxMenuItem("Sky Conditions");
		chckbxmntmSkyConditions.setSelected(settings.viewSkyCondition());
		chckbxmntmSkyConditions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setSkyCondition(!settings.viewSkyCondition());
				skycondvalue.setVisible(settings.viewSkyCondition());
				lblSkycondition.setEnabled(settings.viewSkyCondition());
				}
			});
		mnView.add(chckbxmntmSkyConditions);
		
		JCheckBoxMenuItem chckbxmntmWindSpeed = new JCheckBoxMenuItem("Wind");
		chckbxmntmWindSpeed.setSelected(settings.viewWindSpeedAndDir());
		chckbxmntmWindSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setViewWindSpeedAndDir(!settings.viewWindSpeedAndDir());
				windspeedvalue.setVisible(settings.viewWindSpeedAndDir());
				windDirvalue.setVisible(settings.viewWindSpeedAndDir());
				lblWindDir.setEnabled(settings.viewWindSpeedAndDir());
				lblWindspeed.setEnabled(settings.viewWindSpeedAndDir());
				}
			});
		mnView.add(chckbxmntmWindSpeed);
		
		JCheckBoxMenuItem chckbxmntmAirPressure = new JCheckBoxMenuItem("Air Pressure");
		chckbxmntmAirPressure.setSelected(settings.viewAirPressure());
		chckbxmntmAirPressure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setViewAirPressure(!settings.viewAirPressure());
				airpressurevalue.setVisible(settings.viewAirPressure());
				lblAirPressure.setEnabled(settings.viewAirPressure());
				}
			});
		mnView.add(chckbxmntmAirPressure);
		
		JCheckBoxMenuItem chckbxmntmHumidity = new JCheckBoxMenuItem("Humidity");
		chckbxmntmHumidity.setSelected(settings.viewHumidity());
		chckbxmntmHumidity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setViewHumidity(!settings.viewHumidity());
				humidityvalue.setVisible(settings.viewHumidity());
				lblHumidity.setEnabled(settings.viewHumidity());
				}
			});
		mnView.add(chckbxmntmHumidity);
		
		JCheckBoxMenuItem chckbxmntmSunsetsunrise = new JCheckBoxMenuItem("Sunset/Sunrise");
		chckbxmntmSunsetsunrise.setSelected(settings.viewSunsetAndRise());
		chckbxmntmSunsetsunrise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setViewSunsetAndRise(!settings.viewSunsetAndRise());
				sunriseValue.setVisible(settings.viewSunsetAndRise());
				sunsetValue.setVisible(settings.viewSunsetAndRise());
				lblSunrise.setEnabled(settings.viewSunsetAndRise());
				lblSunset.setEnabled(settings.viewSunsetAndRise());
			}
			});
		mnView.add(chckbxmntmSunsetsunrise);
		
		JSeparator separator = new JSeparator();
		mnView.add(separator);
		
		JRadioButtonMenuItem rdbtnmntmMetric = new JRadioButtonMenuItem("Metric");
		rdbtnmntmMetric.setSelected(settings.viewMetricUnits());
		rdbtnmntmMetric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setViewMetricUnits(true);
			}
			});
		mnView.add(rdbtnmntmMetric);
		
		JRadioButtonMenuItem rdbtnmntmImperial = new JRadioButtonMenuItem("Imperial");
		rdbtnmntmImperial.setSelected(!settings.viewMetricUnits());
		rdbtnmntmImperial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setViewMetricUnits(false);
			}
			});
		mnView.add(rdbtnmntmImperial);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnmntmMetric);
		group.add(rdbtnmntmImperial);
		
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
		
		btnRefresh = new JButton("");
		
		if (currentLocation == null) 
			btnRefresh.setEnabled(false);
		
		try {
		    Image img = ImageIO.read(getClass().getResource("/refresh-icon.png"));
		    btnRefresh.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh(currentLocation.getCityID());
			}
		});

		btnRefresh.setBounds(233, 4, 31, 31);
		panel.add(btnRefresh);
		
		comboBox_location.setBounds(31, 6, 198, 27);
		comboBox_location.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String s = ((City) comboBox_location.getSelectedItem()).toString();
		        switch (s) {
		            case "Add Location":
					try {
						newLocation();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		                break;
		            default: 
		            	System.out.println("Selected City: " + locationModel.getSelectedItem().toString());
		            	currentLocation = (City) locationModel.getSelectedItem();
		            	System.out.println(currentLocation.getCityID());
					try {
						getJSON(currentLocation.getCityID());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
		        }
		    }
		});
		
		panel.add(comboBox_location);	
		
		return panel;
	}
	
	private void newLocation() throws IOException {
		
		programStatus.setText("Adding Location");
		
		locationDialog = new AddLocationDialog(frmOpenweatherapp);
		locationDialog.setDialogListener(new DialogListener() {
			@Override
			public void dialogEventOccurred(DialogEvent event) {
				
				if (event != null) {
					try {
						getJSON(event.getCityID());
						
						currentLocation = event.getCityObj();
						locationModel.addElement(event.getCityObj());
						locationModel.setSelectedItem(event.getCityObj());
						
						programStatus.setText("Loaded Location: " + currentLocation.getCityName() + "  (ID:" + currentLocation.getCityID() + ")" );
					
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, e.toString(), "Loading city failed.. Try again", JOptionPane.ERROR_MESSAGE);
						
						//  loading location failed...
					
					}
					
					
				}
			}
		});
	}
	
	/**
	 * Method that gets the JSON from WeatherAPI.
	 * @param location
	 * @throws IOException
	 */
	private void getJSON(int locationID) throws IOException {
		
		weather = new WeatherAPI(locationID, settings.viewMetricUnits()); // Gets the JSON data from WeatherAPI
			
		JSONObject local = weather.getLocal(); // null if there was an error
		JSONObject shortTerm = weather.getShortTerm();
		JSONObject longTerm = weather.getLongTerm();
		
		if (local != null && shortTerm != null && longTerm != null) {
			tabbedPane.setEnabled(true);
			btnRefresh.setEnabled(true);
			
			System.out.println(local.toString());
			
				try {
					panel_local_values(local);
				} catch (JSONException e) {
					e.printStackTrace();
				}
		} else {
			// JSON failed..
			throw new IOException();
		
		}
		
	}
	
	private void refresh(int locationID) {
				
			try {
				getJSON(locationID);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			}		
	}
		
	private JTabbedPane tabbedPane() throws IOException {
		
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
				getJSON(currentLocation.getCityID());
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
		
		lblHelloToStart = new JLabel("hello! to start add a location");
		lblHelloToStart.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblHelloToStart.setForeground(Color.GRAY);
		lblHelloToStart.setBounds(255, 100, 375, 58);
		panel_local.add(lblHelloToStart);
		
	}
	
	private void panel_local_values(JSONObject local) throws JSONException {
		
		
		if (lblHelloToStart.getText() != null) {
			panel_labels();
			lblHelloToStart.setText(null);
		}

		Local localWeather = new Local(local);
		
		DecimalFormat df = new DecimalFormat("#.00");
		temperature.setText(String.valueOf(df.format(localWeather.getTemp())));
		
		temp_max.setText(String.valueOf(localWeather.getTempMax()));	
		temp_min.setText(String.valueOf(localWeather.getTempMin()));
		locationName.setText(locationModel.getSelectedItem().toString());
		skycondvalue.setText(localWeather.getSkyCondition());
		windspeedvalue.setText(String.valueOf(localWeather.getWindSpeed()));
		windDirvalue.setText(String.valueOf(localWeather.getWindDir()));
		airpressurevalue.setText(String.valueOf(localWeather.getAirPressure()));
		humidityvalue.setText(String.valueOf(localWeather.getHumidity()));
		
		time = localWeather.getSunriseTime();
		sunriseValue.setText(String.valueOf(time.unixToTime()));
		
		time = localWeather.getSunsetTime();
		sunsetValue.setText(String.valueOf(time.unixToTime()));
		
	    String latitude = localWeather.getLatitude();
		String longitude = localWeather.getLongitude();
		mapIcon = new Map(latitude,longitude).getMap();
		map.setIcon(mapIcon);
		
		
		
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	System.out.println(sdf.format(cal.getTime()));
		lblUpdatedtime.setText(String.valueOf(sdf.format(cal.getTime())));
	}
	
	private void panel_labels() {
		
		temperature.setFont(new Font("Tahoma", Font.PLAIN, 45));
		temperature.setBounds(40, 56, 200, 44);
		temperature.setVisible(settings.viewTemp());
		panel_local.add(temperature);
		
		temp_max.setFont(new Font("Tahoma", Font.PLAIN, 25));
		temp_max.setBounds(197, 61, 60, 32);
		temp_max.setVisible(settings.viewTemp());
		panel_local.add(temp_max);
		
		temp_min.setFont(new Font("Tahoma", Font.PLAIN, 25));
		temp_min.setBounds(323, 61, 60, 32);
		temp_min.setVisible(settings.viewTemp());
		panel_local.add(temp_min);
		
		locationName.setHorizontalAlignment(SwingConstants.RIGHT);
		locationName.setFont(new Font("Tahoma", Font.PLAIN, 21));
		locationName.setBounds(544, 11, 200, 33);
		panel_local.add(locationName);
		
		lblUpdatedtime.setHorizontalAlignment(SwingConstants.LEFT);
		lblUpdatedtime.setBounds(700, 392, 200, 14);
		panel_local.add(lblUpdatedtime);
		
		skycondvalue.setBounds(200, 122, 80, 14);
		skycondvalue.setVisible(settings.viewSkyCondition());
		panel_local.add(skycondvalue);
		
		windspeedvalue.setBounds(200, 157, 80, 14);
		windspeedvalue.setVisible(settings.viewWindSpeedAndDir());
		panel_local.add(windspeedvalue);
		
		windDirvalue.setBounds(200, 192, 80, 14);
		windDirvalue.setVisible(settings.viewWindSpeedAndDir());
		panel_local.add(windDirvalue);
		
		airpressurevalue.setBounds(200, 224, 80, 14);
		airpressurevalue.setVisible(settings.viewAirPressure());
		panel_local.add(airpressurevalue);
		
		humidityvalue.setBounds(200, 257, 80, 14);
		humidityvalue.setVisible(settings.viewHumidity());
		panel_local.add(humidityvalue);
		
		sunriseValue.setBounds(200, 314, 80, 14);
		sunriseValue.setVisible(settings.viewSunsetAndRise());
		panel_local.add(sunriseValue);
		
		sunsetValue.setBounds(200, 349, 80, 14);
		sunsetValue.setVisible(settings.viewSunsetAndRise());
		panel_local.add(sunsetValue);
		
		lblSkycondition = new JLabel("SkyCondition");
		lblSkycondition.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSkycondition.setBounds(40, 122, 150, 14);
		lblSkycondition.setEnabled(settings.viewSkyCondition());
		panel_local.add(lblSkycondition);
		
		lblWindspeed = new JLabel("WindSpeed");
		lblWindspeed.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWindspeed.setBounds(40, 146, 150, 33);
		lblWindspeed.setEnabled(settings.viewWindSpeedAndDir());
		panel_local.add(lblWindspeed);
		
		lblWindDir = new JLabel("Wind Direction");
		lblWindDir.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWindDir.setBounds(40, 185, 122, 25);
		lblWindDir.setEnabled(settings.viewWindSpeedAndDir());
		panel_local.add(lblWindDir);
		
		lblAirPressure = new JLabel("Air Pressure");
		lblAirPressure.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAirPressure.setBounds(40, 217, 113, 25);
		lblAirPressure.setEnabled(settings.viewAirPressure());
		panel_local.add(lblAirPressure);
		
		lblSunrise = new JLabel("SunRise");
		lblSunrise.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSunrise.setBounds(40, 303, 110, 33);
		lblSunrise.setEnabled(settings.viewSunsetAndRise());
		panel_local.add(lblSunrise);
		
		lblSunset = new JLabel("SunSet");
		lblSunset.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSunset.setBounds(40, 338, 93, 33);
		lblSunset.setEnabled(settings.viewSunsetAndRise());
		panel_local.add(lblSunset);
		
		lblHumidity = new JLabel("Humidity");
		lblHumidity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHumidity.setBounds(40, 251, 122, 25);
		lblHumidity.setEnabled(settings.viewHumidity());
		panel_local.add(lblHumidity);

		lblLastUpdate = new JLabel("Last Updated:");
		lblLastUpdate.setBounds(625, 389, 77, 20);
		panel_local.add(lblLastUpdate);
		
		lblCurrent = new JLabel("Current:");
		lblCurrent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCurrent.setBounds(40, 24, 85, 28);
		lblCurrent.setEnabled(settings.viewTemp());
		panel_local.add(lblCurrent);
		
		lblDailyHigh = new JLabel("High:");
		lblDailyHigh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDailyHigh.setBounds(198, 25, 60, 26);
		lblDailyHigh.setVisible(settings.viewTemp());
		panel_local.add(lblDailyHigh);
		
		lblLow = new JLabel("Low:");
		lblLow.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLow.setBounds(324, 31, 46, 14);
		lblLow.setVisible(settings.viewTemp());
		panel_local.add(lblLow);
		
		//JPanel panel_1 = new JPanel();
		//panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//panel_1.setBounds(389, 122, 355, 249);
		//panel_local.add(panel_1);
		
		
		
		//ImageIcon icon = new Map("Los Angeles").getMap();
		//JLabel map = new JLabel(icon);
		map.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		map.setBounds(389, 120, 355, 249);
		panel_local.add(map);
		
		
		//JLabel location_image = new JLabel("");
		//location_image.setBackground(Color.gray);
		//location_image.setIcon(null);
		//panel_1.add(location_image);
		
		JLabel lblMap = new JLabel("Map:");
		lblMap.setBounds(389, 100, 46, 14);
		panel_local.add(lblMap);
		
	}
	
	private void panel_short_content() {
		
	}
	
	private void panel_long_content() {
		
	}
	
}
