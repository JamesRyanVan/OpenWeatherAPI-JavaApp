//package main.java;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddLocationDialog extends JDialog {

	private JButton addButton;
    private JButton cancelButton;
    private JButton searchButton;
    private JTextField searchField;
    private JList<City> cityList;
    private DefaultListModel<City> cityModel;
    private DialogListener dialogListener;
    
    /**
     * Constructor method.
     * 
     * @param the parent JFrame which the AddLocationDialog opened from
     */

    public AddLocationDialog(JFrame parent) {
    	
        super(parent, "Add New Location", false);

        /* Sets up dialog window */
//        setSize(400, 150);
        setSize(450, 175);
        setLocationRelativeTo(parent);
        setVisible(true);

        /* Creates components */
        searchField = new JTextField(12);
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        searchButton = new JButton("Search");

        
        ////* Sets up add button *////
        addButton.setEnabled(false); // off by default
       
        /* If add button is clicked, creates a dialog event (containing city info) */
        addButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {        		
        		
				DialogEvent event = new DialogEvent((City)cityList.getSelectedValue());
				
				if (dialogListener != null) {
					dialogListener.dialogEventOccurred(event);
				}
				/* Close the window */
				dispose(); 
        	}
        });
        
        ////* Sets up cancel button *////
         
        /* If cancel button is clicked, close dialog window */
        cancelButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		dispose(); 
        	}
        });
        
        ////* Sets up the JList and its data model *////
        cityModel = new DefaultListModel();
        cityList = new JList(cityModel);
        
        cityList.setVisible(true);
        cityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cityList.setPreferredSize(new Dimension(100, 50));
      
        cityList.setVisibleRowCount(5);
        JScrollPane cityScrollPane = new JScrollPane(cityList);
        
        /* If user double clicks search result, selects city */
        cityList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {	
                	DialogEvent event = new DialogEvent((City)cityList.getSelectedValue());
                	if (dialogListener != null) {
    					dialogListener.dialogEventOccurred(event);
    				}
                	dispose();	// close dialog window
                } 
            
                
            }
        });
        
        ////* Set up the search button/field *////
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	
             
	            	String searchFieldIn = searchField.getText();
	            	if (!searchFieldIn.equals("")) {
		      
	            		// TODO: errors to address: no internet, API 501, no matches for search
		            	searchCities(searchFieldIn); 
		            	
		                searchField.requestFocusInWindow();
		                searchField.setText("");
		                
		                cityList.setSelectedIndex(0);
	            	} else { // print message to try again
	            		
	            	}
	            	

	            	
                } catch (JSONException ex) {
               

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
    	
        setLayout(new GridBagLayout());
        
        GridBagConstraints gc = new GridBagConstraints();

        ////* First row *////
        gc.gridy = 0;

        /* Search field (0,0) */
        gc.gridx = 0;
        gc.weightx = 3.0;
        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(searchField, gc);

        /* Search Button (1,0) */
        gc.gridx++;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(searchButton, gc);

        ////* Next row *////

        gc.gridy++;
        
        /* City List (0,1) and (1,1) */
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1.0;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(cityList, gc);

        ////* Next row *////

        gc.gridy++;

        /* Add Button (0,2) */
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 1.0;
        gc.weighty = 0.05;
        gc.fill = GridBagConstraints.NONE;
//        gc.anchor = 
        add(addButton, gc);
        
        /* Cancel Button (1,2) */
        gc.gridx = 1;
        add(cancelButton, gc); 
        
    }
    
	/**
	 *  Helper method that performs a OpenWeatherMap like search for specified city and 
	 *  populates the query result in an AddLocationDialog's cityList.
	 *  
	 *  @throws JSONException if API query failed
	 *  @param the city name to search
	 *  */
	private void searchCities(String cityName) throws JSONException {
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
			
		} else {
			throw new JSONException("Failed");
			
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





