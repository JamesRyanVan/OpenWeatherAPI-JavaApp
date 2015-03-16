import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.json.*;


public class AddLocationDialog extends JDialog {

    private JButton addButton;
    private JButton cancelButton;
    private JButton searchButton;
    private JTextField searchField;
    private JList cityList;
    DefaultListModel cityModel;


    public AddLocationDialog(JFrame parent) {
        super(parent, "Add New Location", false);

        /* Set up window */
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setVisible(true);

        /* Create components */
        searchField = new JTextField(10);
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        searchButton = new JButton("Search");

        /* Set up the JList */
        cityModel = new DefaultListModel();
        cityList = new JList(cityModel);
        cityList.setVisibleRowCount(5);
        JScrollPane cityScrollPane = new JScrollPane(cityList);

        /* Set up the search button */
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
	            	String city = searchField.getText();
	                getLikeCities(city);

	            
	                searchField.requestFocusInWindow();
	                searchField.setText("");
                } catch (JSONException ex) {
                	
                };
            }
        });

        /* Lay out the components */
        layoutComponents();

    }

    public void layoutComponents() {

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        ////* first row *////
        gc.gridy = 0;

        gc.gridx = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.HORIZONTAL; // component = preferred size
        add(searchField, gc);

        gc.gridx++;
        gc.fill = GridBagConstraints.NONE;
        add(searchButton, gc);

        ////* next row *////

        gc.gridy++;


        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1.0;
        gc.weighty = 5.0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(cityList, gc);

        ////* next row *////

        gc.gridy++;

        gc.gridx = 0;

    }
    
	
	public void getLikeCities(String cityName) throws JSONException {
		JSONObject jsonObj = WeatherAPI.getLikeCities(cityName); 

		if (jsonObj != null) {
			JSONArray jsonArray = jsonObj.getJSONArray("list");		
			cityModel.removeAllElements();
			for (int i = 0; i < jsonArray.length(); i++) {
				String nameResult = jsonArray.getJSONObject(i).getString("name");
				String countryResult = jsonArray.getJSONObject(i).getJSONObject("sys").getString("country");
				cityModel.addElement(nameResult + ", " + countryResult);
			}
			
//			System.out.println(cityModel.toString());
		} else {
			throw new JSONException("Failed");
			
		}
		
		
		
	}
    
    


}



