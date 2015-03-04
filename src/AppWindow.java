import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JRadioButtonMenuItem; 
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

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

	private static final long serialVersionUID = 1L;

	/**
	 * Default Contruction initiates default window settings in the
	 * initUI() method
	 */
	public AppWindow() {
		this.initUI(); // Default Constructor initiates default settings
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
		
		// Window Properties
		
		this.setTitle("OpenWeather Application v1.0"); // Window Title
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
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(718, 0, 180, 22);
		
		comboBox.addItem("Add Location");
		
		panel.add(comboBox);
		this.getContentPane().setLayout(null);
		
		return menubar;
		
	}
	
	/**
	 * tabbedContent method produces a tabbed area in the main window
	 * which separates different weather data.
	 */
	private void tabbedContent() {
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(1, 0, 1009, 474);
		this.getContentPane().add(tabbedPane);
		
		JTabbedPane tabbedPane_local = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_local.setBackground(SystemColor.menu);
		tabbedPane.addTab("Local", null, tabbedPane_local, null);
		
		JTabbedPane tabbedPane_short = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_short.setBackground(SystemColor.menu);
		tabbedPane.addTab("ShortTerm", null, tabbedPane_short, null);
		
		JTabbedPane tabbedPane_long = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_long.setBackground(SystemColor.menu);
		tabbedPane.addTab("LongTerm", null, tabbedPane_long, null);
		
	}
}
