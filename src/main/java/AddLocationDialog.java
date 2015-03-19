//package main.java;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.json.*;


public class AddLocationDialog extends JDialog {

    private JButton addButton;
    private JButton cancelButton;
    private JButton searchButton;
    private JTextField searchField;
    private JList<City> cityList;
    private DefaultListModel<City> cityModel;
    private DialogListener dialogListener;

    public AddLocationDialog(JFrame parent) {
    	
        super(parent, "Add New Location", false);

        /* Set up window */
        setSize(400, 150);
        setLocationRelativeTo(parent);
        setVisible(true);

        /* Create components */
        searchField = new JTextField(12);
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        searchButton = new JButton("Search");

        
        ////* Set up add button *////
        addButton.setEnabled(false);
       
        /* If add button is clicked, dialog event is trigger */
        addButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {        		
        		
				DialogEvent event = new DialogEvent((City)cityList.getSelectedValue());
				
				if (dialogListener != null) {
					dialogListener.dialogEventOccurred(event);
				}
				setVisible(false);
        	}
        });
        
        ////* Set up cancel button *////
         
        cancelButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		setVisible(false);
        	}
        });
        
        /* Set up the search button */
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	
	            	String searchFieldIn = searchField.getText();
	            	if (!searchFieldIn.equals("")) {
		            	cityList.setVisible(true);
		            	searchCities(searchFieldIn);
		                searchField.requestFocusInWindow();
		                searchField.setText("");
	            	}
                } catch (JSONException ex) {
               

                };
            }
        });

        /* Set up the JList */
        cityModel = new DefaultListModel();
        cityList = new JList(cityModel);
        cityList.setVisible(false);
        cityList.setPreferredSize(new Dimension(100, 50));
        cityList.setVisibleRowCount(5);
        JScrollPane cityScrollPane = new JScrollPane(cityList);
        
        /** TEST ***/
        cityList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList<String> list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {	
                	DialogEvent event = new DialogEvent((City)cityList.getSelectedValue());
                	if (dialogListener != null) {
    					dialogListener.dialogEventOccurred(event);
    				}
                	setVisible(false);
                } 
            
                
            }
        });
        /** END OF TEST **/

        /* Lay out the components */
        layoutComponents();

    }

    public void layoutComponents() {

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        ////* first row *////
        gc.gridy = 0;

        gc.gridx = 0;
        gc.weightx = 3;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(searchField, gc);

        gc.gridx++;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(searchButton, gc);

        ////* next row *////

        gc.gridy++;


        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1.0;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(cityList, gc);

        ////* next row *////

        gc.gridy++;

        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;
//        gc.anchor = 
        add(addButton, gc);
        
        gc.gridx = 1;
        add(cancelButton, gc);
        
        
    }
    
	
	public void searchCities(String cityName) throws JSONException {
		JSONObject jsonObj = WeatherAPI.getLikeCities(cityName); 
		cityModel.removeAllElements();
		
		if (jsonObj != null) {
			JSONArray jsonArray = jsonObj.getJSONArray("list");	
			
			addButton.setEnabled(true);
			
			for (int i = 0; i < jsonArray.length(); i++) {
				int cityIdResult = jsonArray.getJSONObject(i).getInt("id");
				String cityNameResult = jsonArray.getJSONObject(i).getString("name");
				String countryNameResult = jsonArray.getJSONObject(i).getJSONObject("sys").getString("country");
				cityModel.addElement(new City(cityIdResult, cityNameResult, countryNameResult));
			}
			
		} else {
			throw new JSONException("Failed");
			
		}
		
		
	}
	
	public void setDialogListener(DialogListener dialogListener) {
		this.dialogListener = dialogListener;
		
	}
    
    


}





