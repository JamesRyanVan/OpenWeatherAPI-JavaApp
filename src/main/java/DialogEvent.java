

/* Class that represents an action event in the 
 * AddLocationDialog class.
 */
public class DialogEvent {
	
	private City cityObj;

	
	/** 
	 * Constructor method.
	 * 
	 * @param cityObj
	 */
	public DialogEvent(City cityObj) {
		this.cityObj = cityObj;
	}
	
	/**
	 * @return cityObj the city obj from the dialog.
	 */
	public City getCityObj() {
		return cityObj;
	}
	
	/** 
	 * Returns the city's name from the dialog.
	 * 
	 * @return the city's name
	 */
	public String getCityName() {
		return cityObj.getCityName();
	}
	
	/**
	 * 
	 * @return city id from dialog
	 */
	public int getCityID() {
		return cityObj.getCityID();
	}
	
	/**
	 * 
	 * @return city id's country name
	 */
	public String getCountryName() {
		return cityObj.getCountryName();
	}
	
}
