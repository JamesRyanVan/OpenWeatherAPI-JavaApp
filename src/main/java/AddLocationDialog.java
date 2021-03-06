package main.java;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

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
    	
    	/* Make dialog stay in foreground */
        super(parent, "Add New Location", ModalityType.APPLICATION_MODAL);
        
        /* Create border for dialog */
        JPanel panel = (JPanel)getContentPane();
        panel.setBorder(BorderFactory.createEtchedBorder());
  
        /* Creates components */
        searchField = new JTextField();
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        searchButton = new JButton("Search");
        resultLabel = new JLabel("");

        /* Sets up add button */
        addButton.setEnabled(false); // off by default
       
        /* If add button is clicked, creates a dialog event (containing city info) */
        addButton.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {     
        		
        		/* Close the window */
				setVisible(false);
        		
				/* Creates a dialog event (containing City obj to be added) */
        		DialogEvent event = new DialogEvent((City)cityList.getSelectedValue());
					
					if (dialogListener != null) {
						dialogListener.dialogEventOccurred(event);
					}
        	}
        });
        
        
        /* If cancel button is clicked, close dialog window */
        cancelButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		setVisible(false);
        	}
        });
        
        /* Sets up the JList and its data model and scroll pane */
        cityModel = new DefaultListModel<City>();
        cityList = new JList<City>(cityModel);   
        cityList.setVisible(true);
        cityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cityScrollPane = new JScrollPane(cityList);
      
        
        /* In the list, if user double clicks search result, selects city */
        cityList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {	
                	DialogEvent event = new DialogEvent((City)cityList.getSelectedValue());
                	if (dialogListener != null) {
    					dialogListener.dialogEventOccurred(event);
//    					System.out.println("Added: " + cityList.getSelectedValue());
    				}
                	setVisible(false);	
                } 
            }
        });
        
        /* Set up the search button/field */
        
        getRootPane().setDefaultButton(searchButton); /* User can quick hit 'enter' key to search */
        
        /* If search button is clicked, populate the query result in the list */
        searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String searchFieldIn = searchField.getText();
				
				if (!searchFieldIn.equals("")) { /* If user enters text in search field */
					try {
	        			searchCities(searchFieldIn); /* Perform a like-search on OpenWeatherMap */
	        	     	searchField.setText(""); /* Clear the search text field */
	        	     	
	        	     	/* Shorten search string longer than 20 chars */
	        	     	if (searchFieldIn.length() > 20) {
							searchFieldIn = searchFieldIn.substring(0, 20) + "..";
						}
	        	     	
		            	///* If match was found *///
		            	if (!cityModel.isEmpty()) {
		            		
		            		addButton.setEnabled(true);
		            		
		            		/* Focus on the first result in the search result list */
		    				cityList.requestFocusInWindow();
		    				cityList.setSelectedIndex(0);
		    				
		    				
		    				/* Formats the label showing number of results */
		    				if (cityModel.getSize() == 1) {
			    				resultLabel.setText(cityModel.getSize() + " match for \"" + searchFieldIn + "\"");
		    				} else {
		    					resultLabel.setText(cityModel.getSize() + " matches for \"" + searchFieldIn + "\"");
		    				}
		            	} else {
		            		///* Match was not found *///
		            		
		            		/* Clear everything in dialog */
		            		cityModel.removeAllElements();
							searchField.setText("");
							
							addButton.setEnabled(false);
						
			        		resultLabel.setText("No matches for " + "\"" + searchFieldIn + "\"");
		            	}
		            
					} catch (JSONException jsonEx){
						
	            		/* Clear everything in dialog */
						cityModel.removeAllElements();
						searchField.setText("");
						
						resultLabel.setText("Server error. Try again.");
						
						addButton.setEnabled(false);
					
					} catch (IOException ioEx) {
						/* Clear everything in dialog */
						cityModel.removeAllElements();
						searchField.setText("");
						
						resultLabel.setText("Server error. Try again.");
						
						addButton.setEnabled(false);
						
					}
				}
			}
        });
       
        
        /* Lays out the components in dialog */
        layoutComponents();
        
        /* Focuses cursor on search field */
        searchField.requestFocusInWindow();
        

        /* Set up the dialog window */
        setBounds(100, 100, 376, 284);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setVisible(false); /* Use another thread to make visible */
		
    }
    
    
	/**
	 * Helper method that lays out the components using a layout manager.
	 *
	 */
    private void layoutComponents() {
    	
    	GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{13, 240, 0, 3};
		gridBagLayout.rowHeights = new int[]{40, 127, 15, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0,0.0};
		getContentPane().setLayout(gridBagLayout);
		
		///* First row = search field and search button *///
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
		
		///* Second row = list showing search results  *///
		GridBagConstraints gbc_cityList = new GridBagConstraints();
		gbc_cityList.insets = new Insets(0, 0, 5, 5);
		gbc_cityList.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc_cityList.fill = GridBagConstraints.BOTH;
		gbc_cityList.gridx = 1;
		gbc_cityList.gridy = 1;
		cityScrollPane.setMinimumSize(new Dimension(240,127));
		cityScrollPane.setMaximumSize(new Dimension(240,127));
		cityScrollPane.setPreferredSize(new Dimension(240,127));
		getContentPane().add(cityScrollPane, gbc_cityList);
		
		///* Third row = label showing # of matches  *///
		GridBagConstraints gbc_matchLabel = new GridBagConstraints();
		gbc_matchLabel.anchor = GridBagConstraints.LAST_LINE_START;
		gbc_matchLabel.insets = new Insets(0, 0, 5, 5);
		resultLabel.setMinimumSize(new Dimension(240,15));
		resultLabel.setPreferredSize(new Dimension(240,15));
		resultLabel.setMaximumSize(new Dimension(240,15));
		gbc_matchLabel.gridx = 1;
		gbc_matchLabel.gridy = 2;
		getContentPane().add(resultLabel, gbc_matchLabel);
			
		///* Fourth row = cancel and add button  *///
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
		gbc_addButton.weighty = 2;
		getContentPane().add(addButton, gbc_addButton);
		

    	
    }
    
	/**
	 *  Helper method that performs a OpenWeatherMap like search for specified city and 
	 *  populates the query result in an AddLocationDialog's cityList.
	 *  
	 *  @throws JSONException 
	 *  @throw IOException
	 *  
	 *  @param the city name to search
	 *  */
		private void searchCities(String cityName) throws IOException, JSONException {
			
			try {

				cityModel.removeAllElements();
				
				/* Do a OWM like search and get JSON obj */
				JSONObject jsonObj = WeatherAPI.getLikeCities(cityName); 
				JSONArray jsonArray = jsonObj.getJSONArray("list");	
				
				addButton.setEnabled(true);
				
				/* Parse the JSON and add valid results to search result list */
				for (int i = 0; i < jsonArray.length(); i++) {
					int cityIDResult = jsonArray.getJSONObject(i).getInt("id");
					
					/* Ignore results where city id = 0 (a bug in OWM) */
					if (cityIDResult != 0) {
						String cityNameResult = jsonArray.getJSONObject(i).getString("name");
						String countryNameResult = jsonArray.getJSONObject(i).getJSONObject("sys").getString("country");
						cityModel.addElement(new City(cityIDResult, cityNameResult, countryNameResult));	
					} 
				}
			
			/* Catch Location Not Found exception from getLikeCities() in WeatherAPI */
			} catch (LocationNotFoundException locEx){
				
				///* Location not found *///
				
				/* Clear everything in dialog */
				cityModel.removeAllElements();
				searchField.setText("");
				
				addButton.setEnabled(false);
				
				/* Format result label */
				if (cityName.length() > 20) {
					cityName = cityName.substring(0, 20) + "..";
				}
        		resultLabel.setText("No matches for " + "\"" + cityName + "\"");
			}
			
	}
	
	
	/**
	 * Sets the dialog listener.
	 * 
	 * @param the dialogListener to set
	 */
	public void setDialogListener(DialogListener dialogListener) {
		this.dialogListener = dialogListener;
		
	}
	

}
