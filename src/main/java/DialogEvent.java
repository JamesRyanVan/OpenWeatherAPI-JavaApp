/* Class that represents an action event in the 
 * AddLocationDialog class.
 */
public class DialogEvent {
	
	private City cityObj;

	
	/** 
	 * Constructor method. The event 
	 * 
	 * @param cityObj
	 */
	public DialogEvent(City cityObj) {
		this.cityObj = cityObj;
	}
	
	/** 
	 * Returns the city's name from the dialog.
	 * 
	 * @return the city's name
	 */
	public String getCityName() {
		return cityObj.getCityName();
	}
	
	public int getCityID() {
		return cityObj.getCityID();
	}
	
	public String getCountryName() {
		return cityObj.getCountryName();
	}
	
}
