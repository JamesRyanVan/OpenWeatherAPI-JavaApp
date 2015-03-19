import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class DialogEvent {
	
	private City cityObj;
	
	public DialogEvent(City cityObj) {
		this.cityObj = cityObj;
	}
	
	public String getCityName() {
		return cityObj.getCityName();
	}
	
	public int getCityId() {
		return cityObj.getCityId();
	}
	
	public String getCountryName() {
		return cityObj.getCountryName();
	}
	
}
