import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class AddLocationDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JButton addButton;
    private JButton cancelButton;
    private JButton searchButton;
    private JLabel resultLabel;
    private JTextField searchField;
    private JList<City> cityList;
    private DefaultListModel<City> cityModel;
    private JScrollPane cityScrollPane;
    private DialogListener dialogListener;
       
    /**
     * Constructor method.
     * 
     * @param the parent JFrame which the AddLocationDialog opened from
     */

    public AddLocationDialog(JFrame parent) {
    	
        super(parent, "Add New Location", false);

        // Sets up dialog window //
        setBounds(100, 100, 376, 293);
        setLocationRelativeTo(parent);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  
        // Creates components //
        searchField = new JTextField();
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        searchButton = new JButton("Search");
        resultLabel = new JLabel("");

        // Sets up add button //
        addButton.setEnabled(false); // off by default
       
        // If add button is clicked, creates a dialog event (containing city info) //
        addButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {        		
        		
				DialogEvent event = new DialogEvent((City)cityList.getSelectedValue());
				
				if (dialogListener != null) {
					dialogListener.dialogEventOccurred(event);
				}
				// Close the window //
				dispose(); 
        	}
        });
        
        // Sets up cancel button //
         
        // If cancel button is clicked, close dialog window //
        cancelButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		dispose(); 
        	}
        });
        
        ///// Sets up the JList and its data model and scroll pane /////
        cityModel = new DefaultListModel<City>();
        cityList = new JList<City>(cityModel);   
        cityList.setVisible(true);
        cityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cityScrollPane = new JScrollPane(cityList);
        
        // If user double clicks search result, selects city //
        cityList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {	
                	DialogEvent event = new DialogEvent((City)cityList.getSelectedValue());
                	if (dialogListener != null) {
    					dialogListener.dialogEventOccurred(event);
    					System.out.println("Added: " + cityList.getSelectedValue());
    				}
                	dispose();	// close dialog window
                } 
            }
        });
        
        ///// Set up the search button/field /////
        
        getRootPane().setDefaultButton(searchButton); // User can quick hit 'enter' key to search
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
	            	String searchFieldIn = searchField.getText();
	            	
	            	if (!searchFieldIn.equals("")) { // If user enters text in search field
	            			searchCities(searchFieldIn); // Perform a like-search on OpenWeatherMap
	            	     	searchField.setText(""); // Clear the search text field
			            	
			            	// Match found //
			            	if (!cityModel.isEmpty()) {
			            		
			            		addButton.setEnabled(true);
			            		// Focus on the first result in the search result list
			    				cityList.requestFocusInWindow();
			    				cityList.setSelectedIndex(0);
			    				
			    				if (cityModel.getSize() == 1) {
				    				resultLabel.setText(cityModel.getSize() + " match for " + searchFieldIn);
			    				} else {
			    					resultLabel.setText(cityModel.getSize() + " matches for " + searchFieldIn);
			    				}

			    			// No match //
			            	} else {
			            		addButton.setEnabled(false);
			            		resultLabel.setText("No matches for " + searchFieldIn + ". Try again.");
			            	}
		
	            	}
                } catch (Exception ex) { // CHANGE
                	JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                };
            }
        });

        /* Lays out the components */
        layoutComponents();
        
        /* Focuses cursor on search field */
        searchField.requestFocusInWindow();
    }
    
    
	/**
	 * Helper method that lays out the components using a layout manager.
	 *
	 */
    private void layoutComponents() {
    	
    	GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{13, 240, 0, 9, 0};
		gridBagLayout.rowHeights = new int[]{61, 153, 44, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
			
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.insets = new Insets(0, 0, 5, 5);
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.gridx = 1;
		gbc_searchField.gridy = 0;
		getContentPane().add(searchField, gbc_searchField);
		searchField.setColumns(10);
		
			
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.anchor = GridBagConstraints.EAST;
		gbc_searchButton.insets = new Insets(0, 0, 5, 5);
		gbc_searchButton.gridx = 2;
		gbc_searchButton.gridy = 0;
		getContentPane().add(searchButton, gbc_searchButton);
		
		GridBagConstraints gbc_matchLabel = new GridBagConstraints();
		gbc_matchLabel.anchor = GridBagConstraints.LAST_LINE_START;
		gbc_matchLabel.insets = new Insets(0, 0, 5, 5);
		gbc_matchLabel.gridx = 1;
		gbc_matchLabel.gridy = 2;
		gbc_matchLabel.weighty = 0.1;
		getContentPane().add(resultLabel, gbc_matchLabel);
			
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.anchor = GridBagConstraints.WEST;
		gbc_cancelButton.insets = new Insets(0, 0, 0, 5);
		gbc_cancelButton.gridx = 1;
		gbc_cancelButton.gridy = 3;
		getContentPane().add(cancelButton, gbc_cancelButton);
		
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.anchor = GridBagConstraints.EAST;
		gbc_addButton.insets = new Insets(0, 0, 0, 5);
		gbc_addButton.gridx = 2;
		gbc_addButton.gridy = 3;
		getContentPane().add(addButton, gbc_addButton);
		
		GridBagConstraints gbc_cityList = new GridBagConstraints();
		gbc_cityList.insets = new Insets(0, 0, 5, 5);
		gbc_cityList.fill = GridBagConstraints.BOTH;
		gbc_cityList.gridx = 1;
		gbc_cityList.gridy = 1;
		getContentPane().add(cityScrollPane, gbc_cityList);
    	
    }
    
	/**
	 *  Helper method that performs a OpenWeatherMap like search for specified city and 
	 *  populates the query result in an AddLocationDialog's cityList.
	 *  
	 *  @throws JSONException if API query failed
	 *  @param the city name to search
	 *  */
	private void searchCities(String cityName) throws JSONException, IOException {

		JSONObject jsonObj = WeatherAPI.getLikeCities(cityName); 

		cityModel.removeAllElements();
		
		if (jsonObj != null) {
			JSONArray jsonArray = jsonObj.getJSONArray("list");	
			
			addButton.setEnabled(true);
			
			for (int i = 0; i < jsonArray.length(); i++) {
				int cityIDResult = jsonArray.getJSONObject(i).getInt("id");
				String cityNameResult = jsonArray.getJSONObject(i).getString("name");
				String countryNameResult = jsonArray.getJSONObject(i).getJSONObject("sys").getString("country");
				cityModel.addElement(new City(cityIDResult, cityNameResult, countryNameResult));	
			}	
		}
	}
	
//    private void searchCities(String searchFieldIn) throws IOException {
//		
//		InputStream inputStream = null; // Starts new stream and scanner
//		Scanner sc = null;
//		cityModel.removeAllElements();
//		
//		try {
//			getClass().getClassLoader();
//		    inputStream = ClassLoader.getSystemResourceAsStream("city.list"); // File containing the list
//		    sc = new Scanner(inputStream);
//		    
//		    while (sc.hasNextLine()) { // While lines exist
//		        String line = sc.nextLine();
//		        
//		        if(line.toLowerCase().contains(searchFieldIn.toLowerCase())) { // If the line contains the city name 
//		        	
//		        	String[] lineArray = line.split("\\s+"); // Split the line up where spaces are
//		        	int cityIDResult = Integer.parseInt(lineArray[0]); // Take the ID number
//		        	
//		        	int index = 1;
//		        	String cityNameResult = "";
//		        	
//		        	while (true) {
//			        	try {
//			        		Double.parseDouble(lineArray[index]); // if index is at a double
//			        		index += 2; // jump to index for country name
//			        		break;
//			        	} catch (NumberFormatException numEx) { // if index is at a string
	
//							// build country name string
//			        		if (cityNameResult.length() != 0) {
//			        			cityNameResult = cityNameResult + " " + lineArray[index++];
//			        		} else {
//			        			cityNameResult += lineArray[index++];
//			        		}
//			        	}
//		        	}
//		        		
//		        	String countryNameResult = lineArray[index];
//		        	cityModel.addElement(new City(cityIDResult, cityNameResult, countryNameResult));
//		        
//		        }
//	        	
//		    }
//		    if (sc.ioException() != null) { // If any error is caught then 0 is returned
//		        throw sc.ioException();
//		    }
//		} catch (FileNotFoundException e){
//			JOptionPane.showMessageDialog (null, "Error", "City list file not found", JOptionPane.ERROR_MESSAGE);
//		}
//		
//		finally {
//		    if (inputStream != null) {
//		        inputStream.close();
//		    }
//		    if (sc != null) {
//		        sc.close();
//		    }
//		}
//		
//	}
	
	/**
	 * Sets the dialog listener.
	 * 
	 * @param the dialogListener to set
	 */
	public void setDialogListener(DialogListener dialogListener) {
		this.dialogListener = dialogListener;
		
	}

}
