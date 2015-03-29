package main.java;

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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComboBox;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
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
	
	final String DEGREE  = "\u00b0";
	
	private double dailyPrecip=0;
	
	// Location ComboBox
	private DefaultComboBoxModel<City> locationModel= new DefaultComboBoxModel<City>();;
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
	private JLabel lblUpdatedtime1 = new JLabel();
	private JLabel lblUpdatedtime2 = new JLabel();
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
	private JLabel lblLow = new JLabel();
	private JLabel map = new JLabel();
	private JLabel dailyPrecipLabel = new JLabel();
	private JLabel dailyPrecipValue = new JLabel();
	
	private JLabel locationName1 = new JLabel();
	private JPanel panel = new JPanel() {
		private static final long serialVersionUID = 1L;
	     @Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); 
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	  
	private JLabel time1 = new JLabel();
	private JLabel temp1 = new JLabel();
	private JLabel sky1 = new JLabel();
	private JLabel picture = new JLabel();
	private JLabel rain1 = new JLabel();
	
	JPanel panel_1 = new JPanel() {
		private static final long serialVersionUID = 1L;
	     @Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); 
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel time2 = new JLabel();
	private JLabel temp2 = new JLabel();
	private JLabel sky2 = new JLabel();
	private JLabel picture2 = new JLabel();
	private JLabel rain2 = new JLabel();
	
	private JPanel panel_2 = new JPanel() {
		private static final long serialVersionUID = 1L;
	     @Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15);
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel time3 = new JLabel();
	private JLabel temp3 = new JLabel();
	private JLabel sky3 = new JLabel();
	private JLabel picture3 = new JLabel();
	private JLabel rain3 = new JLabel();
	
	private JPanel panel_3 = new JPanel() {
		private static final long serialVersionUID = 1L;
	     @Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); 
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel time4 = new JLabel();
	private JLabel temp4 = new JLabel();
	private JLabel sky4 = new JLabel();
	private JLabel picture4 = new JLabel();
	private JLabel rain4 = new JLabel();
	
	private JPanel panel_4 = new JPanel() {
		private static final long serialVersionUID = 1L;
	     @Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); 
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel time7 = new JLabel();
	private JLabel temp7 = new JLabel();
	private JLabel sky7 = new JLabel();
	private JLabel picture7 = new JLabel();
	private JLabel rain7 = new JLabel();
	
	private JPanel panel_5 = new JPanel() {
		private static final long serialVersionUID = 1L;
	     @Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); 
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel time8 = new JLabel();
	private JLabel temp8 = new JLabel();
	private JLabel sky8 = new JLabel();
	private JLabel picture8 = new JLabel();
	private JLabel rain8 = new JLabel();
	
	private JPanel panel_6 = new JPanel() {
		private static final long serialVersionUID = 1L;
	     @Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); 
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel time5 = new JLabel();
	private JLabel temp5 = new JLabel();
	private JLabel sky5 = new JLabel();
	private JLabel picture5 = new JLabel();
	private JLabel rain5 = new JLabel();
	
	private JPanel panel_7 = new JPanel() {
		private static final long serialVersionUID = 1L;
		@Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); //Border corners arcs {width,height}, change this to whatever you want
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel time6 = new JLabel();
	private JLabel temp6 = new JLabel();
	private JLabel sky6 = new JLabel();
	private JLabel picture6 = new JLabel();
	private JLabel rain6 = new JLabel();
	
	// Labels and Panels for LongTerm
	
	private JLabel locationName2 = new JLabel();
	
	private JPanel panel_long_0 = new JPanel() {
		private static final long serialVersionUID = 1L;
		@Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); //Border corners arcs {width,height}, change this to whatever you want
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel long_date = new JLabel();
	private JLabel long_temp = new JLabel();
	private JLabel long_picture = new JLabel();
	private JLabel long_temp_max = new JLabel();
	private JLabel long_temp_max_label = new JLabel();
	private JLabel long_temp_min_label = new JLabel();
	private JLabel long_temp_min = new JLabel();
	private JLabel long_temp_sky = new JLabel();
	private JLabel long_temp_precip_label = new JLabel();
	private JLabel long_temp_precip = new JLabel();
	
	private JPanel panel_long_1 = new JPanel() {
		private static final long serialVersionUID = 1L;
		@Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); //Border corners arcs {width,height}, change this to whatever you want
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel long_date1 = new JLabel();
	private JLabel long_temp1 = new JLabel();
	private JLabel long_picture1 = new JLabel();
	private JLabel long_temp_max1 = new JLabel();
	private JLabel long_temp_max_label1 = new JLabel();
	private JLabel long_temp_min_label1 = new JLabel();
	private JLabel long_temp_min1 = new JLabel();
	private JLabel long_temp_sky1 = new JLabel();
	private JLabel long_temp_precip_label1 = new JLabel();
	private JLabel long_temp_precip1 = new JLabel();
	
	private JPanel panel_long_2 = new JPanel() {
		private static final long serialVersionUID = 1L;
		@Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); //Border corners arcs {width,height}, change this to whatever you want
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel long_date2 = new JLabel();
	private JLabel long_temp2 = new JLabel();
	private JLabel long_picture2 = new JLabel();
	private JLabel long_temp_max2 = new JLabel();
	private JLabel long_temp_max_label2 = new JLabel();
	private JLabel long_temp_min_label2 = new JLabel();
	private JLabel long_temp_min2 = new JLabel();
	private JLabel long_temp_sky2 = new JLabel();
	private JLabel long_temp_precip_label2 = new JLabel();
	private JLabel long_temp_precip2 = new JLabel();
	
	private JPanel panel_long_3 = new JPanel() {
		private static final long serialVersionUID = 1L;
		@Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); //Border corners arcs {width,height}, change this to whatever you want
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel long_date3 = new JLabel();
	private JLabel long_temp3 = new JLabel();
	private JLabel long_picture3 = new JLabel();
	private JLabel long_temp_max3 = new JLabel();
	private JLabel long_temp_max_label3 = new JLabel();
	private JLabel long_temp_min_label3 = new JLabel();
	private JLabel long_temp_min3 = new JLabel();
	private JLabel long_temp_sky3 = new JLabel();
	private JLabel long_temp_precip_label3 = new JLabel();
	private JLabel long_temp_precip3 = new JLabel();
	
	private JPanel panel_long_4 = new JPanel() {
		private static final long serialVersionUID = 1L;
		@Override
	     protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Dimension arcs = new Dimension(15,15); //Border corners arcs {width,height}, change this to whatever you want
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


	        //Draws the rounded panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
	        graphics.setColor(getForeground());
	        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
	     }
	  };
	private JLabel long_date4 = new JLabel();
	private JLabel long_temp4 = new JLabel();
	private JLabel long_picture4 = new JLabel();
	private JLabel long_temp_max4 = new JLabel();
	private JLabel long_temp_max_label4 = new JLabel();
	private JLabel long_temp_min_label4 = new JLabel();
	private JLabel long_temp_min4 = new JLabel();
	private JLabel long_temp_sky4 = new JLabel();
	private JLabel long_temp_precip_label4 = new JLabel();
	private JLabel long_temp_precip4 = new JLabel();
	
	private URL url;
	private BufferedImage skyImages;
	private ImageIcon skyIcons;
	
	/**
	 * Create the application.
	 */
	public AppWindow() {
		initializeSettings();
		initialize();
	}

	private void initializeSettings() {
		//Object Serialization Load//
				try {
					ObjectInputStream in = new ObjectInputStream(new FileInputStream("settings.dat"));
					settings = (Settings) in.readObject();
					in.close();
					City[] cityList = settings.getCityList();
					//if (cityList[0] != null)
					locationModel.addElement(new City(0, "", "Add Location")); 
					
					for (int i = 0; i < cityList.length; i++ ) {
						if (cityList[i] != null) {
							
							locationModel.addElement(cityList[i]);	
						}
					}
					if (settings.getCity() != null) {
						currentLocation = settings.getCity();
						locationModel.setSelectedItem(currentLocation);
					}
					
					
					
				}catch (FileNotFoundException e){
					System.out.println("No previous settings found, using default values");
					settings = new Settings(true, true, true, true, true, true, true, null,true);
					locationModel.addElement(new City(0, "", "Add Location")); 
				}catch (IOException e){
					e.printStackTrace();
				}catch (ClassNotFoundException e){
					e.printStackTrace();
				}
		////////////////////////////	
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
		
		frmOpenweatherapp.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
				    int confirmed = JOptionPane.showConfirmDialog(null, 
				        "Would you like to save current settings?", "Save Current Settings?",
				        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
				    	//Object Serialization Save data//
						try {
							settings.changeCurrentCity(currentLocation);
							
							 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("settings.dat"));
							 out.writeObject(settings);
							 out.close();
						}catch (FileNotFoundException e1){
							e1.printStackTrace();
						}catch (IOException e1){
							e1.printStackTrace();
						}
						//////////////////////////////////
				      
				    }
				  }
				});
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (frmOpenweatherapp.getWidth() / 2), middle.y - (frmOpenweatherapp.getHeight() / 2));
		frmOpenweatherapp.setLocation(newLocation);
		
		frmOpenweatherapp.setJMenuBar(menubar());

		comboBox_location = new JComboBox<City>(locationModel);
		
		frmOpenweatherapp.getContentPane().add(locationPanel());
		try {
			frmOpenweatherapp.getContentPane().add(tabbedPane());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
				newLocation();
			}
		});
		mnLocations.add(mntmNew);
		
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
				//Object Serialization Save data//
				try {
					settings.changeCurrentCity(currentLocation);
					
					 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("settings.dat"));
					 out.writeObject(settings);
					 out.close();
				}catch (FileNotFoundException e1){
					e1.printStackTrace();
				}catch (IOException e1){
					e1.printStackTrace();
				}
				//////////////////////////////////
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
		
		JCheckBoxMenuItem chckbxmntmPrecipitation = new JCheckBoxMenuItem("Precipitation");
		chckbxmntmPrecipitation.setSelected(settings.viewPrecipitation());
		chckbxmntmPrecipitation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setViewPrecipitation(!settings.viewPrecipitation());
				dailyPrecipLabel.setEnabled(settings.viewPrecipitation());
				dailyPrecipValue.setVisible(settings.viewPrecipitation());
			}
			});
		mnView.add(chckbxmntmPrecipitation);
		
		JSeparator separator = new JSeparator();
		mnView.add(separator);
		
		final JRadioButtonMenuItem rdbtnmntmMetric = new JRadioButtonMenuItem("Metric");
		final JRadioButtonMenuItem rdbtnmntmImperial = new JRadioButtonMenuItem("Imperial");
		
		rdbtnmntmMetric.setSelected(settings.viewMetricUnits());
		rdbtnmntmMetric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					settings.setViewMetricUnits(true);
					if (currentLocation != null) {
						System.out.println("Getting data from Open Weather Map...");
						getJSON(currentLocation.getCityID());
						// case where combobox is at add location and you change units
						locationModel.setSelectedItem(currentLocation); 
					}
				} catch (JSONException | IOException ex) {
					
					// something messed up, revert back to old settings
					settings.setViewMetricUnits(false);
					rdbtnmntmImperial.setSelected(true);
					rdbtnmntmMetric.setSelected(false);
					JOptionPane.showMessageDialog(null, "Error retrieving data for " + currentLocation  + ". Try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
					
				}
			}
			});
		mnView.add(rdbtnmntmMetric);
		
		rdbtnmntmImperial.setSelected(!settings.viewMetricUnits());
		rdbtnmntmImperial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					settings.setViewMetricUnits(false);
					if (currentLocation != null) {
						System.out.println("Getting data from Open Weather Map...");
						getJSON(currentLocation.getCityID());
						locationModel.setSelectedItem(currentLocation); 
					}
				} catch (JSONException | IOException ex) {
					
					settings.setViewMetricUnits(true);
					rdbtnmntmImperial.setSelected(false);
					rdbtnmntmMetric.setSelected(true);
					JOptionPane.showMessageDialog(null, "Error retrieving data for " + currentLocation  + ". Try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
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
						newLocation();
						btnRefresh.setEnabled(false); // disable refresh while adding location
		                break;
		            default: 
		            	
		            	currentLocation = (City) locationModel.getSelectedItem();
		            	
		            	
					try {
						System.out.println("Getting data from Open Weather Map...");
						getJSON(currentLocation.getCityID());
					} catch (IOException | JSONException e1) {
						JOptionPane.showMessageDialog(null, "Error retrieving data for " + currentLocation  + ". Try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					break;
		        }
		    }
		});
		
		panel.add(comboBox_location);	
		
		return panel;
	}
	
	private void newLocation() {
		
		programStatus.setText("Adding Location");
		
		locationDialog = new AddLocationDialog(frmOpenweatherapp);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				locationDialog.setVisible(true);
			
			} //run
			
		}); // invokeLater
		
		locationDialog.setDialogListener(new DialogListener() {
			@Override
			public void dialogEventOccurred(DialogEvent event) {
				
				if (event != null) {
					try {
						
						City newCity = event.getCityObj();
						
						City[] cityList = settings.getCityList();
						boolean containsCity = false;
						for (int i = 0; i < cityList.length; i++) {
							
							if (cityList[i] != null) {
								if (cityList[i].getCityID() == event.getCityID()) {
									containsCity = true;
									break;
								}
							}	
						}
						
						if (!containsCity) {
							System.out.println("Getting data from Open Weather Map...");
							getJSON(newCity.getCityID()); // try getting json first
							
							currentLocation = newCity;
							locationModel.addElement(newCity);
							locationModel.setSelectedItem(newCity);
							settings.addLocation(newCity);
							
							programStatus.setText("Loaded Location: " + currentLocation.getCityName() + "  (ID:" + currentLocation.getCityID() + ")" );
							
							
						} else {
							// if already added city, change to it
							if (newCity.getCityID() != currentLocation.getCityID()) {
								locationModel.setSelectedItem(newCity);
								currentLocation = newCity;
								settings.changeCurrentCity(newCity);
							} else {
								locationModel.setSelectedItem(newCity);
							}
							
							// one less popup?
							// JOptionPane.showMessageDialog(null, "Already added " + event.getCityName() + ".", "ERROR", JOptionPane.ERROR_MESSAGE);
							
						}
						
						
					} catch (IOException | JSONException e) {
						JOptionPane.showMessageDialog(null, "Loading data for " + event.getCityName() + " failed.. Try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					
					
				}
			}
		}); // dialogEventOccurred
		
		
		
		

	}
	
	/**
	 * Method that gets the JSON from WeatherAPI.
	 * @param location
	 * @throws IOException
	 */
	private void getJSON(int locationID) throws IOException, JSONException {
		
		weather = new WeatherAPI(locationID, settings.viewMetricUnits()); // Gets the JSON data from WeatherAPI
			
		// Get local, short-term and long-term
		JSONObject local, shortTerm, longTerm;
		try {
			local = weather.getLocal();
			shortTerm = weather.getShortTerm();
			longTerm = weather.getLongTerm();
		} catch (JSONException jsonEx) {
			throw new JSONException("Error occurred while retrieving weather information.");
		} catch (IOException ioEx) {
			throw new IOException("Error occurred while retrieving weather information.");
		}
		
		//// No errors from retrieving JSON objects ////
		
		tabbedPane.setEnabled(true);
		btnRefresh.setEnabled(true);
		
		
	
		try {
		panel_short_values(shortTerm);	
		panel_local_values(local);
		panel_long_values(longTerm);
		} catch (JSONException e) {
			throw new JSONException("Error with JSONObject formatting.");
		}
	
	}
	
	private void refresh(int locationID) {
			
			try {
				System.out.println("Getting data from Open Weather Map...");
				getJSON(locationID);
			} catch (IOException | JSONException e) {
				JOptionPane.showMessageDialog(null, "Refresh Failed... Try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}		
	}
	
	private String getTempUnits() {
		if (settings.viewMetricUnits())
			return DEGREE + "C";
		else
			return DEGREE + "F";
	
	}
	private String getWindUnits(){
		if (settings.viewMetricUnits())
			return " Km/H";
		else
			return " mph";
	}
	private String getPrecipUnits(){
		if (settings.viewMetricUnits())
			return " mm";
		else 
			return " mm";
	}
	
	private String getPressure(double pressure){
		DecimalFormat df = new DecimalFormat("#0.00");
		if (settings.viewMetricUnits())
			return String.valueOf(df.format(pressure/10.00))+" kPa" ;
		else
			return String.valueOf(df.format(pressure))+" mb";
		
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
		
		panel_short = new JPanel();
		panel_short.setBackground(Color.WHITE);
		panel_short.setLayout(null);
		tabbedPane.addTab("ShortTerm", null, panel_short, null);
		
		panel_long = new JPanel();
		panel_long.setBackground(Color.WHITE);
		panel_long.setLayout(null);
		tabbedPane.addTab("LongTerm", null, panel_long, null);

		if (currentLocation == null) {
			panel_blank();
		} else {
			try {
				System.out.println("Getting data from Open Weather Map...");
				getJSON(currentLocation.getCityID());
			} catch (JSONException | IOException e) {
				JOptionPane.showMessageDialog(null, "Initializing city list failed.. Please refresh.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return tabbedPane;
		
	}
	
	private void panel_blank() {
		
		lblHelloToStart = new JLabel("Hello! to get started, add a location");
		lblHelloToStart.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblHelloToStart.setForeground(Color.GRAY);
		lblHelloToStart.setBounds(235, 100, 375, 58);
		panel_local.add(lblHelloToStart);
		
	}
	
	private void panel_local_values(JSONObject local) throws JSONException {
		
		
		if (lblHelloToStart.getText() != null) {
			panel_labels();
			lblHelloToStart.setText(null);
		}
		

		Local localWeather = new Local(local);
		
		DecimalFormat df = new DecimalFormat("#0.00");
		temperature.setText(String.valueOf(df.format(localWeather.getTemp()) + getTempUnits()));
		temp_max.setText(String.valueOf(df.format(localWeather.getTempMax()) + getTempUnits()));	
		temp_min.setText(String.valueOf(df.format(localWeather.getTempMin()) + getTempUnits()));
//		locationName.setText(locationModel.getSelectedItem().toString());
		locationName.setText(currentLocation.toString());
		skycondvalue.setText(localWeather.getSkyCondition());
		windspeedvalue.setText(String.valueOf(localWeather.getWindSpeed()) + getWindUnits());
		windDirvalue.setText(String.valueOf(localWeather.getWindDir()));
		double pressureConvert = localWeather.getAirPressure();
		airpressurevalue.setText(getPressure(pressureConvert));
		humidityvalue.setText(String.valueOf(localWeather.getHumidity())+" %");
		dailyPrecipValue.setText(String.valueOf(df.format(dailyPrecip))+getPrecipUnits());
		
		time = localWeather.getSunriseTime();
		sunriseValue.setText(String.valueOf(time.unixToTime()));
		
		time = localWeather.getSunsetTime();
		sunsetValue.setText(String.valueOf(time.unixToTime()));
		
	    String latitude = localWeather.getLatitude();
		String longitude = localWeather.getLongitude();
		String icon = localWeather.getIcon();
		
		mapIcon = new Map(latitude,longitude,icon).getMap();
		mapIcon.getImage().flush();
		map.setIcon(mapIcon);
		
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		lblUpdatedtime.setText("Updated: " + String.valueOf(sdf.format(cal.getTime())));
		
		// 	SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm a z");
		lblUpdatedtime.setText("Updated: " + localWeather.getUpdateTime().unixToTime());
		lblUpdatedtime1.setText("Updated: " + String.valueOf(sdf.format(cal.getTime())));
		lblUpdatedtime2.setText("Updated: " + String.valueOf(sdf.format(cal.getTime())));
	}
	
	private void panel_short_values(JSONObject shortTerm) throws JSONException, IOException {
		
		ShortTerm shortTermWeather = new ShortTerm(shortTerm);
		
		double[] temps = shortTermWeather.getTemps();
		double[] precips = shortTermWeather.getPrecips();
		Time[] times = shortTermWeather.getTimes();
		String[] skys = shortTermWeather.getSkyConditions();
		String[] icons = shortTermWeather.getIcons();
		dailyPrecip = shortTermWeather.getDailyPrecip();
		
		
//		locationName1.setText(locationModel.getSelectedItem().toString());
		locationName1.setText(currentLocation.toString());
		
		time1.setText(times[0].unixToTime());
		temp1.setText("Temp: " + String.valueOf(temps[0]) + getTempUnits());				
		sky1.setText("Sky: " + skys[0]);
		rain1.setText("Precip: " + precips[0]+getPrecipUnits());
		
		url = new URL("http://openweathermap.org/img/w/" + icons[0] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		picture.setIcon(skyIcons);
		
		time2.setText(times[1].unixToTime());
		temp2.setText("Temp: " + String.valueOf(temps[1]) + getTempUnits());				
		sky2.setText("Sky: " + skys[1]);
		rain2.setText("Precip: " + precips[1]+getPrecipUnits());
		
		url = new URL("http://openweathermap.org/img/w/" + icons[1] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		picture2.setIcon(skyIcons);
		
		time3.setText(times[2].unixToTime());
		temp3.setText("Temp: " + String.valueOf(temps[2]) + getTempUnits());				
		sky3.setText("Sky: " + skys[2]);
		rain3.setText("Precip: " + precips[2]+getPrecipUnits());
		
		url = new URL("http://openweathermap.org/img/w/" + icons[2] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		picture3.setIcon(skyIcons);
		
		time4.setText(times[3].unixToTime());
		temp4.setText("Temp: " + String.valueOf(temps[3]) + getTempUnits());				
		sky4.setText("Sky: " + skys[3]);
		rain4.setText("Precip: " + precips[3]+getPrecipUnits());
		
		url = new URL("http://openweathermap.org/img/w/" + icons[3] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		picture4.setIcon(skyIcons);
		
		time5.setText(times[4].unixToTime());
		temp5.setText("Temp: " + String.valueOf(temps[4]) + getTempUnits());				
		sky5.setText("Sky: " + skys[4]);
		rain5.setText("Precip: " + precips[4]+getPrecipUnits());
		
		url = new URL("http://openweathermap.org/img/w/" + icons[4] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		picture5.setIcon(skyIcons);
		
		time6.setText(times[5].unixToTime());
		temp6.setText("Temp: " + String.valueOf(temps[5]) + getTempUnits());				
		sky6.setText("Sky: " + skys[5]);
		rain6.setText("Precip: " + precips[5]+getPrecipUnits());
		
		url = new URL("http://openweathermap.org/img/w/" + icons[5] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		picture6.setIcon(skyIcons);
		
		time7.setText(times[6].unixToTime());
		temp7.setText("Temp: " + String.valueOf(temps[6]) + getTempUnits());				
		sky7.setText("Sky: " + skys[6]);
		rain7.setText("Precip: " + precips[6]+getPrecipUnits());
		
		url = new URL("http://openweathermap.org/img/w/" + icons[6] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		picture7.setIcon(skyIcons);
		
		time8.setText(times[7].unixToTime());
		temp8.setText("Temp: " + String.valueOf(temps[7] + getTempUnits()));				
		sky8.setText("Sky: " + skys[7]);
		rain8.setText("Precip: " + precips[7]+getPrecipUnits());
		
		url = new URL("http://openweathermap.org/img/w/" + icons[7] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		picture8.setIcon(skyIcons);
		
	}
	
	private void panel_long_values(JSONObject longTerm) throws JSONException, IOException {
		
		LongTerm longTermWeather = new LongTerm(longTerm);
		
		Time[] dates = longTermWeather.getTimes();
		double[] temps = longTermWeather.getTemps();
		double[] maxTemps = longTermWeather.getMaxTemps();
		double[] minTemps = longTermWeather.getMinTemps();
		double[] precips = longTermWeather.getPrecips();
		String[] sky = longTermWeather.getSkyConditions();
		String[] icons = longTermWeather.getIcons();
		
//		locationName2.setText(locationModel.getSelectedItem().toString());
		locationName2.setText(currentLocation.toString());

		
		long_date.setText(dates[0].unixToDate());
		long_temp.setText(String.valueOf(temps[0] + getTempUnits()));
		long_temp_max.setText(String.valueOf(maxTemps[0] + getTempUnits()));
		long_temp_min.setText(String.valueOf(minTemps[0] + getTempUnits()));
		long_temp_precip.setText(String.valueOf(precips[0])+getPrecipUnits());
		long_temp_sky.setText(sky[0]);
		
		url = new URL("http://openweathermap.org/img/w/" + icons[0] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		long_picture.setIcon(skyIcons);
		
		long_date1.setText(dates[1].unixToDate());
		long_temp1.setText(String.valueOf(temps[1] + getTempUnits()));
		long_temp_max1.setText(String.valueOf(maxTemps[1] + getTempUnits()));
		long_temp_min1.setText(String.valueOf(minTemps[1] + getTempUnits()));
		long_temp_precip1.setText(String.valueOf(precips[1])+getPrecipUnits());
		long_temp_sky1.setText(sky[1]);
		
		url = new URL("http://openweathermap.org/img/w/" + icons[1] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		long_picture1.setIcon(skyIcons);
		
		long_date2.setText(dates[2].unixToDate());
		long_temp2.setText(String.valueOf(temps[2] + getTempUnits()));
		long_temp_max2.setText(String.valueOf(maxTemps[2] + getTempUnits()));
		long_temp_min2.setText(String.valueOf(minTemps[2] + getTempUnits()));
		long_temp_precip2.setText(String.valueOf(precips[2])+getPrecipUnits());
		long_temp_sky2.setText(sky[2]);
		
		url = new URL("http://openweathermap.org/img/w/" + icons[2] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		long_picture2.setIcon(skyIcons);
		
		long_date3.setText(dates[3].unixToDate());
		long_temp3.setText(String.valueOf(temps[3] + getTempUnits()));
		long_temp_max3.setText(String.valueOf(maxTemps[3] + getTempUnits()));
		long_temp_min3.setText(String.valueOf(minTemps[3] + getTempUnits()));
		long_temp_precip3.setText(String.valueOf(precips[3])+getPrecipUnits());
		long_temp_sky3.setText(sky[3]);
		
		url = new URL("http://openweathermap.org/img/w/" + icons[3] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		long_picture3.setIcon(skyIcons);
		
		long_date4.setText(dates[4].unixToDate());
		long_temp4.setText(String.valueOf(temps[4] + getTempUnits()));
		long_temp_max4.setText(String.valueOf(maxTemps[4] + getTempUnits()));
		long_temp_min4.setText(String.valueOf(minTemps[4] + getTempUnits()));
		long_temp_precip4.setText(String.valueOf(precips[4])+getPrecipUnits());
		long_temp_sky4.setText(sky[4]);
		
		url = new URL("http://openweathermap.org/img/w/" + icons[4] + ".png");
		skyImages = ImageIO.read(url);
		skyIcons = new ImageIcon(skyImages);
		long_picture4.setIcon(skyIcons);		
	}
	
	private void panel_labels() {
		
		temperature.setFont(new Font("Tahoma", Font.PLAIN, 35));
		temperature.setBounds(40, 56, 200, 44);
		temperature.setVisible(settings.viewTemp());
		panel_local.add(temperature);
		
		temp_max.setFont(new Font("Tahoma", Font.PLAIN, 25));
		temp_max.setBounds(197, 61, 150, 32);
		temp_max.setVisible(settings.viewTemp());
		panel_local.add(temp_max);
		
		temp_min.setFont(new Font("Tahoma", Font.PLAIN, 25));
		temp_min.setBounds(323, 61, 150, 32);
		temp_min.setVisible(settings.viewTemp());
		panel_local.add(temp_min);
		
		locationName.setHorizontalAlignment(SwingConstants.RIGHT);
		locationName.setFont(new Font("Tahoma", Font.PLAIN, 21));
		locationName.setBounds(344, 11, 400, 33);
		panel_local.add(locationName);
		
		lblUpdatedtime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUpdatedtime.setBounds(550, 392, 200, 14);
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
		
		airpressurevalue.setBounds(200, 224, 150, 14);
		airpressurevalue.setVisible(settings.viewAirPressure());
		panel_local.add(airpressurevalue);
		
		humidityvalue.setBounds(200, 257, 80, 14);
		humidityvalue.setVisible(settings.viewHumidity());
		panel_local.add(humidityvalue);
		
		
		dailyPrecipValue.setBounds(200,292,80,14);
		dailyPrecipValue.setVisible(true);
		panel_local.add(dailyPrecipValue);
		
		sunriseValue.setBounds(200, 330, 120, 14);
		sunriseValue.setVisible(settings.viewSunsetAndRise());
		panel_local.add(sunriseValue);
		
		sunsetValue.setBounds(200, 363, 120, 14);
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
		lblSunrise.setBounds(40, 319, 110, 33);
		lblSunrise.setEnabled(settings.viewSunsetAndRise());
		panel_local.add(lblSunrise);
		
		lblSunset = new JLabel("SunSet");
		lblSunset.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSunset.setBounds(40, 353, 93, 33);
		lblSunset.setEnabled(settings.viewSunsetAndRise());
		panel_local.add(lblSunset);
		
		lblHumidity = new JLabel("Humidity");
		lblHumidity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHumidity.setBounds(40, 251, 122, 25);
		lblHumidity.setEnabled(settings.viewHumidity());
		panel_local.add(lblHumidity);
		
		
		dailyPrecipLabel = new JLabel("24 Hour Precip");
		dailyPrecipLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dailyPrecipLabel.setBounds(40, 285, 122, 25);
		dailyPrecipLabel.setEnabled(settings.viewPrecipitation());
		panel_local.add(dailyPrecipLabel);
		
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
		
		map.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		map.setBounds(389, 120, 355, 249);
		panel_local.add(map);
				
		JLabel lblMap = new JLabel("Map:");
		lblMap.setBounds(389, 100, 46, 14);
		panel_local.add(lblMap);
		
		// ShortTerm Panel Labels //
		
		locationName1.setHorizontalAlignment(SwingConstants.RIGHT);
		locationName1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		locationName1.setBounds(344, 5, 400, 33);
		panel_short.add(locationName1);
		
		lblUpdatedtime1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUpdatedtime1.setBounds(550, 392, 200, 14);
		panel_short.add(lblUpdatedtime1);
		
		sky1.setHorizontalAlignment(SwingConstants.LEFT);
		sky1.setBounds(10, 45, 84, 59);
		panel.add(sky1);
		
		time1.setVerticalAlignment(SwingConstants.TOP);
		time1.setBounds(10, 9, 100, 49);
		panel.add(time1);
		time1.setHorizontalAlignment(SwingConstants.LEFT);
		
		temp1.setHorizontalAlignment(SwingConstants.LEFT);
		temp1.setBounds(10, 22, 150, 49);
		panel.add(temp1);
		
		picture.setBounds(77, 53, 46, 46);
		panel.add(picture);
		
		rain1.setBounds(10, 102, 100, 14);
		panel.add(rain1);
		
		panel.setBounds(67, 43, 133, 143);
		panel.setOpaque(false);
		panel_short.add(panel);
		panel.setLayout(null);
		
		panel_1.setLayout(null);
		panel_1.setOpaque(false);
		panel_1.setBounds(223, 43, 133, 143);
		panel_short.add(panel_1);
		
		time2.setVerticalAlignment(SwingConstants.TOP);
		time2.setHorizontalAlignment(SwingConstants.LEFT);
		time2.setBounds(10, 9, 100, 49);
		panel_1.add(time2);
		
		temp2.setHorizontalAlignment(SwingConstants.LEFT);
		temp2.setBounds(10, 22, 150, 49);
		panel_1.add(temp2);
		
		sky2.setHorizontalAlignment(SwingConstants.LEFT);
		sky2.setBounds(10, 45, 84, 59);
		panel_1.add(sky2);
		
		picture2.setBounds(77, 58, 46, 46);
		panel_1.add(picture2);
		
		rain2.setBounds(10, 102, 100, 14);
		panel_1.add(rain2);
		
		panel_2.setLayout(null);
		panel_2.setOpaque(false);
		panel_2.setBounds(378, 43, 133, 143);
		panel_short.add(panel_2);
		
		time3.setVerticalAlignment(SwingConstants.TOP);
		time3.setHorizontalAlignment(SwingConstants.LEFT);
		time3.setBounds(10, 9, 100, 49);
		panel_2.add(time3);
		
		temp3.setHorizontalAlignment(SwingConstants.LEFT);
		temp3.setBounds(10, 22, 150, 49);
		panel_2.add(temp3);
		
		sky3.setHorizontalAlignment(SwingConstants.LEFT);
		sky3.setBounds(10, 45, 84, 59);
		panel_2.add(sky3);
		
		picture3.setBounds(77, 58, 46, 46);
		panel_2.add(picture3);
		
		rain3.setBounds(10, 102, 100, 14);
		panel_2.add(rain3);
		
		panel_3.setLayout(null);
		panel_3.setOpaque(false);
		panel_3.setBounds(534, 43, 133, 143);
		panel_short.add(panel_3);
		
		time4.setVerticalAlignment(SwingConstants.TOP);
		time4.setHorizontalAlignment(SwingConstants.LEFT);
		time4.setBounds(10, 9, 100, 49);
		panel_3.add(time4);
		
		temp4.setHorizontalAlignment(SwingConstants.LEFT);
		temp4.setBounds(10, 22, 150, 49);
		panel_3.add(temp4);
		
		sky4.setHorizontalAlignment(SwingConstants.LEFT);
		sky4.setBounds(10, 45, 84, 59);
		panel_3.add(sky4);
		
		picture4.setBounds(77, 58, 46, 46);
		panel_3.add(picture4);
		
		rain4.setBounds(10, 102, 100, 14);
		panel_3.add(rain4);
		
		panel_4.setLayout(null);
		panel_4.setOpaque(false);
		panel_4.setBounds(378, 210, 133, 143);
		panel_short.add(panel_4);
		
		time7.setVerticalAlignment(SwingConstants.TOP);
		time7.setHorizontalAlignment(SwingConstants.LEFT);
		time7.setBounds(10, 9, 100, 49);
		panel_4.add(time7);
		
		temp7.setHorizontalAlignment(SwingConstants.LEFT);
		temp7.setBounds(10, 22, 150, 49);
		panel_4.add(temp7);
		
		sky7.setHorizontalAlignment(SwingConstants.LEFT);
		sky7.setBounds(10, 45, 84, 59);
		panel_4.add(sky7);
		
		picture7.setBounds(77, 60, 46, 46);
		panel_4.add(picture7);
		
		rain5.setBounds(10, 102, 100, 14);
		panel_4.add(rain5);
		
		panel_5.setLayout(null);
		panel_5.setOpaque(false);
		panel_5.setBounds(534, 210, 133, 143);
		panel_short.add(panel_5);
		
		time8.setVerticalAlignment(SwingConstants.TOP);
		time8.setHorizontalAlignment(SwingConstants.LEFT);
		time8.setBounds(10, 9, 100, 49);
		panel_5.add(time8);
		
		temp8.setHorizontalAlignment(SwingConstants.LEFT);
		temp8.setBounds(10, 22, 150, 49);
		panel_5.add(temp8);
		
		sky8.setHorizontalAlignment(SwingConstants.LEFT);
		sky8.setBounds(10, 45, 84, 59);
		panel_5.add(sky8);
		
		picture8.setBounds(77, 60, 46, 46);
		panel_5.add(picture8);
		
		rain6.setBounds(10, 102, 100, 14);
		panel_5.add(rain6);
		
		panel_6.setLayout(null);
		panel_6.setOpaque(false);
		panel_6.setBounds(67, 210, 133, 143);
		panel_short.add(panel_6);
		
		time5.setVerticalAlignment(SwingConstants.TOP);
		time5.setHorizontalAlignment(SwingConstants.LEFT);
		time5.setBounds(10, 9, 100, 49);
		panel_6.add(time5);
		
		temp5.setHorizontalAlignment(SwingConstants.LEFT);
		temp5.setBounds(10, 22, 150, 49);
		panel_6.add(temp5);
		
		sky5.setHorizontalAlignment(SwingConstants.LEFT);
		sky5.setBounds(10, 45, 84, 59);
		panel_6.add(sky5);
		
		picture5.setBounds(77, 56, 46, 46);
		panel_6.add(picture5);
		
		rain7.setBounds(10, 102, 100, 14);
		panel_6.add(rain7);
		
		panel_7.setLayout(null);
		panel_7.setOpaque(false);
		panel_7.setBounds(223, 210, 133, 143);
		panel_short.add(panel_7);
		
		time6.setVerticalAlignment(SwingConstants.TOP);
		time6.setHorizontalAlignment(SwingConstants.LEFT);
		time6.setBounds(10, 9, 100, 49);
		panel_7.add(time6);
		
		temp6.setHorizontalAlignment(SwingConstants.LEFT);
		temp6.setBounds(10, 22, 150, 49);
		panel_7.add(temp6);
		
		sky6.setHorizontalAlignment(SwingConstants.LEFT);
		sky6.setBounds(10, 45, 88, 59);
		panel_7.add(sky6);
		
		picture6.setBounds(77, 58, 46, 46);
		panel_7.add(picture6);
		
		rain8.setBounds(10, 102, 100, 14);
		panel_7.add(rain8);
		
		// Long Term Panel Labels //
		
		locationName2.setHorizontalAlignment(SwingConstants.RIGHT);
		locationName2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		locationName2.setBounds(344, 5, 400, 33);
		panel_long.add(locationName2);
		
		lblUpdatedtime2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUpdatedtime2.setBounds(550, 392, 200, 14);
		panel_long.add(lblUpdatedtime2);
		
		panel_long_0.setBounds(36, 45, 128, 313);
		panel_long_0.setOpaque(false);
		panel_long.add(panel_long_0);
		panel_long_0.setLayout(null);
		
		long_date = new JLabel("date");
		long_date.setHorizontalAlignment(SwingConstants.CENTER);
		long_date.setBounds(10, 11, 108, 14);
		panel_long_0.add(long_date);
		
		long_temp = new JLabel("00");
		long_temp.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		long_temp.setBounds(15, 113, 100, 42);
		panel_long_0.add(long_temp);
		
		long_picture = new JLabel("");
		long_picture.setBounds(40, 36, 49, 42);
		panel_long_0.add(long_picture);
		
		long_temp_max = new JLabel("00");
		long_temp_max.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_max.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max.setBounds(30, 186, 70, 14);
		panel_long_0.add(long_temp_max);
		
		long_temp_max_label = new JLabel("Max:");
		long_temp_max_label.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max_label.setBounds(30, 166, 70, 14);
		panel_long_0.add(long_temp_max_label);
		
		long_temp_min_label = new JLabel("Min:");
		long_temp_min_label.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min_label.setBounds(30, 211, 70, 14);
		panel_long_0.add(long_temp_min_label);
		
		long_temp_min = new JLabel("00");
		long_temp_min.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_min.setBounds(30, 234, 70, 14);
		panel_long_0.add(long_temp_min);
		
		long_temp_sky = new JLabel("sky");
		long_temp_sky.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_sky.setBounds(30, 89, 70, 14);
		panel_long_0.add(long_temp_sky);
		
		long_temp_precip_label = new JLabel("Precipitation:");
		long_temp_precip_label.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip_label.setBounds(10, 259, 108, 14);
		panel_long_0.add(long_temp_precip_label);
		
		long_temp_precip = new JLabel("00");
		long_temp_precip.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_precip.setBounds(30, 284, 70, 14);
		panel_long_0.add(long_temp_precip);
		
		panel_long_1.setLayout(null);
		panel_long_1.setOpaque(false);
		panel_long_1.setBounds(174, 45, 128, 313);
		panel_long.add(panel_long_1);
		
		long_date1 = new JLabel("date");
		long_date1.setHorizontalAlignment(SwingConstants.CENTER);
		long_date1.setBounds(10, 11, 108, 14);
		panel_long_1.add(long_date1);
		
		long_temp1 = new JLabel("00");
		long_temp1.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		long_temp1.setBounds(15, 113, 100, 42);
		panel_long_1.add(long_temp1);
		
		long_picture1 = new JLabel("");
		long_picture1.setBounds(40, 36, 49, 42);
		panel_long_1.add(long_picture1);
		
		long_temp_max1 = new JLabel("00");
		long_temp_max1.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_max1.setBounds(30, 186, 70, 14);
		panel_long_1.add(long_temp_max1);
		
		long_temp_max_label1 = new JLabel("Max:");
		long_temp_max_label1.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max_label1.setBounds(30, 166, 70, 14);
		panel_long_1.add(long_temp_max_label1);
		
		long_temp_min_label1 = new JLabel("Min:");
		long_temp_min_label1.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min_label1.setBounds(30, 211, 70, 14);
		panel_long_1.add(long_temp_min_label1);
		
		long_temp_min1 = new JLabel("00");
		long_temp_min1.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_min1.setBounds(30, 234, 70, 14);
		panel_long_1.add(long_temp_min1);
		
		long_temp_sky1 = new JLabel("sky");
		long_temp_sky1.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_sky1.setBounds(30, 89, 70, 14);
		panel_long_1.add(long_temp_sky1);
		
		long_temp_precip_label1 = new JLabel("Precipitation:");
		long_temp_precip_label1.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip_label1.setBounds(10, 259, 108, 14);
		panel_long_1.add(long_temp_precip_label1);
		
		long_temp_precip1 = new JLabel("00");
		long_temp_precip1.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_precip1.setBounds(30, 284, 70, 14);
		panel_long_1.add(long_temp_precip1);
		
		panel_long_2.setLayout(null);
		panel_long_2.setOpaque(false);
		panel_long_2.setBounds(312, 45, 128, 313);
		panel_long.add(panel_long_2);
		
		long_date2 = new JLabel("date");
		long_date2.setHorizontalAlignment(SwingConstants.CENTER);
		long_date2.setBounds(10, 11, 108, 14);
		panel_long_2.add(long_date2);
		
		long_temp2 = new JLabel("00");
		long_temp2.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		long_temp2.setBounds(15, 113, 100, 42);
		panel_long_2.add(long_temp2);
		
		long_picture2 = new JLabel("");
		long_picture2.setBounds(40, 36, 49, 42);
		panel_long_2.add(long_picture2);
		
		long_temp_max2 = new JLabel("00");
		long_temp_max2.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_max2.setBounds(30, 186, 70, 14);
		panel_long_2.add(long_temp_max2);
		
		long_temp_max_label2 = new JLabel("Max:");
		long_temp_max_label2.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max_label2.setBounds(30, 166, 70, 14);
		panel_long_2.add(long_temp_max_label2);
		
		long_temp_min_label2 = new JLabel("Min:");
		long_temp_min_label2.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min_label2.setBounds(30, 211, 70, 14);
		panel_long_2.add(long_temp_min_label2);
		
		long_temp_min2 = new JLabel("00");
		long_temp_min2.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_min2.setBounds(30, 234, 70, 14);
		panel_long_2.add(long_temp_min2);
		
		long_temp_sky2 = new JLabel("sky");
		long_temp_sky2.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_sky2.setBounds(30, 89, 70, 14);
		panel_long_2.add(long_temp_sky2);
		
		long_temp_precip_label2 = new JLabel("Precipitation:");
		long_temp_precip_label2.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip_label2.setBounds(10, 259, 108, 14);
		panel_long_2.add(long_temp_precip_label2);
		
		long_temp_precip2 = new JLabel("00");
		long_temp_precip2.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_precip2.setBounds(30, 284, 70, 14);
		panel_long_2.add(long_temp_precip2);
		
		panel_long_3.setLayout(null);
		panel_long_3.setOpaque(false);
		panel_long_3.setBounds(450, 45, 128, 313);
		panel_long.add(panel_long_3);
		
		long_date3 = new JLabel("date");
		long_date3.setHorizontalAlignment(SwingConstants.CENTER);
		long_date3.setBounds(10, 11, 108, 14);
		panel_long_3.add(long_date3);
		
		long_temp3 = new JLabel("00");
		long_temp3.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		long_temp3.setBounds(15, 113, 100, 42);
		panel_long_3.add(long_temp3);
		
		long_picture3 = new JLabel("");
		long_picture3.setBounds(40, 36, 49, 42);
		panel_long_3.add(long_picture3);
		
		long_temp_max3 = new JLabel("00");
		long_temp_max3.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_max3.setBounds(30, 186, 70, 14);
		panel_long_3.add(long_temp_max3);
		
		long_temp_max_label3 = new JLabel("Max:");
		long_temp_max_label3.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max_label3.setBounds(30, 166, 70, 14);
		panel_long_3.add(long_temp_max_label3);
		
		long_temp_min_label3 = new JLabel("Min:");
		long_temp_min_label3.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min_label3.setBounds(30, 211, 70, 14);
		panel_long_3.add(long_temp_min_label3);
		
		long_temp_min3 = new JLabel("00");
		long_temp_min3.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_min3.setBounds(30, 234, 70, 14);
		panel_long_3.add(long_temp_min3);
		
		long_temp_sky3 = new JLabel("sky");
		long_temp_sky3.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_sky3.setBounds(30, 89, 70, 14);
		panel_long_3.add(long_temp_sky3);
		
		long_temp_precip_label3 = new JLabel("Precipitation:");
		long_temp_precip_label3.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip_label3.setBounds(10, 259, 108, 14);
		panel_long_3.add(long_temp_precip_label3);
		
		long_temp_precip3 = new JLabel("00");
		long_temp_precip3.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_precip3.setBounds(30, 284, 70, 14);
		panel_long_3.add(long_temp_precip3);
		
		panel_long_4.setLayout(null);
		panel_long_4.setOpaque(false);
		panel_long_4.setBounds(588, 45, 128, 313);
		panel_long.add(panel_long_4);
		
		long_date4 = new JLabel("date");
		long_date4.setHorizontalAlignment(SwingConstants.CENTER);
		long_date4.setBounds(10, 11, 108, 14);
		panel_long_4.add(long_date4);
		
		long_temp4 = new JLabel("00");
		long_temp4.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		long_temp4.setBounds(15, 113, 100, 42);
		panel_long_4.add(long_temp4);
		
		long_picture4 = new JLabel("");
		long_picture4.setBounds(40, 36, 49, 42);
		panel_long_4.add(long_picture4);
		
		long_temp_max4 = new JLabel("00");
		long_temp_max4.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_max4.setBounds(30, 186, 70, 14);
		panel_long_4.add(long_temp_max4);
		
		long_temp_max_label4 = new JLabel("Max:");
		long_temp_max_label4.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_max_label4.setBounds(30, 166, 70, 14);
		panel_long_4.add(long_temp_max_label4);
		
		long_temp_min_label4 = new JLabel("Min:");
		long_temp_min_label4.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min_label4.setBounds(30, 211, 70, 14);
		panel_long_4.add(long_temp_min_label4);
		
		long_temp_min4 = new JLabel("00");
		long_temp_min4.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_min4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_min4.setBounds(30, 234, 70, 14);
		panel_long_4.add(long_temp_min4);
		
		long_temp_sky4 = new JLabel("sky");
		long_temp_sky4.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_sky4.setBounds(30, 89, 70, 14);
		panel_long_4.add(long_temp_sky4);
		
		long_temp_precip_label4 = new JLabel("Precipitation:");
		long_temp_precip_label4.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip_label4.setBounds(10, 259, 108, 14);
		panel_long_4.add(long_temp_precip_label4);
		
		long_temp_precip4 = new JLabel("00");
		long_temp_precip4.setHorizontalAlignment(SwingConstants.CENTER);
		long_temp_precip4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		long_temp_precip4.setBounds(30, 284, 70, 14);
		panel_long_4.add(long_temp_precip4);
		
	}	
}
